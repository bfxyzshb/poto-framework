package com.weibo.poto.entity;

import java.io.Serializable;

public interface Identifier<T> extends Serializable {

    T getValue();
}
