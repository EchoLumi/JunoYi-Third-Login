package com.junoyi.system.domain.vo;


import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 用户信息 vo 数据实体
 * 用于响应给前端的用户信息数据传输实体
 *
 * @author Fan
 */
@Data
@Builder
public class UserInfoVo {

    private Long userId;

    private String userName;

    private String nickName;

    private String avatar;

    private List<?> permissions;

    private List<?> roles;

}