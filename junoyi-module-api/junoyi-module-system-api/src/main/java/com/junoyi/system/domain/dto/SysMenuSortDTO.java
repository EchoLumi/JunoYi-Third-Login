package com.junoyi.system.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * 系统菜单排序数据传输对象
 * 用于批量更新菜单排序，支持拖拽排序场景
 *
 * @author Fan
 */
@Data
public class SysMenuSortDTO {

    /**
     * 菜单排序项列表
     */
    private List<SortItem> items;

    /**
     * 单个菜单排序项
     */
    @Data
    public static class SortItem {
        /**
         * 菜单ID
         */
        private Long id;

        /**
         * 父级菜单ID（支持拖拽改变层级）
         */
        private Long parentId;

        /**
         * 排序值
         */
        private Integer sort;
    }
}
