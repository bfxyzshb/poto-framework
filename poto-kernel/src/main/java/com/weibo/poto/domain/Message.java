package com.weibo.poto.domain;

import java.io.Serializable;

public interface Message extends Serializable {

    Object getHandle();

    Class getHandleType();

}