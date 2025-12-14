package com.junoyi.framework.event.domain.spring;


import com.junoyi.framework.event.domain.BaseEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Duration;


/**
 * Spring 中 ApplicationReadyEvent
 *
 * @author Fan
 */
public class SpringApplicationReadyEvent extends BaseEvent {

    private final ConfigurableApplicationContext context;

    private final Duration timeTaken;


    /**
     * 构造方法，初始化 SpringApplicationReadyEvent 实例
     *
     * @param context 应用程序上下文对象
     */
    public SpringApplicationReadyEvent(ConfigurableApplicationContext context, Duration timeTaken){
        this.context = context;
        this.timeTaken = timeTaken;
    }

    /**
     * 获取应用程序上下文对象
     *
     * @return ConfigurableApplicationContext 应用程序上下文实例
     */
    public ConfigurableApplicationContext getApplicationContext(){
        return context;
    }

    public Duration getTimeTaken(){
        return timeTaken;
    }

}
