package com.junoyi.system.domain.dto;


import lombok.Data;

/**
 * 登录请求数据传输对象
 * 用于封装用户登录时所需的各种认证信息
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     * 用于用户身份标识
     */
    private String username;

    /**
     * 邮箱地址
     * 用户注册或登录使用的电子邮箱
     */
    private String email;

    /**
     * 手机号码
     * 用户注册或登录使用的手机号码
     */
    private String phonenumber;

    /**
     * 密码
     * 用户登录凭证
     */
    private String password;

    /**
     * 唯一标识符
     * 用于设备或会话的唯一性标识
     */
    private String uuid;

    /**
     * 验证码
     * 短信或邮件验证码，用于二次验证
     */
    private String code;
}
