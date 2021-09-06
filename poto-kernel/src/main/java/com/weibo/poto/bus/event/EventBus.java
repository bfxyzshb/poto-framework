package com.weibo.poto.bus.event;

import com.weibo.poto.bus.Bus;
import com.weibo.poto.bus.common.MessageHandler;

<<<<<<< HEAD
public interface EventBus<T, R> extends Bus<T, R> {
    void subscribe(MessageHandler<T> handler);

=======
public interface EventBus<T,R> extends Bus<R> {
    void subscribe(MessageHandler<T> handler);
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
    void dispatchAll(Object object);
}
