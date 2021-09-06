package com.weibo.poto.bus.common;

import com.weibo.poto.bus.command.CommandMessage;
import com.weibo.poto.bus.event.EventMessage;
import com.weibo.poto.domain.Message;

/**
 * 通用command消息
 *
 * @param
 */
public class CommonMessage<T> implements CommandMessage, EventMessage {

    private static final long serialVersionUID = 8754588074137370013L;

    private String commandName;
    private final T classType;


    public static CommandMessage asCommandMessage(Object command) {
        if (CommandMessage.class.isInstance(command)) {
            return (CommandMessage) command;
        }
        return new CommonMessage(command.getClass().getName(), command, command);
    }

    public static EventMessage asEventMessage(Object event) {
        return new CommonMessage<Object>(event.getClass());
    }

    public CommonMessage(T classType) {
        this.classType = classType;
    }

    public CommonMessage(String commandName, T classType, Object payload) {
        this.commandName = commandName;
        this.classType = classType;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public T getHandle() {
        return classType;
    }

    @Override
    public Class getHandleType() {
        return classType.getClass();
    }
}