package com.weibo.poto.bus.common;

import java.lang.reflect.AccessibleObject;

/**
 * AccessibleObject是Method、Field、Constructor类的基类，它提供了将反射的对象标记为在使用的时候取消默认Java语言
 * 访问控制检查力，对于公共成员、默认成员、私有成员、受保护成员；在分别使用Field、Method、Constructor对象来设置或获取字段、
 * 调用方法、  或者创建初
 * 始化对象的时候，执行安全检查。
 *
 * @param <T>
 */
public interface HandlerDefinition<T extends AccessibleObject> {

    boolean isMessageHandler(T member);


    Class<?> resolveHandleFor(T member);
}
