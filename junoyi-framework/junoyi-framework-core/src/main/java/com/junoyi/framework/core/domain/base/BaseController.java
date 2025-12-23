package com.junoyi.framework.core.domain.base;


import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;

/**
 * BaseController类是所有控制器类的基类
 * 提供基础的日志记录功能，子类控制器可以直接使用log对象进行日志输出
 *
 * @author Fan
 */
public class BaseController {
    /**
     * 日志记录器实例
     * 通过JunoYiLogFactory工厂创建，用于记录控制器层的日志信息
     */
    protected final JunoYiLog log = JunoYiLogFactory.getLogger(this.getClass());

}
