package com.weibo.poto.bus.common;

import com.weibo.poto.domain.Message;

/**
 * 参数解析接口类
 *
 * @param <T>
 */
public interface ParameterResolver<T> {

    T resolveParameterValue(Message message);

    boolean matches(Message message);
}
