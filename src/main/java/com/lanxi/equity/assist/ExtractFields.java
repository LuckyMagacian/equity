package com.lanxi.equity.assist;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/11 10:57
 */
public interface ExtractFields {

    static List<Field> getSuperFields(Class superClass, boolean includeSuper) {
        return getSuperFields(superClass, includeSuper, false, false, true, true);
    }


    static List<Field> getSuperFields(Class superClass, boolean includeSuper, boolean includePrivate,
                                      boolean includeStatic, boolean includeFinal, boolean includeTransient) {
        if (superClass == null || superClass.equals(Object.class)) {
            return new ArrayList<>();
        }
        List<Field>   fields = new ArrayList<>();
        Stream<Field> stream = Stream.of(superClass.getDeclaredFields());
        if (!includePrivate) {
            stream = stream.filter(f -> !Modifier.isPrivate(f.getModifiers()));
        }
        if (!includeStatic) {
            stream = stream.filter(f -> !Modifier.isStatic(f.getModifiers()));
        }
        if (!includeFinal) {
            stream = stream.filter(f -> !Modifier.isFinal(f.getModifiers()));
        }
        if (!includeTransient) {
            stream = stream.filter(f -> !Modifier.isTransient(f.getModifiers()));
        }
        fields.addAll(stream.collect(Collectors.toList()));
        if (!includeSuper) {
            return fields;
        }
        fields.addAll(getSuperFields(superClass.getSuperclass(), includeSuper, includePrivate,
                                     includeStatic, includeFinal, includeTransient));
        return fields;
    }

    static List<Field> getClassFields(Class clazz, boolean includeSuper) {
        return getSuperFields(clazz,true,true,false,true,true);
    }

    static List<Field> getClassFields(Class clazz, boolean includeSuper, boolean includePrivate,
                                      boolean includeStatic, boolean includeFinal, boolean includeTransient) {
        List<Field> fields = new ArrayList<>();

        Stream<Field> stream = Stream.of(clazz.getDeclaredFields());
        if (!includePrivate) {
            stream = stream.filter(f -> !Modifier.isPrivate(f.getModifiers()));
        }
        if (!includeStatic) {
            stream = stream.filter(f -> !Modifier.isStatic(f.getModifiers()));
        }
        if (!includeFinal) {
            stream = stream.filter(f -> !Modifier.isFinal(f.getModifiers()));
        }
        if (!includeTransient) {
            stream = stream.filter(f -> !Modifier.isTransient(f.getModifiers()));
        }

        fields.addAll(stream.collect(Collectors.toList()));
        if (!includeSuper) {
            return fields;
        }
        fields.addAll(getSuperFields(clazz.getSuperclass(), includeSuper,includePrivate,includeStatic,includeFinal,includeTransient));
        return fields;
    }


}
