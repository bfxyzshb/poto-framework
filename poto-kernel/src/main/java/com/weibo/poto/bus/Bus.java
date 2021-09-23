package com.weibo.poto.bus;


public interface Bus<T,R> {

    R dispatch(T object);
}

