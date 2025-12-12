package com.junoyi.demo.listener;

import com.junoyi.demo.event.TestEvent;
import com.junoyi.framework.event.annotation.EventHandler;
import com.junoyi.framework.event.annotation.EventListener;
import com.junoyi.framework.event.enums.EventPriority;

@EventListener
public class Test2EventListener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTestEven32t(TestEvent event){
        System.out.println("优先级 最高 321321321");
    }
}