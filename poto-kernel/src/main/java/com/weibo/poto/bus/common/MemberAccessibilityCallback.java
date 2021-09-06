package com.weibo.poto.bus.common;

import java.lang.reflect.AccessibleObject;
import java.security.PrivilegedAction;

public class MemberAccessibilityCallback implements PrivilegedAction<Object> {

    private final AccessibleObject method;

    public MemberAccessibilityCallback(AccessibleObject method) {
        this.method = method;
    }

    @Override
    public Object run() {
        method.setAccessible(true);
        return Void.class;
    }
}
