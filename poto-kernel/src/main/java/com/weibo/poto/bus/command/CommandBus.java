package com.weibo.poto.bus.command;


import com.weibo.poto.bus.Bus;
import com.weibo.poto.bus.common.MessageHandler;

<<<<<<< HEAD
public interface CommandBus<T, R> extends Bus<T, R> {
=======
public interface CommandBus<R> extends Bus<R> {
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b

    <C> void subscribe(String commandName, MessageHandler<? super C> handler);

}
