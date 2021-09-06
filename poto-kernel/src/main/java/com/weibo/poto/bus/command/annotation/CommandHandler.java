package com.weibo.poto.bus.command.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CommandHandler {

    String commandName() default "";
}
