package com.junoyi.framework.security.token;

import com.junoyi.framework.core.utils.StringUtils;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import com.junoyi.framework.security.enums.PlatformType;
import com.junoyi.framework.security.module.LoginUser;
import com.junoyi.framework.security.properties.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static com.junoyi.framework.core.constant.Constants.*;

/**
 * JWT Token 服务实现类
 * 
 * 设计原则：AccessToken 和 RefreshToken 逻辑关联，但生成上完全独立
 * - 通过共享的 tokenId 建立关联
 * - 各自独立签名、独立过期时间
 * - RefreshToken 不依赖 AccessToken 生成
 * 
 * 安全特性：
 * 1. 使用 HS512 算法签名
 * 2. 共享 tokenId 关联 Token 对
 * 3. 独立的 JTI 防止重放攻击
 * 4. 区分 Token 类型防止混用
 * 
 * @author Fan
 */
@Component
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(JwtTokenService.class);

    private final SecurityProperties securityProperties;

    /**
     * 创建 Token 对（AccessToken + RefreshToken）
     * 两个 Token 通过 tokenId 关联，但各自独立生成
     */
    @Override
    public TokenPair createTokenPair(LoginUser loginUser) {
        // 生成共享的 tokenId，用于关联 AccessToken 和 RefreshToken
        String tokenId = UUID.randomUUID().toString().replace("-", "");
        
        Date now = new Date();
        
        // 独立生成 AccessToken
        Duration accessDuration = getAccessExpireDuration(loginUser.getPlatformType());
        Date accessExpiration = new Date(now.getTime() + accessDuration.toMillis());
        String accessToken = buildAccessToken(loginUser, tokenId, now, accessExpiration);
        
        // 独立生成 RefreshToken
        Duration refreshDuration = getRefreshExpireDuration(loginUser.getPlatformType());
        Date refreshExpiration = new Date(now.getTime() + refreshDuration.toMillis());
        String refreshToken = buildRefreshToken(loginUser, tokenId, now, refreshExpiration);
        
        log.info("TokenPairCreated", "创建 Token 对成功 | 用户: " + loginUser.getUserName() 
                + " | tokenId: " + tokenId.substring(0, 8) + "..."
                + " | AccessToken 有效期: " + accessDuration.toMinutes() + "分钟"
                + " | RefreshToken 有效期: " + refreshDuration.toDays() + "天");
        
        return TokenPair.builder()
                .tokenId(tokenId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpireTime(accessExpiration.getTime())
                .refreshExpireTime(refreshExpiration.getTime())
                .build();
    }

    /**
     * 构建 AccessToken（独立生成）
     */
    private String buildAccessToken(LoginUser loginUser, String tokenId, Date now, Date expiration) {
        String jti = UUID.randomUUID().toString().replace("-", "");
        
        JwtBuilder builder = Jwts.builder()
                .subject(String.valueOf(loginUser.getUserId()))
                .claim(CLAIM_TYPE, TOKEN_TYPE_ACCESS)
                .claim(CLAIM_TOKEN_ID, tokenId)          // 关联标识
                .claim(CLAIM_JTI, jti)                   // 独立唯一标识
                .claim(CLAIM_PLATFORM, loginUser.getPlatformType().getCode())
                .claim(CLAIM_USERNAME, loginUser.getUserName())
                .claim(CLAIM_NICK_NAME, loginUser.getNickName());
        
        // 添加权限列表（如果存在）
        if (loginUser.getPermissions() != null && !loginUser.getPermissions().isEmpty()) {
            builder.claim(CLAIM_PERMISSIONS, String.join(",", loginUser.getPermissions()));
        }
        
        // 添加角色列表（如果存在）
        if (loginUser.getRoles() != null && !loginUser.getRoles().isEmpty()) {
            builder.claim(CLAIM_ROLES, loginUser.getRoles().stream()
                    .map(String::valueOf)
                    .reduce((a, b) -> a + "," + b)
                    .orElse(""));
        }
        
        return builder
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSecretKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * 构建 RefreshToken（独立生成，不依赖 AccessToken）
     */
    private String buildRefreshToken(LoginUser loginUser, String tokenId, Date now, Date expiration) {
        String jti = UUID.randomUUID().toString().replace("-", "");
        
        // RefreshToken 只包含最少必要信息
        return Jwts.builder()
                .subject(String.valueOf(loginUser.getUserId()))
                .claim(CLAIM_TYPE, TOKEN_TYPE_REFRESH)
                .claim(CLAIM_TOKEN_ID, tokenId)          // 关联标识
                .claim(CLAIM_JTI, jti)                   // 独立唯一标识
                .claim(CLAIM_PLATFORM, loginUser.getPlatformType().getCode())
                .claim(CLAIM_USERNAME, loginUser.getUserName())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSecretKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * 解析访问令牌
     */
    @Override
    public LoginUser parseAccessToken(String accessToken) {
        try {
            Claims claims = parseToken(accessToken);

            // 验证 Token 类型
            String tokenType = claims.get(CLAIM_TYPE, String.class);
            if (!TOKEN_TYPE_ACCESS.equals(tokenType)) {
                log.warn("TokenTypeError", "Token 类型不匹配，期望: access，实际: " + tokenType);
                return null;
            }

            return extractLoginUser(claims);

        } catch (ExpiredJwtException e) {
            log.warn("TokenExpired", "AccessToken 已过期");
            return null;
        } catch (SignatureException e) {
            log.warn("TokenSignatureError", "AccessToken 签名验证失败");
            return null;
        } catch (Exception e) {
            log.error("TokenParseError", "解析 AccessToken 失败", e);
            return null;
        }
    }

    /**
     * 验证访问令牌
     */
    @Override
    public boolean validateAccessToken(String accessToken) {
        if (StringUtils.isBlank(accessToken))
            return false;

        try {
            Claims claims = parseToken(accessToken);
            String tokenType = claims.get(CLAIM_TYPE, String.class);
            
            if (!TOKEN_TYPE_ACCESS.equals(tokenType)) {
                log.warn("TokenValidationFailed", "Token 类型不匹配");
                return false;
            }

            return claims.getSubject() != null && claims.getExpiration() != null;

        } catch (ExpiredJwtException e) {
            log.debug("TokenExpired", "AccessToken 已过期");
            return false;
        } catch (SignatureException e) {
            log.warn("TokenSignatureError", "AccessToken 签名验证失败");
            return false;
        } catch (MalformedJwtException e) {
            log.warn("TokenMalformed", "AccessToken 格式错误");
            return false;
        } catch (Exception e) {
            log.error("TokenValidationError", "验证 AccessToken 失败", e);
            return false;
        }
    }

    /**
     * 解析刷新令牌
     */
    @Override
    public LoginUser parseRefreshToken(String refreshToken) {
        try {
            Claims claims = parseToken(refreshToken);

            String tokenType = claims.get(CLAIM_TYPE, String.class);
            if (!TOKEN_TYPE_REFRESH.equals(tokenType)) {
                log.warn("TokenTypeError", "Token 类型不匹配，期望: refresh，实际: " + tokenType);
                return null;
            }

            return extractLoginUser(claims);

        } catch (Exception e) {
            log.error("TokenParseError", "解析 RefreshToken 失败", e);
            return null;
        }
    }

    /**
     * 验证刷新令牌
     */
    @Override
    public boolean validateRefreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken))
            return false;

        try {
            Claims claims = parseToken(refreshToken);
            String tokenType = claims.get(CLAIM_TYPE, String.class);
            
            if (!TOKEN_TYPE_REFRESH.equals(tokenType))
                return false;

            return claims.getSubject() != null && claims.getExpiration() != null;

        } catch (Exception e) {
            log.debug("TokenValidationFailed", "RefreshToken 验证失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 使用刷新令牌刷新 Token 对
     * 生成全新的 Token 对（新的 tokenId）
     */
    @Override
    public TokenPair refreshTokenPair(String refreshToken) {
        try {
            if (!validateRefreshToken(refreshToken))
                throw new IllegalArgumentException("RefreshToken 无效或已过期");

            LoginUser loginUser = parseRefreshToken(refreshToken);
            if (loginUser == null)
                throw new IllegalArgumentException("无法从 RefreshToken 中解析用户信息");

            // 创建全新的 Token 对（新的 tokenId）
            TokenPair newTokenPair = createTokenPair(loginUser);

            log.info("TokenPairRefreshed", "刷新 Token 对成功 | 用户: " + loginUser.getUserName() 
                    + " | 新 tokenId: " + newTokenPair.getTokenId().substring(0, 8) + "...");

            return newTokenPair;

        } catch (IllegalArgumentException e) {
            log.warn("TokenRefreshFailed", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("TokenRefreshError", "刷新 Token 对失败", e);
            throw new RuntimeException("刷新令牌失败", e);
        }
    }

    /**
     * 获取 Token 中的 tokenId
     */
    @Override
    public String getTokenId(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.get(CLAIM_TOKEN_ID, String.class);
        } catch (Exception e) {
            log.error("GetTokenIdError", "获取 tokenId 失败", e);
            return null;
        }
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 解析 Token
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从 Claims 中提取 LoginUser
     */
    private LoginUser extractLoginUser(Claims claims) {
        Long userId = Long.parseLong(claims.getSubject());
        String username = claims.get(CLAIM_USERNAME, String.class);
        String nickName = claims.get(CLAIM_NICK_NAME, String.class);
        Integer platformCode = claims.get(CLAIM_PLATFORM, Integer.class);

        LoginUser.LoginUserBuilder builder = LoginUser.builder()
                .userId(userId)
                .userName(username)
                .nickName(nickName)
                .platformType(getPlatformType(platformCode));
        
        // 提取权限列表
        String permsStr = claims.get(CLAIM_PERMISSIONS, String.class);
        if (StringUtils.isNotBlank(permsStr)) {
            Set<String> permissions = new HashSet<>(Arrays.asList(permsStr.split(",")));
            builder.permissions(permissions);
        }
        
        // 提取角色列表
        String rolesStr = claims.get(CLAIM_ROLES, String.class);
        if (StringUtils.isNotBlank(rolesStr)) {
            Set<Long> roles = Arrays.stream(rolesStr.split(","))
                    .filter(StringUtils::isNotBlank)
                    .map(Long::parseLong)
                    .collect(Collectors.toSet());
            builder.roles(roles);
        }

        return builder.build();
    }

    /**
     * 获取签名密钥
     */
    private SecretKey getSecretKey() {
        String secret = securityProperties.getToken().getSecret();
        
        if (secret == null || secret.length() < 32)
            throw new IllegalStateException("JWT 密钥长度不足，至少需要 32 个字符");

        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取 AccessToken 过期时间
     */
    private Duration getAccessExpireDuration(PlatformType platformType) {
        String platformKey = getPlatformKey(platformType);
        String expireStr = securityProperties.getToken().getAccessExpire().get(platformKey);
        
        if (StringUtils.isBlank(expireStr))
            return Duration.ofMinutes(30);

        return DurationStyle.detectAndParse(expireStr);
    }

    /**
     * 获取 RefreshToken 过期时间
     */
    private Duration getRefreshExpireDuration(PlatformType platformType) {
        String platformKey = getPlatformKey(platformType);
        String expireStr = securityProperties.getToken().getRefreshExpire().get(platformKey);
        
        if (StringUtils.isBlank(expireStr))
            return Duration.ofDays(7);

        return DurationStyle.detectAndParse(expireStr);
    }

    /**
     * 根据平台类型获取配置键名
     */
    private String getPlatformKey(PlatformType platformType) {
        return switch (platformType) {
            case ADMIN_WEB, FRONT_DESK_WEB -> "web";
            case MINI_PROGRAM -> "miniprogram";
            case APP -> "app";
            case DESKTOP_APP -> "desktop";
        };
    }

    /**
     * 根据平台代码获取平台类型
     */
    private PlatformType getPlatformType(Integer code) {
        if (code == null)
            return PlatformType.ADMIN_WEB;

        for (PlatformType type : PlatformType.values()) {
            if (type.getCode() == code)
                return type;
        }
        
        return PlatformType.ADMIN_WEB;
    }
}
