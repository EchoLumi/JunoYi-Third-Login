package com.junoyi.system.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class RouterVo {

    /**
     * 菜单 ID
     */
    private Long id;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 组件路径
     */
    private String component;

    private Meta meta;

    private List<RouterVo> children;


    @Data
    public static class Meta {

        private String title;

        private String icon;

        private Boolean keepAlive;

        private Boolean fixedTab;

        private List<?> permissionList;
    }
}