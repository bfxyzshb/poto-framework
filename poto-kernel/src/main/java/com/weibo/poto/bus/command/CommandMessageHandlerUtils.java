package com.weibo.poto.bus.command;

import com.weibo.poto.bus.command.annotation.CommandHandler;
import com.weibo.poto.bus.common.AbstractMessageHandler;

public abstract class CommandMessageHandlerUtils {

    /**
     * Returns the name of the Command accepted by the given <code>handler</code>.
     *
     * @param handler The handler to resolve the name from
     * @return The name of the command accepted by the handler
     */
    public static String resolveAcceptedCommandName(AbstractMessageHandler handler) {
        CommandHandler annotation = handler.getAnnotation(CommandHandler.class);
        if (annotation != null && !"".equals(annotation.commandName())) {
            return annotation.commandName();
        }
        return handler.getHandleType().getName();
    }
}
