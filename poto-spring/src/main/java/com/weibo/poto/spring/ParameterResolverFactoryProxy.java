package com.weibo.poto.spring;

import com.weibo.poto.bus.common.ParametersResolverFactory;
import com.weibo.poto.bus.common.ParametersResolverFactoryImpl;
import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.List;

public class ParameterResolverFactoryProxy implements FactoryBean<ParametersResolverFactory> {

    private final List<ParametersResolverFactory> factories;
    private ParametersResolverFactory parametersResolverFactory;

    public ParameterResolverFactoryProxy(List<ParametersResolverFactory> defaultFactories) {
        this.factories = new ArrayList<ParametersResolverFactory>(defaultFactories);
    }

    @Override
    public ParametersResolverFactory getObject() throws Exception {
        return new ParametersResolverFactoryImpl(factories);
    }

    @Override
    public Class<?> getObjectType() {
        return ParametersResolverFactoryImpl.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


}