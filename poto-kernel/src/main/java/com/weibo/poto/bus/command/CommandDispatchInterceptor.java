package com.weibo.poto.bus.command;

import com.weibo.poto.spi.annotation.SPI;

@SPI
public interface CommandDispatchInterceptor {

    boolean preIntercept(Command command);

    default void postIntercept(Command command, Response response) {
    }
}