package com.lanxi.equity.assist;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/11 11:11
 */
public interface ConvertAssist {

    static String nullAsEmpty(String str){
        return Optional.ofNullable(str).orElse("");
    }

    static List<Field> objToFields(Object obj) {
        List<Field> fields = new ArrayList<>();
        Optional.ofNullable(obj).ifPresent(o -> {
            fields.addAll(ExtractFields.getClassFields(o.getClass(), true));
        });
        return fields;
    }

    static String fieldToTableField(Field field) {
        StringBuilder tableField = new StringBuilder();
        Optional.ofNullable(field).ifPresent(f -> {
            tableField.append(field.getName().replaceAll("[A-Z]{1}", "_$0").toLowerCase() + " ");
        });
        return tableField.toString();
    }

    static <T extends Model> EntityWrapper objToWrapper(T t) {
        if (t.getClass().getAnnotation(TableName.class) == null && t instanceof Model) {
            throw new IllegalArgumentException("arg : t must has @TableName annotation or extend Model !");
        }
        EntityWrapper wrapper = new EntityWrapper<>();
        List<Field>   fields  = objToFields(t);
        fields.stream().forEach(field -> {
            try {
                field.setAccessible(true);
                Object object = field.get(t);
                Optional.ofNullable(object).ifPresent(value -> wrapper.eq(fieldToTableField(field), value));
            } catch (Exception e) {
                throw new RuntimeException("exception occured when compose EntityWrapper", e);
            }
        });
        return wrapper;
    }

    static String extractCommentInfo(Object o) {

        AnnotatedElement annoE   = o instanceof AnnotatedElement ? (AnnotatedElement) o : o.getClass();
        StringBuffer     buffer  = new StringBuffer();
        Comment          comment = (Comment) annoE.getAnnotation(Comment.class);
        if (comment == null) {

        } else if (comment.value().length == 1) {
            buffer.append(comment.value()[0]);
        } else {
            Stream.of(comment.value()).forEach(v -> buffer.append(v + ","));
            if (buffer.lastIndexOf(",") > 0) {
                buffer.deleteCharAt(buffer.indexOf(","));
            }
        }
        return buffer.toString();
    }

    static String extractCommentInfo(Class clazz, String fieldName) {
        List<Field> fields = ExtractFields.getClassFields(clazz, true,true,true,true,true);
        Field       field  = fields.stream().filter(f -> f.getName().equals(fieldName)).findAny().orElse(null);
        return field == null ? null : extractCommentInfo(field);
    }

    static <T> T optinalDeal(T t, Supplier<T> supplier){
        if(supplier==null){
            throw new NullPointerException("supplier can't be null !");
        }
        return Optional.ofNullable(t).orElse(supplier.get());
    }
}
