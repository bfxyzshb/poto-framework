package com.weibo.poto.bus.common;

import com.weibo.poto.bus.command.CommandMessage;
import com.weibo.poto.bus.event.EventMessage;
<<<<<<< HEAD
import com.weibo.poto.domain.Message;
=======
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b

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
<<<<<<< HEAD
        return new CommonMessage(command.getClass().getName(), command, command);
=======
        return new CommonMessage<Object>(command.getClass().getName(), command);
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
    }

    public static EventMessage asEventMessage(Object event) {
        return new CommonMessage<Object>(event.getClass());
    }

    public CommonMessage(T classType) {
        this.classType = classType;
    }

<<<<<<< HEAD
    public CommonMessage(String commandName, T classType, Object payload) {
=======
    public CommonMessage(String commandName, T classType) {
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
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