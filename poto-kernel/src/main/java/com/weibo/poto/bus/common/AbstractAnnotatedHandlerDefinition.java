package com.weibo.poto.bus.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public abstract class AbstractAnnotatedHandlerDefinition<T extends Annotation>
        implements HandlerDefinition<AccessibleObject> {

    private final Class<T> annotationType;

    protected AbstractAnnotatedHandlerDefinition(Class<T> annotationType) {
        this.annotationType = annotationType;
    }

    /**
     * annotationType 注解是否在此方法
     * @param member
     * @return
     */
    @Override
    public boolean isMessageHandler(AccessibleObject member) {
        return member.isAnnotationPresent(annotationType);
    }

    @Override
    public Class<?> resolveHandleFor(AccessibleObject member) {
        T annotation = member.getAnnotation(annotationType);
        Class<?> definedHandle = null;
        if (annotation != null) {
            definedHandle = getDefinedHandle(annotation);
            if (definedHandle == Void.class) {
                return null;
            }
        }
        return definedHandle;
    }

    protected abstract Class<?> getDefinedHandle(T annotation);

    @Override
    public String toString() {
        return "AnnotatedHandler{" + annotationType + '}';
    }
}