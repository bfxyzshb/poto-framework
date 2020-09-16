package com.weibo.poto.spring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpringBeanReferenceBuilderFactory {

    private static Map<String, SpringBeanReferenceBuilder> holder = new ConcurrentHashMap<>();

    public static SpringBeanReferenceBuilder getSpringBeanReferenceBuilderFactory(String name) {
        if (SpringBeanReferenceBuilder.PARAMETER_RESOLVER_FACTORY_BEAN_NAME.equalsIgnoreCase(name) && holder.get(SpringBeanReferenceBuilder.PARAMETER_RESOLVER_FACTORY_BEAN_NAME) == null) {
            holder.putIfAbsent(name, new SpringContextParameterResolverFactoryBuilder());
        }
        if (SpringBeanReferenceBuilder.COMMAND_BUS_NAME.equalsIgnoreCase(name) && holder.get(SpringBeanReferenceBuilder.COMMAND_BUS_NAME) == null) {
            holder.putIfAbsent(name, new CommandBusReferenceBuilder());
        }
        if (SpringBeanReferenceBuilder.EVENT_BUS_NAME.equalsIgnoreCase(name) && holder.get(SpringBeanReferenceBuilder.EVENT_BUS_NAME) == null) {
            holder.putIfAbsent(name, new CommandBusReferenceBuilder());
        }
        return holder.get(name);
    }

}
