
package com.weibo.poto.spi.support;


import com.weibo.poto.spi.SPIFactory;
import com.weibo.poto.spi.SPILoader;
import com.weibo.poto.spi.annotation.Adaptive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Adaptive
public class AdaptiveSPIFactory implements SPIFactory {
    private final List<SPIFactory> factories;

    public AdaptiveSPIFactory() {
        SPILoader<SPIFactory> loader = SPILoader.getExtensionLoader(SPIFactory.class);
        List<SPIFactory> list = new ArrayList<>();
        for (String name : loader.getSupportedExtensions()) {
            list.add(loader.getExtension(name));
        }
        factories = Collections.unmodifiableList(list);
    }

    @Override
    public <T> T getExtension(Class<T> type, String name) {
        for (SPIFactory factory : factories) {
            T extension = factory.getExtension(type, name);
            if (extension != null) {
                return extension;
            }
        }
        return null;
    }

}
