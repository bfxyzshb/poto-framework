package com.weibo.poto.bus;


public interface Bus<R> {

    R dispatch(Object object);
}
