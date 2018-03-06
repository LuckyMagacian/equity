package com.lanxi.equity.assist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/6 20:01
 */
public class InRangeValidator implements ConstraintValidator<InRange, String> {

    private InRange range;

    @Override public void initialize(InRange constraintAnnotation) {
        this.range = constraintAnnotation;
    }

    @Override public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        if(value.isEmpty()){
            return false;
        }
        String[] ranges = range.value();
        Class    clazz  = range.clazz();
        List<String> values =new ArrayList<>();
        if(clazz!=null) {
            values = Stream.of(clazz.getDeclaredFields())
                                        .filter(f -> Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
                                        .filter(f -> f.getType().equals(String.class))
                                        .peek(f -> f.setAccessible(true))
                                        .map(f -> {
                                            try {
                                                return (String) f.get(clazz);
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                                return null;
                                            }
                                        })
                                        .filter(s -> s != null)
                                        .collect(Collectors.toList());
        }
        if(ranges!=null){
            values.addAll(Arrays.asList(ranges));
        }
        return values.contains(value);
    }
}
