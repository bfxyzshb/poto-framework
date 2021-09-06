package com.weibo.poto.bus.command;

import com.google.common.collect.FluentIterable;
import com.weibo.poto.bus.common.CommonMessage;
import com.weibo.poto.bus.common.MessageHandler;
import com.weibo.poto.exception.PotoException;
import com.weibo.poto.logger.Logger;
import com.weibo.poto.logger.LoggerFactory;
<<<<<<< HEAD
import com.weibo.poto.spi.SPILoader;
=======
import lombok.Setter;
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.String.format;

<<<<<<< HEAD
public class DefaultCommandBus implements CommandBus<Command, Response> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCommandBus.class);
    private final ConcurrentMap<String, MessageHandler<?>> subscriptions = new ConcurrentHashMap<String, MessageHandler<?>>();
=======
public class DefaultCommandBus implements CommandBus<Response> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCommandBus.class);
    private final ConcurrentMap<String, MessageHandler<?>> subscriptions = new ConcurrentHashMap<String, MessageHandler<?>>();
    @Setter
    private List<CommandDispatchInterceptor> interceptors;
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b

    public DefaultCommandBus() {
    }

<<<<<<< HEAD
=======
    public DefaultCommandBus(List<CommandDispatchInterceptor> Interceptors) {
        this.interceptors = Interceptors;
    }

>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b

    private MessageHandler findCommandHandlerFor(CommandMessage command) {
        final MessageHandler handler = subscriptions.get(command.getCommandName());
        if (handler == null) {
            throw new PotoException(format("No handler was subscribed to command [%s]",
                    command.getCommandName()));
        }
        return handler;
    }

    @Override
<<<<<<< HEAD
    public Response dispatch(Command command) {
        Response response = null;
        CommandMessage commandMessage = null;
        try {
            commandMessage = CommonMessage.asCommandMessage(command);
            //前置拦截器
            boolean flag = preIntercept(command);
            if (!flag) {
                return null;
            }
            MessageHandler handler = findCommandHandlerFor(commandMessage);
            response = Response.buildSuccess(handler.handle(commandMessage));
            return response;
        } catch (Throwable throwable) {
            logger.error("command dispatch error", throwable);
        } finally {
            //后置拦截器
            postIntercept(command, response);
=======
    public Response dispatch(Object command) {
        Response response = null;
        CommandMessage commandTmp = null;
        try {
            commandTmp = CommonMessage.asCommandMessage(command);
            //前置拦截器
            preIntercept(commandTmp);
            MessageHandler handler = findCommandHandlerFor(commandTmp);
            response = Response.buildSuccess(handler.handle(commandTmp));
            return response;
        } catch (Throwable throwable) {
            //todo
            System.out.printf(String.valueOf(throwable));
        } finally {
            //后置拦截器
            postIntercept(commandTmp, response);
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
        }
        return null;
    }

    @Override
    public <T> void subscribe(String commandName, MessageHandler<? super T> handler) {
        subscriptions.put(commandName, handler);
    }

<<<<<<< HEAD
    private void postIntercept(Command command, Response response) {
        SPILoader<CommandDispatchInterceptor> commandDispatchInterceptorExtension = SPILoader.getExtensionLoader(CommandDispatchInterceptor.class);
        if (commandDispatchInterceptorExtension == null) {
            return;
        }
        //validation 必须要执行的后期可以再加
        List<CommandDispatchInterceptor> interceptors = commandDispatchInterceptorExtension.getActivateExtension(command.getProtocol(), null);
=======
    private void postIntercept(CommandMessage command, Response response) {
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
        if (CollectionUtils.isEmpty(interceptors)) {
            return;
        }
        try {
            for (CommandDispatchInterceptor postInterceptor : FluentIterable.from(interceptors).toSet()) {
                postInterceptor.postIntercept(command, response);
            }
        } catch (Exception e) {
            logger.error("postInterceptor error:" + e.getMessage(), e);
        }
    }

<<<<<<< HEAD
    private boolean preIntercept(Command command) {
        SPILoader<CommandDispatchInterceptor> commandDispatchInterceptorExtension = SPILoader.getExtensionLoader(CommandDispatchInterceptor.class);
        if (commandDispatchInterceptorExtension == null) {
            return true;
        }
        List<CommandDispatchInterceptor> interceptors = commandDispatchInterceptorExtension.getActivateExtension(command.getProtocol(), new String[]{"validation"});
        if (CollectionUtils.isEmpty(interceptors)) {
            return true;
        }
        for (CommandDispatchInterceptor preInterceptor : FluentIterable.from(interceptors).toSet()) {
            if (!preInterceptor.preIntercept(command)) {
                return false;
            }
        }
        return true;
=======
    private void preIntercept(CommandMessage command) {
        if (CollectionUtils.isEmpty(interceptors)) {
            return;
        }
        for (CommandDispatchInterceptor preInterceptor : FluentIterable.from(interceptors).toSet()) {
            preInterceptor.preIntercept(command);
        }
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
    }
}