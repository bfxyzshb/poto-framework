package com.weibo.poto.bus.event;

import com.weibo.poto.bus.Bus;
import com.weibo.poto.bus.common.MessageHandler;

public interface EventBus extends Bus<EventMessage, Object> {
    void subscribe(MessageHandler<EventMessage> handler);

    void dispatchAll(Object object);
}
