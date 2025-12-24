package com.junoyi.framework.captcha.enums;

/**
 * 验证码类型枚举
 *
 * @author Fan
 */
public enum CaptchaType {
    /**
     * 图片验证码 - 传统数字/字母验证码
     */
    IMAGE,
    /**
     * 滑块验证码 - 拖动滑块到指定位置
     */
    SLIDER,
    /**
     * 点选验证码 - 按顺序点击指定文字
     */
    CLICK,
    /**
     * 行为验证码 - 基于用户行为分析
     */
    BEHAVIOR
}
