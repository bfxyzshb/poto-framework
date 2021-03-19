package com.weibo.poto.bus.command;

import com.google.common.collect.FluentIterable;
import com.weibo.poto.bus.common.CommonMessage;
import com.weibo.poto.bus.common.MessageHandler;
import com.weibo.poto.exception.PotoException;
import com.weibo.poto.logger.Logger;
import com.weibo.poto.logger.LoggerFactory;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.String.format;

public class DefaultCommandBus implements CommandBus<Response> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCommandBus.class);
    private final ConcurrentMap<String, MessageHandler<?>> subscriptions = new ConcurrentHashMap<String, MessageHandler<?>>();
    @Setter
    private List<CommandDispatchInterceptor> interceptors;

    public DefaultCommandBus() {
    }

    public DefaultCommandBus(List<CommandDispatchInterceptor> Interceptors) {
        this.interceptors = Interceptors;
    }


    private MessageHandler findCommandHandlerFor(CommandMessage command) {
        final MessageHandler handler = subscriptions.get(command.getCommandName());
        if (handler == null) {
            throw new PotoException(format("No handler was subscribed to command [%s]",
                    command.getCommandName()));
        }
        return handler;
    }

    @Override
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
        }
        return null;
    }

    @Override
    public <T> void subscribe(String commandName, MessageHandler<? super T> handler) {
        subscriptions.put(commandName, handler);
    }

    private void postIntercept(CommandMessage command, Response response) {
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

    private void preIntercept(CommandMessage command) {
        if (CollectionUtils.isEmpty(interceptors)) {
            return;
        }
        for (CommandDispatchInterceptor preInterceptor : FluentIterable.from(interceptors).toSet()) {
            preInterceptor.preIntercept(command);
        }
    }
}