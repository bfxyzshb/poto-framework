package com.weibo.poto.spring;

import com.weibo.poto.bus.common.ParametersResolverFactory;
import com.weibo.poto.bus.event.EventHandlerAdapter;
import com.weibo.poto.bus.event.EventBus;
import com.weibo.poto.bus.event.annotation.EventHandler;
import lombok.Setter;

import java.util.Map;

public class EventHandlerBeanPostProcessor extends AbstractHandlerBeanPostProcessor<EventHandlerAdapter> {

    @Setter
    private EventBus eventBus;

    public EventHandlerBeanPostProcessor() {
        super(EventHandler.class);
    }

    @Override
    protected void subscribe(EventHandlerAdapter adapter) {
        ensureEventBusInit();
        eventBus.subscribe(adapter);
    }

    @Override
    protected EventHandlerAdapter initializeAdapterFor(Object bean, ParametersResolverFactory parametersResolverFactory) {
        return new EventHandlerAdapter(bean, parametersResolverFactory);
    }

    private void ensureEventBusInit() {
        if (eventBus == null) {
            Map<String, EventBus> beans = getApplicationContext().getBeansOfType(EventBus.class);
            if (beans.size() != 1) {
                throw new IllegalStateException(
                        "the application context must " + "contain exactly one bean of type EventBus. The current application context has: " + beans.size());
            } else {
                this.eventBus = beans.entrySet().iterator().next().getValue();
            }
        }
    }
}
