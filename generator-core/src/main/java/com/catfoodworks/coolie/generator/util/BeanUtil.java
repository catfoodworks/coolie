package com.catfoodworks.coolie.generator.util;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BeanUtil {

    public static <S, T> void copy(S source, T target) {
        try {

            if(source == null || target == null)
                return;

            BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopier.copy(source, target, null);

        } catch (Exception e) {
            throw new RuntimeException(String.format("bean copy fail from %s to %s", source.getClass().getTypeName(), target.getClass().getTypeName()));
        }
    }

    public static <S, T> T copy(S source, Class<T> targetClass) {
        try {

            T target = targetClass.newInstance();
            copy(source, target);
            return target;

        } catch (Exception e) {
            throw new RuntimeException(String.format("bean copy fail from %s to %s", source.getClass().getTypeName(), targetClass.getTypeName()), e);
        }
    }

    public static Map toMap(Object source) {
        return BeanMap.create(source);
    }

    public static String toString(Object source) {
        return toString(source, "");
    }

    public static String toString(Object source, String nullDefault) {
        if(source == null)
            return nullDefault;

        Map<String, Object> beanMap = BeanUtil.toMap(source);
        return beanMap
                .keySet()
                .stream()
                .map(String::valueOf)
                .sorted()
                .map(key -> String.format("%s:%s", key, Objects.toString(beanMap.get(key), "")))
                .collect(Collectors.joining(",", "{", "}"));
    }
}
