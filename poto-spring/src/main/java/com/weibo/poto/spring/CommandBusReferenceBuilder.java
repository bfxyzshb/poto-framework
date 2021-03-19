package com.weibo.poto.spring;

import com.google.common.collect.Lists;
import com.weibo.poto.bus.command.CommandDispatchInterceptor;
import com.weibo.poto.bus.command.DefaultCommandBus;
import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.SPILoader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CommandBusReferenceBuilder implements SpringBeanReferenceBuilder {

    @Override
    public RuntimeBeanReference getBeanReference(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(COMMAND_BUS_NAME)) {
            //Interceptors
            AbstractBeanDefinition interceptorDef = BeanDefinitionBuilder.genericBeanDefinition(ClassPathCommandInterceptorFactoryBean.class).getBeanDefinition();
            registry.registerBeanDefinition(COMMAND_BUS_NAME, interceptorDef);
        }
        return new RuntimeBeanReference(COMMAND_BUS_NAME);
    }

    private static class ClassPathCommandInterceptorFactoryBean implements FactoryBean<List<CommandDispatchInterceptor>>, InitializingBean, ApplicationContextAware, BeanClassLoaderAware {
        private ClassLoader classLoader;
        private ApplicationContext applicationContext;
        private List<CommandDispatchInterceptor> list = new ArrayList<>();

        public ClassPathCommandInterceptorFactoryBean() {
        }

        @Override
        public List<CommandDispatchInterceptor> getObject() throws Exception {
            return this.list;
        }

        @Override
        public Class<?> getObjectType() {
            return ArrayList.class;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            Map<String, Protocol> protocolMap = applicationContext.getBeansOfType(Protocol.class);
            Protocol protocol = new DefaultProtocal();
            if (MapUtils.isNotEmpty(protocolMap)) {
                protocol = protocolMap.values().stream().findFirst().get();
            }
            SPILoader<CommandDispatchInterceptor> commandDispatchInterceptorSPILoader = SPILoader.getExtensionLoader(CommandDispatchInterceptor.class);
            CommandDispatchInterceptor commandDispatchInterceptor = commandDispatchInterceptorSPILoader.getAdaptiveExtension();
            list.add(commandDispatchInterceptor);
            List<CommandDispatchInterceptor> commandDispatchInterceptorList = commandDispatchInterceptorSPILoader.getActivateExtension(protocol, null);
            this.list.addAll(CollectionUtils.isEmpty(commandDispatchInterceptorList) ? Lists.newArrayList() : commandDispatchInterceptorList);
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public void setBeanClassLoader(ClassLoader classLoader) {
            this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
        }
    }


}
