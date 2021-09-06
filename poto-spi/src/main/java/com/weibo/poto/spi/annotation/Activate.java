
package com.weibo.poto.spi.annotation;




import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.SPILoader;

import java.lang.annotation.*;

/**
 * Activate. This annotation is useful for automatically activate certain extensions with the given criteria,
 * for examples: <code>@Activate</code> can be used to load certain <code>Filter</code> extension when there are
 * multiple implementations.
 * <ol>
 * <li>{@link Activate#group()} specifies group criteria. Framework SPI defines the valid group values.
 * <li>{@link Activate#value()} specifies parameter key in {@link Protocol} criteria.
 * </ol>
 * SPI provider can call {@link SPILoader#getActivateExtension(Protocol, String, String)} to find out all activated
 * extensions with the given criteria.
* @see SPI
 * @see Protocol
 * @see SPILoader
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Activate {
    /**
     * Activate the current extension when one of the groups matches. The group passed into
     * {@link SPILoader#getActivateExtension(Protocol, String, String)} will be used for matching.
     *
     * @return group names to match
     * @see SPILoader#getActivateExtension(Protocol, String, String)
     */
    String[] group() default {};

    /**
     * Activate the current extension when the specified keys appear in the URL's parameters.
     * <p>
     * For example, given <code>@Activate("cache, validation")</code>, the current extension will be return only when
     * there's either <code>cache</code> or <code>validation</code> key appeared in the URL's parameters.
     * </p>
     *
     * @return URL parameter keys
     * @see SPILoader#getActivateExtension(Protocol, String)
     * @see SPILoader#getActivateExtension(Protocol, String, String)
     */
    String[] value() default {};


    /**
     * Absolute ordering info, optional
     *
     * @return absolute ordering info
     */
    int order() default 0;
}