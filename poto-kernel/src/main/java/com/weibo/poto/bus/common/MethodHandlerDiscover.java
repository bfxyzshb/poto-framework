package com.weibo.poto.bus.common;

import com.weibo.poto.domain.Message;
import com.weibo.poto.exception.PotoException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.weibo.poto.bus.common.ReflectionUtils.methodsOf;

/**
 * 获取@commandHandle  eventHandle注解的方法
 */
public final class MethodHandlerDiscover {

    private final Class<?> targetType;
    private final List<MethodHandler> handlers = new ArrayList<>();
    private final ParametersResolverFactory parameterResolver;

    private static final ConcurrentMap<String, MethodHandlerDiscover> INSPECTORS =
            new ConcurrentHashMap<String, MethodHandlerDiscover>();


    public static <T extends Annotation> MethodHandlerDiscover getInstance(
            Class<?> beanClass, Class<T> annotationType, ParametersResolverFactory parametersResolverFactory,
            boolean allowDuplicates) {
        return getInstance(beanClass, parametersResolverFactory, allowDuplicates,
                new AnnotatedHandlerDefinition<T>(annotationType));
    }

    public static MethodHandlerDiscover getInstance(Class<?> beanClass,
                                                    ParametersResolverFactory parametersResolverFactory,
                                                    boolean allowDuplicates,
                                                    HandlerDefinition<? super Method> handlerDefinition) {
        // "AnnotatedHandler{" + commandHandle + '}'
        String key = handlerDefinition.toString() + "@" + beanClass.getName();
        MethodHandlerDiscover discover = INSPECTORS.get(key);
        while (discover == null
                || !beanClass.equals(discover.getTargetType())
                || !discover.parameterResolver.equals(parametersResolverFactory)) {
            final MethodHandlerDiscover newInspector = new MethodHandlerDiscover(
                    parametersResolverFactory,
                    beanClass,
                    allowDuplicates,
                    handlerDefinition);
            if (discover == null) {
                INSPECTORS.putIfAbsent(key, newInspector);
            } else {
                INSPECTORS.replace(key, discover, newInspector);
            }
            discover = INSPECTORS.get(key);
        }
        return discover;
    }

    private MethodHandlerDiscover(ParametersResolverFactory parametersResolverFactory,
                                  Class<?> targetType, boolean allowDuplicates,
                                  HandlerDefinition<? super Method> handlerDefinition) {
        this.parameterResolver = parametersResolverFactory;
        this.targetType = targetType;
        Iterable<Method> methods = methodsOf(targetType);
        NavigableSet<MethodHandler> uniqueHandlers = new TreeSet<MethodHandler>();
        for (Method method : methods) {
            if (handlerDefinition.isMessageHandler(method)) {
                final Class<?> explicitHandleType = handlerDefinition.resolveHandleFor(method);
                MethodHandler handlerMethod = MethodHandler.createFor(method,
                        explicitHandleType,
                        parametersResolverFactory
                );
                handlers.add(handlerMethod);
                if (!allowDuplicates && !uniqueHandlers.add(handlerMethod)) {
                    MethodHandler existing = uniqueHandlers.tailSet(handlerMethod).first();
                    throw new PotoException(
                            String.format("The class %s contains two handler methods (%s and %s) that listen "
                                            + "to the same Message type: %s",
                                    method.getDeclaringClass().getSimpleName(),
                                    handlerMethod.getMethodName(),
                                    existing.getMethodName(),
                                    handlerMethod.getHandleType().getSimpleName()));
                }
            }
        }
        Collections.sort(handlers);
    }

    public MethodHandler findHandlerMethod(final Message message) {
        for (MethodHandler handler : handlers) {
            if (handler.matches(message)) {
                return handler;
            }
        }
        return null;
    }

    public List<MethodHandler> getHandlers() {
        return new ArrayList<MethodHandler>(handlers);
    }

    public Class<?> getTargetType() {
        return targetType;
    }

    private static class AnnotatedHandlerDefinition<T extends Annotation> extends AbstractAnnotatedHandlerDefinition<T> {

        protected AnnotatedHandlerDefinition(Class<T> annotationType) {
            super(annotationType);
        }

        @Override
        protected Class<?> getDefinedHandle(T annotation) {
            return null;
        }
    }
}
