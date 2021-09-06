package com.weibo.poto.spring;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public interface SpringBeanReferenceBuilder {
    String PARAMETER_RESOLVER_FACTORY_BEAN_NAME = "-parameterResolverFactory";
    String COMMAND_BUS_NAME = "-command_bus";
    String EVENT_BUS_NAME = "-event_bus";

<<<<<<< HEAD
    String EXTENSIONEXECTOR_NAME = "-EXTENSIONEXECTOR_NAME";

=======
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
    RuntimeBeanReference getBeanReference(BeanDefinitionRegistry registry);
}
