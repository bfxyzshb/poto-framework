package com.weibo.poto.bus.common;

import com.weibo.poto.domain.Message;

import java.lang.annotation.Annotation;

public class DefaultParameterResolverFactory implements ParametersResolverFactory {

    @Override
    public ParameterResolver createParameterResolver(Annotation[] methodAnnotations, Class<?> parameterType,
                                                     Annotation[] parameterAnnotations) {
        if (Message.class.isAssignableFrom(parameterType)) {
            return new MessageParameterResolver(parameterType);
        }
        return null;
    }

    private static class MessageParameterResolver implements ParameterResolver {

        private final Class<?> parameterType;

        public MessageParameterResolver(Class<?> parameterType) {
            this.parameterType = parameterType;
        }

        @Override
        public Object resolveParameterValue(Message message) {
            return message;
        }

        @Override
        public boolean matches(Message message) {
            return parameterType.isInstance(message);
        }
    }
}