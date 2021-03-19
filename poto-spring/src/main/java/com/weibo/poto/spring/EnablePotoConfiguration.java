package com.weibo.poto.spring;

import com.weibo.poto.bus.command.DefaultCommandBus;
import com.weibo.poto.bus.event.DefaultEventBus;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;


public class EnablePotoConfiguration implements ImportBeanDefinitionRegistrar {

    private static final String EVENT_HANDLER_BEAN_NAME = "poto-event-handler";

    private static final String COMMAND_HANDLER_BEAN_NAME = "poto-command-handler";

    private static final String DEFAULT_COMMAND_BUS = "command-bus";
    private static final String DEFAULT_EVENT_BUS = "event-bus";


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerAnnotationEventHandleBeanPostProcessor(registry, CommandHandlerBeanPostProcessor.class, COMMAND_HANDLER_BEAN_NAME);
        registerAnnotationEventHandleBeanPostProcessor(registry, EventHandlerBeanPostProcessor.class, EVENT_HANDLER_BEAN_NAME);
        //注册commandBus 避免spring boot 配置扫描包scan
        registerCommandBus(registry, DefaultCommandBus.class, DEFAULT_COMMAND_BUS, SpringBeanReferenceBuilder.COMMAND_BUS_NAME);
        registerEventBus(registry, DefaultEventBus.class, DEFAULT_EVENT_BUS, SpringBeanReferenceBuilder.EVENT_BUS_NAME);

    }

    public void registerAnnotationEventHandleBeanPostProcessor(BeanDefinitionRegistry registry, Class<?> beanClass, String beanName) {
        if (beanClass == null) {
            return;
        }
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.getPropertyValues().add("parametersResolverFactory",
                SpringBeanReferenceBuilderFactory.getSpringBeanReferenceBuilderFactory(SpringBeanReferenceBuilder.PARAMETER_RESOLVER_FACTORY_BEAN_NAME).getBeanReference(registry));
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 注册Bus
     */
    public void registerCommandBus(BeanDefinitionRegistry registry, Class<?> beanClass, String beanName, String name) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.getPropertyValues().add("interceptors",
                SpringBeanReferenceBuilderFactory.getSpringBeanReferenceBuilderFactory(name).getBeanReference(registry));
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    public void registerEventBus(BeanDefinitionRegistry registry, Class<?> beanClass, String beanName, String name) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        registry.registerBeanDefinition(beanName, beanDefinition);
    }


}
