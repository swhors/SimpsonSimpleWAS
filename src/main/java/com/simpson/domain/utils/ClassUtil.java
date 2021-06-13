package com.simpson.domain.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
    
    public static Object createInstance(String className, String rootDir) {
        try {
            Class<?> clazz = Class.forName(className);
            
            Constructor<?> ctor = (rootDir == null ? clazz.getConstructor() :
                clazz.getConstructor(String.class));
            
            return (rootDir == null? ctor.newInstance(): ctor.newInstance(rootDir));
        } catch (ClassNotFoundException | InstantiationException |
            IllegalAccessException | NoSuchMethodException |
            InvocationTargetException e) {
            logger.error("{}", e);
        }
        return null;
    }
}
