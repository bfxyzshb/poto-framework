package com.weibo.poto.bus.common;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ParametersResolverFactoryImpl implements ParametersResolverFactory {

    private final ParametersResolverFactory[] factories;

    public ParametersResolverFactoryImpl(List<ParametersResolverFactory> list) {
        this.factories = list.toArray(new ParametersResolverFactory[list.size()]);
    }

    @Override
    public ParameterResolver createParameterResolver(Annotation[] memberAnnotations, Class<?> parameterType, Annotation[] parameterAnnotations) {
        for (ParametersResolverFactory factory : factories) {
            ParameterResolver resolver = factory.createParameterResolver(memberAnnotations, parameterType, parameterAnnotations);
            if (resolver != null) {
                return resolver;
            }
        }
        return null;
    }
}