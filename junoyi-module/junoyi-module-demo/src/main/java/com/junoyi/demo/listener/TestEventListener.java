package com.junoyi.demo.listener;

import com.junoyi.framework.event.annotation.EventListener;
import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;

/**
 * Test事件监听器
 */
@EventListener
public class TestEventListener {
    private final JunoYiLog log = JunoYiLogFactory.getLogger(TestEventListener.class);


}