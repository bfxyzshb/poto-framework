package com.weibo.poto.spring;

import com.weibo.poto.bus.Bus;
import com.weibo.poto.bus.common.ClasspathParameterResolverFactory;
import com.weibo.poto.bus.common.ParametersResolverFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractHandlerBeanPostProcessor<T>
        implements BeanPostProcessor, ApplicationContextAware {

    private ParametersResolverFactory parametersResolverFactory;
    private ApplicationContext applicationContext;
    private final Class<? extends Annotation> annotationClass;

    protected AbstractHandlerBeanPostProcessor(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 后置处理器
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        final ClassLoader classLoader = targetClass.getClassLoader();
        if (parametersResolverFactory == null) {
            parametersResolverFactory = ClasspathParameterResolverFactory.forClassLoader(classLoader);
        }
        //带有@CommandHandler @EventHandler注解
        if (isContentAnnotation(targetClass, annotationClass)) {
            //初始化适配器
            T adapter = initializeAdapterFor(bean, parametersResolverFactory);
            subscribe(adapter);
            return adapter;
        }
        return bean;
    }

    protected boolean isContentAnnotation(Class<?> targetClass, Class<? extends Annotation> annotationClass) {
        return isNotHandlerSubclass(targetClass) && hasAnnotationMethod(targetClass, annotationClass);
    }


    protected abstract void subscribe(T adapter);

    protected abstract T initializeAdapterFor(Object bean, ParametersResolverFactory parametersResolverFactory);

    protected boolean isNotHandlerSubclass(Class<?> beanClass) {
        return !Bus.class.isAssignableFrom(beanClass);
    }


    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private boolean hasAnnotationMethod(Class<?> beanClass, Class<? extends Annotation> annotationClass) {
        final AtomicBoolean result = new AtomicBoolean(false);
        ReflectionUtils.doWithMethods(beanClass, new HasEventHandlerAnnotationMethodCallback(result, annotationClass));
        return result.get();
    }

    private static final class HasEventHandlerAnnotationMethodCallback implements ReflectionUtils.MethodCallback {

        private final AtomicBoolean result;
        private Class<? extends Annotation> annotationClass;

        private HasEventHandlerAnnotationMethodCallback(AtomicBoolean result, Class<? extends Annotation> annotationClass) {
            this.result = result;
            this.annotationClass = annotationClass;
        }

        @Override
        public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
            if (method.isAnnotationPresent(annotationClass)) {
                result.set(true);
            }
        }
    }

    public void setParametersResolverFactory(ParametersResolverFactory parametersResolverFactory) {
        this.parametersResolverFactory = parametersResolverFactory;
    }
}