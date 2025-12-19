package com.junoyi.system.controller;

import com.junoyi.framework.core.domain.base.BaseController;
import com.junoyi.framework.core.domain.module.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统认证控制器
 * 处理用户登录认证和用户信息获取相关接口
 *
 * @author Fan
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SysAuthController extends BaseController {

    /**
     * 用户登录接口
     * 处理用户登录请求，验证用户身份并返回认证结果
     *
     * @return R<?> 统一响应结果，包含登录状态和相关信息
     */
    @PostMapping("/login")
    public R<?> login(){

        return R.ok();
    }

    /**
     * 刷新accessToken
     * 客户端通过传递refreshToken，验证refreshToken是否有效，
     * 来刷新accessToken，从而延长登录时间
     *
     * @return R<?> 统一响应结果
     */
    @PostMapping("/refresh")
    public R<?> refresh(){

        return R.ok();
    }


    /**
     * 获取用户信息接口
     * 根据当前认证用户获取其详细信息
     *
     * @return R<?> 统一响应结果，包含用户信息数据
     */
    @GetMapping("/info")
    public R<?> getUserInfo(){
        return R.ok();
    }
}
