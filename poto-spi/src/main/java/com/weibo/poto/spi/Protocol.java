package com.weibo.poto.spi;

import java.util.HashSet;
import java.util.Set;

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
