
package com.weibo.poto.spi.support;

import com.weibo.poto.spi.SPIFactory;
import com.weibo.poto.spi.SPILoader;
import com.weibo.poto.spi.annotation.SPI;

/**
 * SpiExtensionFactory
 */
public class SPIExtensionFactory implements SPIFactory {

    @Override
    public <T> T getExtension(Class<T> type, String name) {
        if (type.isInterface() && type.isAnnotationPresent(SPI.class)) {
            SPILoader<T> loader = SPILoader.getExtensionLoader(type);
            if (!loader.getSupportedExtensions().isEmpty()) {
                return loader.getAdaptiveExtension();
            }
        }
        return null;
    }

}
