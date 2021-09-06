package com.weibo.poto.bus.event.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author shb
 * @date 2020/06/01
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EventHandler {
}
