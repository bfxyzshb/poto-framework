package com.weibo.poto.repository.util;


import com.weibo.poto.entity.AggregateRoot;
import com.weibo.poto.entity.Identifier;

import java.lang.reflect.Field;


public class ReflectionUtils {

    public static <T extends AggregateRoot<ID>, ID extends Identifier> void writeField(
            String fieldName, T aggregate, ID id) {
        Class<? extends AggregateRoot> aClass = aggregate.getClass();
        try {
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(aggregate, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
