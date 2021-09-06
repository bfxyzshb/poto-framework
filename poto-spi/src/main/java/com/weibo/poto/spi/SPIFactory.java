
package com.weibo.poto.spi;
import com.weibo.poto.spi.annotation.SPI;

/**
 * ExtensionFactory
 */
@SPI
public interface SPIFactory {

    /**
     * Get extension.
     *
     * @param type object type.
     * @param name object name.
     * @return object instance.
     */
    <T> T getExtension(Class<T> type, String name);

}
