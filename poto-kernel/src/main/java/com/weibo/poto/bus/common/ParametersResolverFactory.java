package com.weibo.poto.bus.common;

import java.lang.annotation.Annotation;

/**
 * 参数解析工厂类
 * 1.spring bean ParameterResolver
 * 2.
 */
public interface ParametersResolverFactory {

    ParameterResolver createParameterResolver(Annotation[] memberAnnotations, Class<?> parameterType, Annotation[] parameterAnnotations);

}
