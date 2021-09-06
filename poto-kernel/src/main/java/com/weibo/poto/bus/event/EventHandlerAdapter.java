package com.weibo.poto.bus.event;

import com.weibo.poto.bus.common.MessageHandler;
import com.weibo.poto.bus.common.MethodHandler;
import com.weibo.poto.bus.common.MethodHandlerDiscover;
import com.weibo.poto.bus.common.ParametersResolverFactory;
import com.weibo.poto.bus.event.annotation.EventHandler;
import com.weibo.poto.domain.Message;
import com.weibo.poto.exception.PotoException;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName AnnotationEventHandlerAdapter
 * @Description TODO
 * @Author hebiao1
 * @Date 2020/7/12 9:40 下午
 * @Version 1.0
 */
public class EventHandlerAdapter implements MessageHandler<EventMessage> {
    private final ParametersResolverFactory parametersResolverFactory;
    private final Object target;
    private MethodHandlerDiscover discover;

    public EventHandlerAdapter(Object bean, ParametersResolverFactory parametersResolverFactory) {
        this.parametersResolverFactory = parametersResolverFactory;
        this.target = bean;
        discover = MethodHandlerDiscover.getInstance(bean.getClass(),
                EventHandler.class,
                parametersResolverFactory,
                true);
    }

    @Override
    public Object handle(EventMessage message) throws Throwable {
        MethodHandler m = findHandlerMethod(message);
        if (m == null) {
            // event listener doesn't support this type of event
            return null;
        }
        try {
            return m.invoke(target, message);
        } catch (IllegalAccessException e) {
            throw new PotoException("Access to the message handler method was denied.", e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            }
            throw new PotoException("An exception occurred while invoking the handler method.", e);
        }
    }

    public MethodHandler findHandlerMethod(Message message) {
        return discover.findHandlerMethod(message);
    }
}
