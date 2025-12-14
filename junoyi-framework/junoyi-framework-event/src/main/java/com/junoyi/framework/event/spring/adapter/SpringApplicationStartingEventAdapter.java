package com.junoyi.framework.event.spring.adapter;

import com.junoyi.framework.event.core.Event;
import com.junoyi.framework.event.domain.spring.SpringApplicationStartingEvent;
import com.junoyi.framework.event.spring.SpringEventAdapter;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.stereotype.Component;

/**
 * @author Fan
 */
@Component
public class SpringApplicationStartingEventAdapter implements SpringEventAdapter<ApplicationStartingEvent> {

    @Override
    public boolean supports(Object springEvent) {
        return springEvent instanceof ApplicationStartingEvent;
    }

    @Override
    public Event adapt(Object springEvent) {
        ApplicationStartingEvent applicationStartingEvent = (ApplicationStartingEvent) springEvent;
        return new SpringApplicationStartingEvent(
                applicationStartingEvent.getBootstrapContext()
        );
    }
}