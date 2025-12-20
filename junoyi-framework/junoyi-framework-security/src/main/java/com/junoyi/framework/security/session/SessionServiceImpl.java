package com.junoyi.framework.security.session;

import com.junoyi.framework.core.utils.StringUtils;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import com.junoyi.framework.redis.utils.RedisUtils;
import com.junoyi.framework.security.module.LoginUser;
import com.junoyi.framework.security.token.JwtTokenService;
import com.junoyi.framework.security.token.TokenPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 会话服务实现类
 * 
 * Redis 存储结构：
 * 1. session:{tokenId}     -> UserSession（会话详情）
 * 2. refresh:{tokenId}     -> userId（RefreshToken 白名单）
 * 3. user:sessions:{userId} -> Set<tokenId>（用户会话索引）
 *
 * @author Fan
 */
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(SessionServiceImpl.class);
    private final JwtTokenService tokenService;

    // Redis Key 前缀
    private static final String SESSION_KEY_PREFIX = "session:";
    private static final String REFRESH_KEY_PREFIX = "refresh:";
    private static final String USER_SESSIONS_KEY_PREFIX = "user:sessions:";

    @Override
    public TokenPair login(LoginUser loginUser, String loginIp, String userAgent) {
        // 1. 创建 Token 对
        TokenPair tokenPair = tokenService.createTokenPair(loginUser);
        String tokenId = tokenPair.getTokenId();

        // 2. 构建会话信息
        Date now = new Date();
        UserSession session = UserSession.builder()
                .sessionId(tokenId)
                .userId(loginUser.getUserId())
                .userName(loginUser.getUserName())
                .nickName(loginUser.getNickName())
                .platformType(loginUser.getPlatformType())
                .permissions(loginUser.getPermissions())
                .roles(loginUser.getRoles())
                .loginIp(loginIp)
                .loginTime(now)
                .lastAccessTime(now)
                .userAgent(userAgent)
                .deviceType(parseDeviceType(userAgent))
                .accessExpireTime(tokenPair.getAccessExpireTime())
                .refreshExpireTime(tokenPair.getRefreshExpireTime())
                .build();

        // 3. 计算 TTL（使用 RefreshToken 的过期时间）
        long ttlMillis = tokenPair.getRefreshExpireTime() - System.currentTimeMillis();
        Duration ttl = Duration.ofMillis(ttlMillis);

        // 4. 存储会话到 Redis
        String sessionKey = SESSION_KEY_PREFIX + tokenId;
        RedisUtils.setCacheObject(sessionKey, session, ttl);

        // 5. 存储 RefreshToken 白名单
        String refreshKey = REFRESH_KEY_PREFIX + tokenId;
        RedisUtils.setCacheObject(refreshKey, loginUser.getUserId(), ttl);

        // 6. 添加到用户会话索引
        String userSessionsKey = USER_SESSIONS_KEY_PREFIX + loginUser.getUserId();
        RedisUtils.setCacheSet(userSessionsKey, Set.of(tokenId));

        log.info("SessionCreated", "用户登录成功 | 用户: " + loginUser.getUserName() 
                + " | tokenId: " + tokenId.substring(0, 8) + "..."
                + " | IP: " + loginIp);

        return tokenPair;
    }

    @Override
    public boolean logout(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }

        // 获取 tokenId
        String tokenId = tokenService.getTokenId(token);
        if (StringUtils.isBlank(tokenId)) {
            return false;
        }

        return doLogout(tokenId);
    }

    /**
     * 执行登出逻辑
     */
    private boolean doLogout(String tokenId) {
        // 1. 获取会话信息（用于获取 userId）
        UserSession session = getSessionByTokenId(tokenId);
        
        // 2. 删除会话
        String sessionKey = SESSION_KEY_PREFIX + tokenId;
        RedisUtils.deleteObject(sessionKey);

        // 3. 删除 RefreshToken 白名单
        String refreshKey = REFRESH_KEY_PREFIX + tokenId;
        RedisUtils.deleteObject(refreshKey);

        // 4. 从用户会话索引中移除
        if (session != null) {
            String userSessionsKey = USER_SESSIONS_KEY_PREFIX + session.getUserId();
            Set<String> sessions = RedisUtils.getCacheSet(userSessionsKey);
            if (sessions != null) {
                sessions.remove(tokenId);
                if (sessions.isEmpty()) {
                    RedisUtils.deleteObject(userSessionsKey);
                } else {
                    RedisUtils.deleteObject(userSessionsKey);
                    RedisUtils.setCacheSet(userSessionsKey, sessions);
                }
            }
            
            log.info("SessionDestroyed", "用户登出成功 | 用户: " + session.getUserName() 
                    + " | tokenId: " + tokenId.substring(0, 8) + "...");
        }

        return true;
    }

    @Override
    public UserSession getSession(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        String tokenId = tokenService.getTokenId(token);
        return getSessionByTokenId(tokenId);
    }

    @Override
    public UserSession getSessionByTokenId(String tokenId) {
        if (StringUtils.isBlank(tokenId)) {
            return null;
        }

        String sessionKey = SESSION_KEY_PREFIX + tokenId;
        return RedisUtils.getCacheObject(sessionKey);
    }

    @Override
    public LoginUser getLoginUser(String token) {
        UserSession session = getSession(token);
        if (session == null) {
            return null;
        }

        return LoginUser.builder()
                .userId(session.getUserId())
                .userName(session.getUserName())
                .nickName(session.getNickName())
                .platformType(session.getPlatformType())
                .permissions(session.getPermissions())
                .roles(session.getRoles())
                .loginIp(session.getLoginIp())
                .loginTime(session.getLoginTime())
                .build();
    }

    @Override
    public TokenPair refreshToken(String refreshToken) {
        // 1. 验证 RefreshToken
        if (!tokenService.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("RefreshToken 无效或已过期");
        }

        // 2. 获取 tokenId
        String oldTokenId = tokenService.getTokenId(refreshToken);
        if (StringUtils.isBlank(oldTokenId)) {
            throw new IllegalArgumentException("无法解析 RefreshToken");
        }

        // 3. 检查 RefreshToken 是否在白名单中（是否被主动失效）
        String refreshKey = REFRESH_KEY_PREFIX + oldTokenId;
        if (!RedisUtils.isExistsObject(refreshKey)) {
            throw new IllegalArgumentException("RefreshToken 已被撤销");
        }

        // 4. 获取旧会话
        UserSession oldSession = getSessionByTokenId(oldTokenId);
        if (oldSession == null) {
            throw new IllegalArgumentException("会话不存在或已过期");
        }

        // 5. 构建 LoginUser
        LoginUser loginUser = LoginUser.builder()
                .userId(oldSession.getUserId())
                .userName(oldSession.getUserName())
                .nickName(oldSession.getNickName())
                .platformType(oldSession.getPlatformType())
                .permissions(oldSession.getPermissions())
                .roles(oldSession.getRoles())
                .loginIp(oldSession.getLoginIp())
                .loginTime(oldSession.getLoginTime())
                .build();

        // 6. 删除旧会话
        doLogout(oldTokenId);

        // 7. 创建新会话
        TokenPair newTokenPair = login(loginUser, oldSession.getLoginIp(), oldSession.getUserAgent());

        log.info("TokenRefreshed", "Token 刷新成功 | 用户: " + loginUser.getUserName() 
                + " | 旧tokenId: " + oldTokenId.substring(0, 8) + "..."
                + " | 新tokenId: " + newTokenPair.getTokenId().substring(0, 8) + "...");

        return newTokenPair;
    }

    @Override
    public boolean updateSession(String tokenId, LoginUser loginUser) {
        if (StringUtils.isBlank(tokenId)) {
            return false;
        }

        UserSession session = getSessionByTokenId(tokenId);
        if (session == null) {
            return false;
        }

        // 更新会话信息
        session.setUserName(loginUser.getUserName());
        session.setNickName(loginUser.getNickName());
        session.setPermissions(loginUser.getPermissions());
        session.setRoles(loginUser.getRoles());
        session.setLastAccessTime(new Date());

        // 保存到 Redis（保留原 TTL）
        String sessionKey = SESSION_KEY_PREFIX + tokenId;
        RedisUtils.setCacheObject(sessionKey, session, true);

        log.info("SessionUpdated", "会话更新成功 | 用户: " + loginUser.getUserName() 
                + " | tokenId: " + tokenId.substring(0, 8) + "...");

        return true;
    }

    @Override
    public List<UserSession> getUserSessions(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }

        String userSessionsKey = USER_SESSIONS_KEY_PREFIX + userId;
        Set<String> tokenIds = RedisUtils.getCacheSet(userSessionsKey);
        
        if (tokenIds == null || tokenIds.isEmpty()) {
            return Collections.emptyList();
        }

        return tokenIds.stream()
                .map(this::getSessionByTokenId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public boolean kickOut(String tokenId) {
        if (StringUtils.isBlank(tokenId)) {
            return false;
        }

        UserSession session = getSessionByTokenId(tokenId);
        if (session != null) {
            log.info("SessionKicked", "会话被踢出 | 用户: " + session.getUserName() 
                    + " | tokenId: " + tokenId.substring(0, 8) + "...");
        }

        return doLogout(tokenId);
    }

    @Override
    public int kickOutAll(Long userId) {
        if (userId == null) {
            return 0;
        }

        List<UserSession> sessions = getUserSessions(userId);
        int count = 0;
        
        for (UserSession session : sessions) {
            if (doLogout(session.getSessionId())) {
                count++;
            }
        }

        log.info("AllSessionsKicked", "用户所有会话被踢出 | userId: " + userId + " | 数量: " + count);

        return count;
    }

    @Override
    public boolean isValid(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }

        // 1. 验证 Token 签名
        if (!tokenService.validateAccessToken(token) && !tokenService.validateRefreshToken(token)) {
            return false;
        }

        // 2. 检查会话是否存在
        String tokenId = tokenService.getTokenId(token);
        if (StringUtils.isBlank(tokenId)) {
            return false;
        }

        String sessionKey = SESSION_KEY_PREFIX + tokenId;
        return RedisUtils.isExistsObject(sessionKey);
    }

    @Override
    public void touch(String tokenId) {
        if (StringUtils.isBlank(tokenId)) {
            return;
        }

        UserSession session = getSessionByTokenId(tokenId);
        if (session != null) {
            session.setLastAccessTime(new Date());
            String sessionKey = SESSION_KEY_PREFIX + tokenId;
            RedisUtils.setCacheObject(sessionKey, session, true);
        }
    }

    /**
     * 解析设备类型
     */
    private String parseDeviceType(String userAgent) {
        if (StringUtils.isBlank(userAgent)) {
            return "Unknown";
        }
        
        userAgent = userAgent.toLowerCase();
        
        if (userAgent.contains("mobile") || userAgent.contains("android") || userAgent.contains("iphone")) {
            return "Mobile";
        } else if (userAgent.contains("tablet") || userAgent.contains("ipad")) {
            return "Tablet";
        } else if (userAgent.contains("windows") || userAgent.contains("macintosh") || userAgent.contains("linux")) {
            return "Desktop";
        }
        
        return "Unknown";
    }
}
