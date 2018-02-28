package com.lanxi.equity.assist;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author yangyuanjian created in 2018/2/6 20:00
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {InRangeValidator.class})
public @interface InRange {
    String[] value() default "";
    Class clazz();

    String message() default "{javax.validation.constraints.NotInRange.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
