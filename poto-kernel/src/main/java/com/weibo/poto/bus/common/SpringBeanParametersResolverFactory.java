package com.weibo.poto.bus.common;

import com.weibo.poto.domain.Message;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

public class SpringBeanParametersResolverFactory implements ParametersResolverFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 处理方法参数 为spring bean
     *
     * @param memberAnnotations
     * @param parameterType
     * @param parameterAnnotations
     * @return
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public ParameterResolver createParameterResolver(Annotation[] memberAnnotations, Class<?> parameterType,
                                            Annotation[] parameterAnnotations) {
        if (applicationContext == null) {
            return null;
        }
        Map<String, ?> beansFound = applicationContext.getBeansOfType(parameterType);
        if (beansFound.isEmpty()) {
            return null;
        } else if (beansFound.size() > 1) {
            final AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
            if (beanFactory instanceof ConfigurableListableBeanFactory) {
                for (Map.Entry<String, ?> bean : beansFound.entrySet()) {
                    final ConfigurableListableBeanFactory clBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
                    if (clBeanFactory.containsBeanDefinition(bean.getKey())
                            && clBeanFactory.getBeanDefinition(bean.getKey()).isPrimary()) {
                        return new SpringBeanParameterResolver(beanFactory, bean.getKey());
                    }
                }
            }
            return null;
        } else {
            return new SpringBeanParameterResolver(applicationContext.getAutowireCapableBeanFactory(),
                    beansFound.keySet().iterator().next());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    private static class SpringBeanParameterResolver implements ParameterResolver<Object> {

        private final AutowireCapableBeanFactory beanFactory;
        private final String beanName;

        public SpringBeanParameterResolver(AutowireCapableBeanFactory beanFactory, String beanName) {
            this.beanFactory = beanFactory;
            this.beanName = beanName;
        }

        @Override
        public Object resolveParameterValue(Message message) {
            return beanFactory.getBean(beanName);
        }

        @Override
        public boolean matches(Message message) {
            return true;
        }
    }
}