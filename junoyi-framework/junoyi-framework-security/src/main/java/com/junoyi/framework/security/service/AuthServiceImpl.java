package com.junoyi.framework.security.service;

import com.junoyi.framework.security.module.LoginUser;
import com.junoyi.framework.security.session.SessionService;
import com.junoyi.framework.security.session.UserSession;
import com.junoyi.framework.security.token.TokenPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 认证服务实现类
 *
 * @author Fan
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SessionService sessionService;

    @Override
    public TokenPair login(LoginUser loginUser, String loginIp, String userAgent) {
        return sessionService.login(loginUser, loginIp, userAgent);
    }

    @Override
    public TokenPair login(LoginUser loginUser) {
        return sessionService.login(loginUser, null, null);
    }

    @Override
    public boolean logout(String token) {
        return sessionService.logout(token);
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        return sessionService.refreshToken(refreshToken);
    }

    @Override
    public LoginUser getLoginUser(String token) {
        return sessionService.getLoginUser(token);
    }

    @Override
    public UserSession getSession(String token) {
        return sessionService.getSession(token);
    }

    @Override
    public UserSession getSessionByTokenId(String tokenId) {
        return sessionService.getSessionByTokenId(tokenId);
    }

    @Override
    public boolean isValid(String token) {
        return sessionService.isValid(token);
    }

    @Override
    public boolean updatePermissions(String tokenId, LoginUser loginUser) {
        return sessionService.updateSession(tokenId, loginUser);
    }

    @Override
    public List<UserSession> getUserSessions(Long userId) {
        return sessionService.getUserSessions(userId);
    }

    @Override
    public boolean kickOut(String tokenId) {
        return sessionService.kickOut(tokenId);
    }

    @Override
    public int kickOutAll(Long userId) {
        return sessionService.kickOutAll(userId);
    }
}
