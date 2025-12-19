package com.junoyi.framework.security.helper;

import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;
import com.junoyi.framework.security.module.LoginUser;
import com.junoyi.framework.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Token工具类
 * 提供Token相关的辅助功能
 * @author Fan
 */
@Component
@RequiredArgsConstructor
public class TokenHelper {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(TokenHelper.class);

    private SecurityProperties securityProperties;

    /**
     * 生成AccessToken
     * @return 返回AccessToken
     */
    public String createAccessToken(LoginUser loginUser){
        return null;
    }

    /**
     * 生成RefreshToken
     * @return 返回RefreshToken
     */
    public String createRefreshToken(){

        return null;
    }
}