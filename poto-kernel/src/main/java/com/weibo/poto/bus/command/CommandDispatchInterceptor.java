package com.weibo.poto.bus.command;

import com.weibo.poto.spi.annotation.SPI;

@SPI
public interface CommandDispatchInterceptor {

    default void preIntercept(CommandMessage commandMessage) {
    }

    default void postIntercept(CommandMessage commandMessage, Response response) {
    }
}