package com.weibo.poto.spi;

import java.util.HashSet;
import java.util.Set;

/**
 * 抽象协议,由业务方自己扩展
 */
public interface Protocol {

    Set<String> parameters = new HashSet<>();

    default void addParameter(String parameter) {
        parameters.add(parameter);
    }

    default Set<String> getParameters() {
        return parameters;
    }

    String getExtensionName();

}
