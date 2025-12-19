package com.junoyi.framework.security.module;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 登录用户信息类
 * 用于封装用户登录后的基本信息、权限、角色等数据
 *
 * @author Fan
 */
@Data
@Builder
public class LoginUser {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户权限列表
     */
    private List<?> permissions;

    /**
     * 用户角色列表
     */
    private List<?> roles;

    /**
     * 登录IP地址
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date loginTime;
}
