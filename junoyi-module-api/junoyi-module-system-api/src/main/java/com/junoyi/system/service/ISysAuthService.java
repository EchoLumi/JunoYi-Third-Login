package com.junoyi.system.service;

import com.junoyi.framework.security.module.LoginUser;
import com.junoyi.system.domain.bo.LoginBO;
import com.junoyi.system.domain.vo.AuthVo;
import com.junoyi.system.domain.vo.UserInfoVo;

/**
 * 系统验证认证业务接口类
 *
 * @author Fan
 */
public interface ISysAuthService {


    AuthVo login(LoginBO loginBO);

    UserInfoVo getUserInfo(LoginUser loginUser);
}
