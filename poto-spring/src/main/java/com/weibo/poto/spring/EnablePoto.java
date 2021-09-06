package com.weibo.poto.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnablePotoConfiguration.class)
public @interface EnablePoto {

}