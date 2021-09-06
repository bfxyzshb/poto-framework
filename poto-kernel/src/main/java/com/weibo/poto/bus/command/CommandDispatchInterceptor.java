package com.weibo.poto.bus.command;

import com.weibo.poto.spi.annotation.SPI;

@SPI
public interface CommandDispatchInterceptor {

<<<<<<< HEAD
    boolean preIntercept(Command command);

    default void postIntercept(Command command, Response response) {
=======
    default void preIntercept(CommandMessage commandMessage) {
    }

    default void postIntercept(CommandMessage commandMessage, Response response) {
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
    }
}