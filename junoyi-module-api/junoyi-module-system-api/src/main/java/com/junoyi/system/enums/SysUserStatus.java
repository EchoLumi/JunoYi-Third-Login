package com.junoyi.system.enums;

/**
 * 系统用户状态枚举类
 * 用于定义系统用户的启用和禁用状态
 *
 * @author Fan
 */
public enum SysUserStatus {
    /**
     * 启用状态
     */
    ENABLE(1,"启用"),

    /**
     * 禁用状态
     */
    DISABLE(0, "禁用");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态标签
     */
    private final String label;

    /**
     * 构造函数
     * @param code 状态码
     * @param label 状态标签
     */
    private SysUserStatus(int code, String label){
        this.code = code;
        this.label = label;
    }

    /**
     * 获取状态码
     * @return 状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取状态标签
     * @return 状态标签
     */
    public String getLabel() {
        return label;
    }
}
