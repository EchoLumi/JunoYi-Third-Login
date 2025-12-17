package com.junoyi.framework.core.domain.base;

/**
 * 基础异常类
 * <p>
 * 所有业务异常都应该继承此类，作为系统异常处理体系的基础类。
 * 该类继承自RuntimeException，属于非受检异常，无需显式声明或捕获。
 * </p>
 *
 * @author Fan
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
