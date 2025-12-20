package com.junoyi.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.junoyi.framework.core.utils.StringUtils;
import com.junoyi.framework.security.enums.PlatformType;
import com.junoyi.framework.security.module.LoginUser;
import com.junoyi.framework.security.service.AuthService;
import com.junoyi.framework.security.token.TokenPair;
import com.junoyi.system.domain.dto.LoginRequest;
import com.junoyi.system.domain.po.LoginIdentity;
import com.junoyi.system.domain.po.SysUser;
import com.junoyi.system.domain.vo.AuthVo;
import com.junoyi.system.enums.LoginType;
import com.junoyi.system.enums.SysUserStatus;
import com.junoyi.system.mapper.SysUserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统认证服务实现类
 *
 * @author Fan
 */
@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements ISysAuthService {

    private final AuthService authService;
    private final SysUserMapper sysUserMapper;
    
    // 密码加密器（可以注入为 Bean）
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthVo login(LoginRequest loginRequest) {
        // 解析登录账号类型
        LoginIdentity loginIdentity = parseIdentity(loginRequest);

        // 根据账号类型查询用户
        SysUser user = findUserByIdentity(loginIdentity);

        // 登录校验（用户状态等）
        validateUser(user);

        // 校验密码
        validatePassword(loginRequest.getPassword(), user.getPassword());

        // 构建 LoginUser
        LoginUser loginUser = buildLoginUser(user);

        // 获取请求信息
        HttpServletRequest request = getHttpServletRequest();
        String loginIp = getClientIp(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;

        // 调用 AuthService 登录（自动创建会话存入 Redis）
        TokenPair tokenPair = authService.login(loginUser, loginIp, userAgent);

        // 构建返回结果
        AuthVo authVo = new AuthVo();
        authVo.setAccessToken(tokenPair.getAccessToken());
        authVo.setRefreshToken(tokenPair.getRefreshToken());

        return authVo;
    }

    /**
     * 解析登录账号类型
     */
    private LoginIdentity parseIdentity(LoginRequest request) {
        if (StringUtils.isNotBlank(request.getPhonenumber()))
            return new LoginIdentity(LoginType.PHONENUMBER, request.getPhonenumber());

        if (StringUtils.isNotBlank(request.getEmail()))
            return new LoginIdentity(LoginType.EMAIL, request.getEmail());

        if (StringUtils.isNotBlank(request.getUsername()))
            return new LoginIdentity(LoginType.USERNAME, request.getUsername());

        throw new RuntimeException("登录账号不能为空");
    }

    /**
     * 根据登录标识查询用户
     */
    private SysUser findUserByIdentity(LoginIdentity identity) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::isDelFlag, false);
        
        switch (identity.getLoginType()) {
            case USERNAME -> wrapper.eq(SysUser::getUserName, identity.getAccount());
            case PHONENUMBER -> wrapper.eq(SysUser::getPhonenumber, identity.getAccount());
            case EMAIL -> wrapper.eq(SysUser::getEmail, identity.getAccount());
        }

        SysUser user = sysUserMapper.selectOne(wrapper);

        if (user == null)
            throw new RuntimeException("用户不存在或已被删除");

        return user;
    }

    /**
     * 校验用户状态
     */
    private void validateUser(SysUser user) {
        if (user.isDelFlag())
            throw new RuntimeException("用户已被删除");

        if (user.getStatus() == SysUserStatus.DISABLED.getCode())
            throw new RuntimeException("用户已被禁用");

        if (user.getStatus() == SysUserStatus.LOCKED.getCode())
            throw new RuntimeException("用户已被锁定");

    }

    /**
     * 校验密码
     */
    private void validatePassword(String rawPassword, String encodedPassword) {
        if (StringUtils.isBlank(rawPassword)) {
            throw new RuntimeException("密码不能为空");
        }

//        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
//            throw new RuntimeException("密码错误");
//        }
    }

    /**
     * 构建 LoginUser
     */
    private LoginUser buildLoginUser(SysUser user) {
        // TODO: 从数据库查询用户权限和角色
        Set<String> permissions = getUserPermissions(user.getUserId());
        Set<Long> roles = getUserRoles(user.getUserId());

        return LoginUser.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .nickName(user.getNickName())
                .platformType(PlatformType.ADMIN_WEB)  // 可以从请求中判断平台类型
                .permissions(permissions)
                .roles(roles)
                .build();
    }

    /**
     * 获取用户权限列表
     * TODO: 实现从数据库查询
     */
    private Set<String> getUserPermissions(Long userId) {
        // 这里应该从数据库查询用户权限
        // return sysPermissionMapper.selectPermissionsByUserId(userId);
        return new HashSet<>();
    }

    /**
     * 获取用户角色ID列表
     * TODO: 实现从数据库查询
     */
    private Set<Long> getUserRoles(Long userId) {
        // 这里应该从数据库查询用户角色
        // return sysRoleMapper.selectRoleIdsByUserId(userId);
        return new HashSet<>();
    }

    /**
     * 获取 HttpServletRequest
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 获取客户端 IP
     */
    private String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个代理时取第一个 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}
