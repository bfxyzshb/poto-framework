package com.weibo.poto.bus.command;


import com.weibo.poto.bus.Bus;
import com.weibo.poto.bus.common.MessageHandler;

public interface CommandBus extends Bus {

    <C> void subscribe(String commandName, MessageHandler<? super C> handler);

}
