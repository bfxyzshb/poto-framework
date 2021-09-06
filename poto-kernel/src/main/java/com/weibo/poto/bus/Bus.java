package com.weibo.poto.bus;


import com.weibo.poto.bus.command.Command;

public interface Bus<T,R> {

    R dispatch(T object);
}
