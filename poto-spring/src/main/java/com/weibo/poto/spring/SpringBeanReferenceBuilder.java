package com.weibo.poto.spring;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public interface SpringBeanReferenceBuilder {
    String PARAMETER_RESOLVER_FACTORY_BEAN_NAME = "-parameterResolverFactory";
    String COMMAND_BUS_NAME = "-command_bus";
    String EVENT_BUS_NAME = "-event_bus";

    String EXTENSIONEXECTOR_NAME = "-EXTENSIONEXECTOR_NAME";

    RuntimeBeanReference getBeanReference(BeanDefinitionRegistry registry);
}