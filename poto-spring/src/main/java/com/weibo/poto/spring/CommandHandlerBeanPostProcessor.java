package com.weibo.poto.spring;

import com.weibo.poto.bus.command.CommandHandlerAdapter;
import com.weibo.poto.bus.command.CommandBus;
import com.weibo.poto.bus.common.ParametersResolverFactory;
import lombok.Setter;

import java.util.Map;

public class CommandHandlerBeanPostProcessor
        extends AbstractHandlerBeanPostProcessor<CommandHandlerAdapter> {
    @Setter
    private CommandBus commandBus;

    public CommandHandlerBeanPostProcessor() {
        super(com.weibo.poto.bus.command.annotation.CommandHandler.class);
    }

    @Override
    protected CommandHandlerAdapter initializeAdapterFor(Object bean,
                                                         ParametersResolverFactory parametersResolverFactory) {
        return new CommandHandlerAdapter(bean, parametersResolverFactory);
    }

    private void ensureCommandBusInit() {
        if (commandBus == null) {
            Map<String, CommandBus> beans = getApplicationContext().getBeansOfType(CommandBus.class);
            if (beans.size() != 1) {
                throw new IllegalStateException(
                        "the application context must " + "contain exactly one bean of type CommandBus. The current application context has: " + beans.size());
            } else {
                this.commandBus = beans.entrySet().iterator().next().getValue();
            }
        }
    }

    @Override
    protected void subscribe(CommandHandlerAdapter adapter) {
        ensureCommandBusInit();
        for (String cmd : adapter.supportedCommands()) {
            commandBus.subscribe(cmd, adapter);
        }
    }


}