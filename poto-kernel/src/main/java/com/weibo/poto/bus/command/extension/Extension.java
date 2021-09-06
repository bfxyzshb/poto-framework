package com.weibo.poto.bus.command.extension;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;


@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Extension {
  String value();
}
