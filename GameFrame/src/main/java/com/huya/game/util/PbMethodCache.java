package com.huya.game.util;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yesheng
 */
public abstract class PbMethodCache {
    private static final ConcurrentHashMap<String, Method> cacheWithClassName = new ConcurrentHashMap<String, Method>();
    private static ConcurrentHashMap<Class<?>, Method> methodCaches = new ConcurrentHashMap<>();


    public static GeneratedMessageV3 parseFrom(String clazzName, byte[] data) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Method method = cacheWithClassName.get(clazzName);
        if (method == null) {
            Class<?> clazz = PbMethodCache.class.getClassLoader().loadClass(clazzName);
            method = clazz.getMethod("parseFrom", byte[].class);
            method.setAccessible(true);
            cacheWithClassName.putIfAbsent(clazzName, method);
            method = cacheWithClassName.get(clazzName);
        }
        return (GeneratedMessageV3) method.invoke(null, data);
    }

    public static AbstractMessage  parseFrom(Class clazz, ByteString byteString)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        Method method = methodCaches.get(clazz);
        if (method == null) {
            method = clazz.getMethod("parseFrom", ByteString.class);
            method.setAccessible(true);
            methodCaches.putIfAbsent(clazz, method);
        }
        return (AbstractMessage) method.invoke(null, byteString);
    }

    public static GeneratedMessageV3.Builder<?> newBuilder(String className) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cacheWithClassName.get(className);
        if(method == null){
            Class<?> clazz = PbMethodCache.class.getClassLoader().loadClass(className);
            method = clazz.getMethod("newBuilder");
            method.setAccessible(true);
            cacheWithClassName.putIfAbsent(className, method);
            method = cacheWithClassName.get(className);
        }
        return (GeneratedMessageV3.Builder<?>) method.invoke(null);
    }

}
