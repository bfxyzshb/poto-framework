package com.weibo.poto.bus;


import com.weibo.poto.bus.command.Response;

public interface Bus<R> {

    R dispatch(Object object);
}
