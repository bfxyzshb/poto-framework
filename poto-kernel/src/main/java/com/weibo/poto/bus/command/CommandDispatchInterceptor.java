package com.weibo.poto.bus.command;

public interface CommandDispatchInterceptor {

    default void preIntercept(CommandMessage commandMessage) {
    }

    default void postIntercept(CommandMessage commandMessage, Response response) {
    }
}