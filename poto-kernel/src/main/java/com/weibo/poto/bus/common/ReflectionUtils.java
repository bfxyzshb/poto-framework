package com.weibo.poto.bus.common;

import org.springframework.util.Assert;

import java.lang.reflect.*;
import java.security.AccessController;
import java.util.*;

public abstract class ReflectionUtils {

    /**
     * A map of Primitive types to their respective wrapper types.
     */
    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(8);

    private ReflectionUtils() {
        // utility class
    }

    static {
        primitiveWrapperTypeMap.put(boolean.class, Boolean.class);
        primitiveWrapperTypeMap.put(byte.class, Byte.class);
        primitiveWrapperTypeMap.put(char.class, Character.class);
        primitiveWrapperTypeMap.put(double.class, Double.class);
        primitiveWrapperTypeMap.put(float.class, Float.class);
        primitiveWrapperTypeMap.put(int.class, Integer.class);
        primitiveWrapperTypeMap.put(long.class, Long.class);
        primitiveWrapperTypeMap.put(short.class, Short.class);
    }

    public static Object getFieldValue(Field field, Object object) {
        ensureAccessible(field);
        try {
            return field.get(object);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("Unable to access field.", ex);
        }
    }

    /**
     * Returns the class on which the method with name "<code>getter</code>" and parameters of type
     * <code>parameterTypes</code> is declared. The given <code>instanceClass</code> is the instance on which the
     * method cn be called. If the method is not available on the given <code>instanceClass</code>, <code>null</code>
     * is returned.
     *
     * @param instanceClass  The class on which to look for the method
     * @param methodName     The name of the method
     * @param parameterTypes The parameter types of the method
     * @return The class on which the method is declared, or <code>null</code> if not found
     */
    public static Class<?> declaringClass(Class<?> instanceClass, String methodName, Class<?>... parameterTypes) {
        try {
            return instanceClass.getMethod(methodName, parameterTypes).getDeclaringClass();
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Indicates whether the given class implements a customized equals method. This methods returns true if the
     * declaring type of the equals method is not <code>Object</code>.
     *
     * @param type The type to inspect
     * @return <code>true</code> if the given type overrides the equals method, otherwise <code>false</code>
     */
    public static boolean hasEqualsMethod(Class<?> type) {
        return !Object.class.equals(declaringClass(type, "equals", Object.class));
    }

    /**
     * Indicates whether the two given objects are <em>not the same</em>, override an equals method that indicates
     * they are <em>not equal</em>, or implements {@link Comparable} which indicates the two are not equal. If this
     * method cannot safely indicate two objects are not equal, it returns
     * <code>false</code>.
     *
     * @param value      One of the values to compare
     * @param otherValue other value to compare
     * @return <code>true</code> if these objects explicitly indicate they are not equal, <code>false</code> otherwise.
     */
    @SuppressWarnings("unchecked")
    public static boolean explicitlyUnequal(Object value, Object otherValue) {
        if (value == otherValue) { // NOSONAR (The == comparison is intended here)
            return false;
        } else if (value == null || otherValue == null) {
            return true;
        } else if (value instanceof Comparable) {
            return ((Comparable) value).compareTo(otherValue) != 0;
        } else if (hasEqualsMethod(value.getClass())) {
            return !value.equals(otherValue);
        }
        return false;
    }

    /**
     * Makes the given <code>member</code> accessible via reflection if it is not the case already.
     *
     * @param member The member (field, method, constructor, etc) to make accessible
     * @param <T>    The type of member to make accessible
     * @return the given <code>member</code>, for easier method chaining
     *
     * @throws IllegalStateException if the member is not accessible and the security manager doesn't allow it to be
     * made accessible
     */
    public static <T extends AccessibleObject> T ensureAccessible(T member) {
        if (!isAccessible(member)) {
            AccessController.doPrivileged(new MemberAccessibilityCallback(member));
        }
        return member;
    }

    /**
     *
     */
    public static boolean isAccessible(AccessibleObject member) {
        return member.isAccessible() || (Member.class.isInstance(member) && isNonFinalPublicMember((Member) member));
    }

    /**
     * Checks whether the given <code>member</code> is public and non-final. These members do no need to be set
     * accessible using reflection.
     *
     * @param member The member to check
     * @return <code>true</code> if the member is public and non-final, otherwise <code>false</code>.
     *
     * @see #isAccessible(java.lang.reflect.AccessibleObject)
     * @see #ensureAccessible(java.lang.reflect.AccessibleObject)
     */
    public static boolean isNonFinalPublicMember(Member member) {
        return (Modifier.isPublic(member.getModifiers())
                && Modifier.isPublic(member.getDeclaringClass().getModifiers())
                && !Modifier.isFinal(member.getModifiers()));
    }

    /**
     * Returns an {@link Iterable} of all the fields declared on the given class and its super classes. The iterator
     * will always return fields declared in a subtype before returning fields declared in a super type.
     *
     * @param clazz The class to return fields for
     * @return an <code>Iterable</code> providing access to all declared fields in the class hierarchy
     */
    public static Iterable<Field> fieldsOf(Class<?> clazz) {
        List<Field> fields = new LinkedList<Field>();
        Class<?> currentClazz = clazz;
        do {
            fields.addAll(Arrays.asList(currentClazz.getDeclaredFields()));
            currentClazz = currentClazz.getSuperclass();
        } while (currentClazz != null);
        return Collections.unmodifiableList(fields);
    }

    /**
     * Returns an {@link Iterable} of all the methods declared on the given class and its super classes. The iterator
     * will always return methods declared in a subtype before returning methods declared in a super type.
     *
     * @param clazz The class to return methods for
     * @return an <code>Iterable</code> providing access to all declared methods in the class hierarchy
     */
    public static Iterable<Method> methodsOf(Class<?> clazz) {
        List<Method> methods = new LinkedList<Method>();
        Class<?> currentClazz = clazz;
        do {
            methods.addAll(Arrays.asList(currentClazz.getDeclaredMethods()));
            addMethodsOnDeclaredInterfaces(currentClazz, methods);
            currentClazz = currentClazz.getSuperclass();
        } while (currentClazz != null);
        return Collections.unmodifiableList(methods);
    }

    /**
     * Returns the boxed wrapper type for the given <code>primitiveType</code>.
     *
     * @param primitiveType The primitive type to return boxed wrapper type for
     * @return the boxed wrapper type for the given <code>primitiveType</code>
     *
     * @throws IllegalArgumentException will be thrown instead of returning null if no wrapper class was found.
     */
    public static Class<?> resolvePrimitiveWrapperType(Class<?> primitiveType) {
        Assert.notNull(primitiveType, "primitiveType may not be null");
        Assert.isTrue(primitiveType.isPrimitive(), "primitiveType is not actually primitive: " + primitiveType);

        Class<?> primitiveWrapperType = primitiveWrapperTypeMap.get(primitiveType);
        Assert.notNull(primitiveWrapperType, "no wrapper found for primitiveType: " + primitiveType);
        return primitiveWrapperType;
    }

    private static void addMethodsOnDeclaredInterfaces(Class<?> currentClazz, List<Method> methods) {
        for (Class iface : currentClazz.getInterfaces()) {
            methods.addAll(Arrays.asList(iface.getDeclaredMethods()));
            addMethodsOnDeclaredInterfaces(iface, methods);
        }
    }

    /**
     * Indicates whether the given field has the "transient" modifier
     *
     * @param field the field to inspect
     * @return <code>true</code> if the field is marked transient, otherwise <code>false
     * </code>
     */
    public static boolean isTransient(Field field) {
        return Modifier.isTransient(field.getModifiers());
    }
}