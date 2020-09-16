package com.weibo.poto.bus.event;

import com.weibo.poto.bus.Bus;
import com.weibo.poto.bus.common.MessageHandler;

public interface EventBus<T,R> extends Bus<R> {
    void subscribe(MessageHandler<T> handler);
    void dispatchAll(Object object);
}
