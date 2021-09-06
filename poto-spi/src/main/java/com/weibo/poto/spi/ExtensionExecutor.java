package com.weibo.poto.spi;

import java.util.function.Function;

/**
 * @ClassName ExtensionExecutor
 * @Author hebiao1
 * @Date 2021/9/2 3:57 下午
 * @Version 1.0
 */

public class ExtensionExecutor {

    public <R, T> R execute(Class<T> targetClz, Protocol protocol, Function<T, R> exeFunction) {
        SPILoader<T> extensionLoader = SPILoader.getExtensionLoader(targetClz);
        T component = extensionLoader.getExtension(protocol.getExtensionName());
        return exeFunction.apply(component);
    }

}
