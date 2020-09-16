package com.weibo.poto.spring;

import com.weibo.poto.bus.command.CommandDispatchInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CommandBusReferenceBuilder implements SpringBeanReferenceBuilder {

    @Override
    public RuntimeBeanReference getBeanReference(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(COMMAND_BUS_NAME)) {
            //Interceptors
            AbstractBeanDefinition def = BeanDefinitionBuilder.genericBeanDefinition(ClassPathCommandInterceptorFactoryBean.class).getBeanDefinition();
            registry.registerBeanDefinition(COMMAND_BUS_NAME, def);
        }
        return new RuntimeBeanReference(COMMAND_BUS_NAME);
    }

    private static class ClassPathCommandInterceptorFactoryBean implements FactoryBean<List<CommandDispatchInterceptor>>, InitializingBean, ApplicationContextAware {
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
            //todo spi扩展




            Map<String, CommandDispatchInterceptor> interceptorMap = applicationContext.getBeansOfType(CommandDispatchInterceptor.class);
            interceptorMap.forEach((k, v) -> {
                this.list.add(v);
            });
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }
    }


}
