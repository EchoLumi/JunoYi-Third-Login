package com.junoyi.framework.event.domain.spring;


import com.junoyi.framework.event.domain.BaseEvent;
import org.springframework.boot.ConfigurableBootstrapContext;

/**
 * SpringApplicationStartingEvent类表示Spring应用程序启动事件
 * 该事件在Spring应用启动时触发，继承自BaseEvent基类
 *
 * @author Fan
 */
public class SpringApplicationStartingEvent extends BaseEvent {

    private final ConfigurableBootstrapContext bootstrapContext;

    /**
     * 构造函数，创建一个SpringApplicationStartingEvent实例
     * @param bootstrapContext 可配置的引导上下文对象，用于获取启动阶段的配置信息
     */
    public SpringApplicationStartingEvent(ConfigurableBootstrapContext bootstrapContext){
        this.bootstrapContext = bootstrapContext;
    }

    /**
     * 获取引导上下文对象
     * @return 返回ConfigurableBootstrapContext类型的引导上下文对象
     */
    public ConfigurableBootstrapContext getBootstrapContext(){
        return bootstrapContext;
    }
}
