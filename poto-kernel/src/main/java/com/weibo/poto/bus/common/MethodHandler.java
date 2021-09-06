package com.weibo.poto.bus.common;

import com.weibo.poto.domain.Message;
import com.weibo.poto.exception.PotoException;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static com.weibo.poto.bus.common.ReflectionUtils.ensureAccessible;
import static java.lang.String.format;

public final class MethodHandler extends AbstractMessageHandler {

    private final Method method;

    public static MethodHandler createFor(Method method, Class<?> explicitHandleType,
                                          ParametersResolverFactory parametersResolverFactory) {
        ParameterResolver[] resolvers = findResolvers(
                parametersResolverFactory,
                method.getAnnotations(),
                method.getParameterTypes(),
                method.getParameterAnnotations(),
                explicitHandleType == null);
        Class<?> HandleType = explicitHandleType;
        if (explicitHandleType == null) {
            Class<?> firstParameter = method.getParameterTypes()[0];
            if (Message.class.isAssignableFrom(firstParameter)) {
                HandleType = Object.class;
            } else {
                HandleType = firstParameter;
            }
        }
        ensureAccessible(method);
        validate(method, resolvers);
        return new MethodHandler(method, resolvers, HandleType);
    }

    @Override
    public Object invoke(Object target, Message message) throws InvocationTargetException, IllegalAccessException {
        Assert.isTrue(method.getDeclaringClass().isInstance(target),
                "Given target is not an instance of the method's owner.");
        Assert.notNull(message, "Event may not be null");
        Object[] parameterValues = new Object[getParameterValueResolvers().length];
        for (int i = 0; i < parameterValues.length; i++) {
            parameterValues[i] = getParameterValueResolvers()[i].resolveParameterValue(message);
        }
        return method.invoke(target, parameterValues);
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return method.getAnnotation(annotationType);
    }

    private static void validate(Method method, ParameterResolver[] parameterResolvers) {
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            if (parameterResolvers[i] == null) {
                throw new PotoException(
                        format("On method %s, parameter %s is invalid. It is not of any format supported by a provided"
                                        + "ParameterValueResolver.",
                                method.toGenericString(), i + 1));
            }
        }

        if (method.getName().equals("handle")
                && Arrays.equals(method.getParameterTypes(), new Class[]{Message.class})) {
            throw new PotoException(String.format(
                    "Event Handling class %s contains method %s that has a naming conflict with a "
                            + "method on the EventHandler interface. Please rename the method.",
                    method.getDeclaringClass().getSimpleName(),
                    method.getName()));
        }
    }

    private MethodHandler(Method method, ParameterResolver[] parameterValueResolvers, Class HandleType) {
        super(HandleType, method.getDeclaringClass(), parameterValueResolvers);
        this.method = method;
    }

    public String getMethodName() {
        return method.getName();
    }


    public Method getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return format("HandlerMethod %s.%s for Handle type %s: %s",
                method.getDeclaringClass().getSimpleName(), method.getName(),
                getHandleType().getSimpleName(), method.toGenericString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        MethodHandler that = (MethodHandler) o;
        return method.equals(that.method);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + method.hashCode();
        return result;
    }
}
