package com.weibo.poto.bus.common;


public interface MessageHandler<T> {

    Object handle(T message) throws Throwable;
}