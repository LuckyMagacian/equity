package com.lanxi.equity.test;

import org.junit.Test;

import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/7 9:38
 */
public class FieldExtractNew {

    private static String staticModifer = "(static)";

    private static String finalModifer = "(final)";

    private static String classModifer = "(class)";

    private static String className = "([A-Z]+[A-Za-z0-9_]*)";

    private static String genericModifer = "(\\< *" + className + " *\\>)";

    private static String genericClassName = className + " *" + genericModifer;

    private static String fieldClassName = "((\"+className+\")|(byte)|(char)|(int)|(long)|(float)|(double))";

    private static String arrayType = fieldClassName + "( *\\[\\])+";

    public static String append(String... args) {
        StringBuilder builder = new StringBuilder();
        Stream.of(args).forEach(s -> builder.append(s + " +"));
        return builder.substring(0, builder.length() - 2);
    }

    public static void match(String str, String... patterns) {
        System.out.println("------------------------------------------------------------------------------");
        String pattern = append(patterns);
        System.out.println(pattern);
        System.out.println(str.matches(pattern));
    }


    @Test
    public void test1() {
        String rangeModifer = "((public)|(private)|(protected)|())";
        match("public", rangeModifer);
        match("private", rangeModifer);
        match("public static final", rangeModifer, staticModifer, finalModifer);
        match("public class Object", rangeModifer, classModifer, className);
        match("public class A<T>", rangeModifer, classModifer, genericClassName);
    }

}
