package com.weibo.poto.bus.command;


import com.weibo.poto.bus.Bus;
import com.weibo.poto.bus.common.MessageHandler;

public interface CommandBus<T, R> extends Bus<T, R> {

    <C> void subscribe(String commandName, MessageHandler<? super C> handler);

}
