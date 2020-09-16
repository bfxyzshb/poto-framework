package com.weibo.poto.spring;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public interface SpringBeanReferenceBuilder {
    static final String PARAMETER_RESOLVER_FACTORY_BEAN_NAME = "-parameterResolverFactory";
    static final String COMMAND_BUS_NAME = "-command_bus";
    static final String EVENT_BUS_NAME = "-event_bus";

    public RuntimeBeanReference getBeanReference(BeanDefinitionRegistry registry);
}
