package com.weibo.poto.bus.command;

import com.weibo.poto.bus.common.*;
import com.weibo.poto.exception.PotoException;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * CommandHandler 适配器
 */
public class CommandHandlerAdapter implements MessageHandler<CommandMessage> {

    private final CommandBus commandBus;
    private final Map<String, MethodHandler> handlers = new HashMap<String, MethodHandler>();
    private final Object target;
    private final ParametersResolverFactory parametersResolverFactory;


    public CommandHandlerAdapter(Object target, CommandBus commandBus) {
        Assert.notNull(target, "target may not be null");
        this.parametersResolverFactory = ClasspathParameterResolverFactory.forClass(target.getClass());
        MethodHandlerDiscover inspector = MethodHandlerDiscover.getInstance(target.getClass(),
                com.weibo.poto.bus.command.annotation.CommandHandler.class,
                parametersResolverFactory,
                true);
        for (MethodHandler handler : inspector.getHandlers()) {
            String commandName = CommandMessageHandlerUtils.resolveAcceptedCommandName(handler);
            handlers.put(commandName, handler);
        }
        this.target = target;
        this.commandBus = commandBus;
    }

    public CommandHandlerAdapter(Object bean,
                                 ParametersResolverFactory parametersResolverFactory) {
        Assert.notNull(bean, bean.getClass().getSimpleName() + " not be null");
        //创建bean中带有CommandHandler注解的的巡查器(目的是查询CommandHandler的方法)
        MethodHandlerDiscover inspector = MethodHandlerDiscover.getInstance(bean.getClass(),
                com.weibo.poto.bus.command.annotation.CommandHandler.class,
                parametersResolverFactory,
                true);
        for (MethodHandler handler : inspector.getHandlers()) {
            String commandName = CommandMessageHandlerUtils.resolveAcceptedCommandName(handler);
            handlers.put(commandName, handler);
        }
        this.parametersResolverFactory = parametersResolverFactory;
        this.target = bean;
        this.commandBus = null;
    }


    @Override
    public Object handle(CommandMessage commandMessage) throws Throwable {
        try {
            //获取真正的handler
            final MethodHandler handler = handlers.get(commandMessage.getCommandName());
            if (handler == null) {
                throw new PotoException("No handler found for command " + commandMessage.getCommandName());
            }
            return handler.invoke(target, commandMessage);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    public Set<String> supportedCommands() {
        return handlers.keySet();
    }

}