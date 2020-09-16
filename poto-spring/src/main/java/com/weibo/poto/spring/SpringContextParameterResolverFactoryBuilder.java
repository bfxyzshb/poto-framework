package com.weibo.poto.spring;

import com.weibo.poto.bus.common.ClasspathParameterResolverFactory;
import com.weibo.poto.bus.common.ParametersResolverFactory;
import com.weibo.poto.bus.common.SpringBeanParametersResolverFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.util.ClassUtils;

public final class SpringContextParameterResolverFactoryBuilder implements SpringBeanReferenceBuilder {


    @Override
    public RuntimeBeanReference getBeanReference(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(PARAMETER_RESOLVER_FACTORY_BEAN_NAME)) {
            final ManagedList<BeanDefinition> factories = new ManagedList<BeanDefinition>();
            factories.add(BeanDefinitionBuilder.genericBeanDefinition(ClasspathParameterResolverFactoryBean.class)
                    .getBeanDefinition());
            factories.add(BeanDefinitionBuilder.genericBeanDefinition(SpringBeanParametersResolverFactory.class)
                    .getBeanDefinition());
            AbstractBeanDefinition def =
                    BeanDefinitionBuilder.genericBeanDefinition(ParameterResolverFactoryProxy.class)
                            .addConstructorArgValue(factories)
                            .getBeanDefinition();
            registry.registerBeanDefinition(PARAMETER_RESOLVER_FACTORY_BEAN_NAME, def);
        }
        return new RuntimeBeanReference(PARAMETER_RESOLVER_FACTORY_BEAN_NAME);
    }

    private static class ClasspathParameterResolverFactoryBean implements BeanClassLoaderAware, InitializingBean,
            FactoryBean<ParametersResolverFactory> {

        private ClassLoader classLoader;
        private ParametersResolverFactory factory;

        @Override
        public ParametersResolverFactory getObject() throws Exception {
            return factory;
        }

        @Override
        public Class<?> getObjectType() {
            return ParametersResolverFactory.class;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            this.factory = ClasspathParameterResolverFactory.forClassLoader(classLoader);
        }

        @Override
        public void setBeanClassLoader(ClassLoader classLoader) {
            this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
        }
    }
}