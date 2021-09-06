package com.weibo.poto.bus.common;

import com.weibo.poto.domain.Message;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class AbstractMessageHandler implements Comparable<AbstractMessageHandler> {

    private final Score score;
    private final Class<?> HandleType;
    private final ParameterResolver[] parameterValueResolvers;

    protected AbstractMessageHandler(Class<?> HandleType, Class<?> declaringClass,
                                     ParameterResolver... parameterValueResolvers) {
        this.score = new Score(HandleType, declaringClass);
        this.HandleType = HandleType;
        this.parameterValueResolvers = Arrays.copyOf(parameterValueResolvers, parameterValueResolvers.length);
    }


    protected AbstractMessageHandler(AbstractMessageHandler delegate) {
        this.score = delegate.score;
        this.HandleType = delegate.HandleType;
        this.parameterValueResolvers = delegate.parameterValueResolvers;
    }


    public boolean matches(Message message) {
        Assert.notNull(message, "Event may not be null");
        //校验第一个参数 HandleType 是否为message.getHandleType()的父类
        if (HandleType != null && !HandleType.isAssignableFrom(message.getHandleType())) {
            return false;
        }
        for (ParameterResolver parameterResolver : parameterValueResolvers) {
            if (!parameterResolver.matches(message)) {
                return false;
            }
        }
        return true;
    }

    public abstract Object invoke(Object target, Message message)
            throws InvocationTargetException, IllegalAccessException;

    public Class getHandleType() {
        return HandleType;
    }

    @Override
    public int compareTo(AbstractMessageHandler o) {
        return score.compareTo(o.score);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AbstractMessageHandler)
                && ((AbstractMessageHandler) obj).score.equals(score);
    }

    @Override
    public int hashCode() {
        return score.hashCode();
    }

    /**
     * 获取参数解析类
     *
     * @param parametersResolverFactory
     * @param memberAnnotations
     * @param parameterTypes
     * @param parameterAnnotations
     * @param resolveHandle
     * @return
     */
    protected static ParameterResolver[] findResolvers(ParametersResolverFactory parametersResolverFactory,
                                                       Annotation[] memberAnnotations, Class<?>[] parameterTypes,
                                                       Annotation[][] parameterAnnotations, boolean resolveHandle) {
        int parameters = parameterTypes.length;
        ParameterResolver[] parameterValueResolvers = new ParameterResolver[parameters];
        for (int i = 0; i < parameters; i++) {
            //第一个参数默认为event参数
            final boolean isHandleParameter = resolveHandle && i == 0;
            //判断parameterTypes[i]是否为Message类的父类
            if (isHandleParameter && !Message.class.isAssignableFrom(parameterTypes[i])) {
                parameterValueResolvers[i] = new HandleParameterResolver(parameterTypes[i]);
            } else {
                parameterValueResolvers[i] = parametersResolverFactory.createParameterResolver(memberAnnotations,
                        parameterTypes[i],
                        parameterAnnotations[i]);
            }
        }
        return parameterValueResolvers;
    }


    protected ParameterResolver[] getParameterValueResolvers() {
        return parameterValueResolvers;
    }


    public abstract <T extends Annotation> T getAnnotation(Class<T> annotationType);

    private static class HandleParameterResolver implements ParameterResolver {

        private final Class<?> handleType;

        public HandleParameterResolver(Class<?> handleType) {
            this.handleType = handleType;
        }

        @Override
        public Object resolveParameterValue(Message message) {
            return message.getHandle();
        }

        @Override
        public boolean matches(Message message) {
            return message.getHandleType() != null && handleType.isAssignableFrom(message.getHandleType());
        }
    }

    private static final class Score implements Comparable<Score> {

        private final int declarationDepth;
        private final int HandleDepth;
        private final String HandleName;

        private Score(Class HandleType, Class<?> declaringClass) {
            declarationDepth = superClassCount(declaringClass, 0);
            HandleDepth = superClassCount(HandleType, -255);
            HandleName = HandleType.getName();
        }

        private int superClassCount(Class<?> declaringClass, int interfaceScore) {
            if (declaringClass.isInterface()) {
                return interfaceScore;
            }
            int superClasses = 0;

            while (declaringClass != null) {
                superClasses++;
                declaringClass = declaringClass.getSuperclass();
            }
            return superClasses;
        }

        @Override
        public int compareTo(Score o) {
            if (declarationDepth != o.declarationDepth) {
                return (o.declarationDepth < declarationDepth) ? -1 : 1;
            } else if (HandleDepth != o.HandleDepth) {
                return (o.HandleDepth < HandleDepth) ? -1 : 1;
            } else {
                return HandleName.compareTo(o.HandleName);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Score score = (Score) o;
            return declarationDepth == score.declarationDepth
                    && HandleDepth == score.HandleDepth
                    && HandleName.equals(score.HandleName);
        }

        @Override
        public int hashCode() {
            int result = declarationDepth;
            result = 31 * result + HandleDepth;
            result = 31 * result + HandleName.hashCode();
            return result;
        }
    }
}