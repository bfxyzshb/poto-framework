package com.weibo.poto.bus.common;

import com.weibo.poto.logger.Logger;
import com.weibo.poto.logger.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.*;

import static java.util.ServiceLoader.load;

public final class ClasspathParameterResolverFactory {

    private static final Logger logger = LoggerFactory.getLogger(ClasspathParameterResolverFactory.class);

    private static final Object monitor = new Object();
    private static final Map<ClassLoader, WeakReference<ParametersResolverFactory>> FACTORIES =
            new WeakHashMap<ClassLoader, WeakReference<ParametersResolverFactory>>();

    public static ParametersResolverFactory forClass(Class<?> clazz) {
        return forClassLoader(clazz == null ? null : clazz.getClassLoader());
    }

    public static ParametersResolverFactory forClassLoader(ClassLoader classLoader) {
        synchronized (monitor) {
            ParametersResolverFactory factory;
            if (!FACTORIES.containsKey(classLoader)) {
                factory = new ParametersResolverFactoryImpl(findDelegates(classLoader));
                FACTORIES.put(classLoader, new WeakReference<ParametersResolverFactory>(factory));
                return factory;
            }
            factory = FACTORIES.get(classLoader).get();
            if (factory == null) {
                factory = new ParametersResolverFactoryImpl(findDelegates(classLoader));
                FACTORIES.put(classLoader, new WeakReference<ParametersResolverFactory>(factory));
            }
            return factory;
        }
    }

    private static List<ParametersResolverFactory> findDelegates(ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        Iterator<ParametersResolverFactory> iterator = load(ParametersResolverFactory.class, classLoader).iterator();
        //noinspection WhileLoopReplaceableByForEach
        final List<ParametersResolverFactory> factories = new ArrayList<ParametersResolverFactory>();
        while (iterator.hasNext()) {
            try {
                ParametersResolverFactory factory = iterator.next();
                factories.add(factory);
            } catch (ServiceConfigurationError e) {
                logger.info("ParameterResolverFactory instance ignored, as one of the required classes is not available"
                        + "on the classpath: {}", e.getMessage());
            } catch (NoClassDefFoundError e) {
                logger.info("ParameterResolverFactory instance ignored. It relies on a class that cannot be found: {}", e.getMessage());
            }
        }
        return factories;
    }
}