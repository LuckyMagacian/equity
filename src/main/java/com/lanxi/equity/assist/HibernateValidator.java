package com.lanxi.equity.assist;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yangyuanjian created in 2018/2/6 9:40
 */
public class HibernateValidator {

    //    public static final Class[] INSERT=new Class[]{Insert.class};
    //
    //    public static final Class[] UPDATE=new Class[]{Update.class};
    //
    //    public static final Class[] INSERT_UPDATE=new Class[]{Insert.class,Update.class};


    @Comment("校验器工厂")
    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();

    @Comment("校验器")
    private static final Validator VALIDATOR = FACTORY.getValidator();

    /**
     * 校验参数t的值是否符合配置的设定
     *
     * @param t   待校验的值,不允许为null
     * @param <T> 泛型
     * @return 结果集合
     */
    @Comment("校验t")
    public static <T> HashSet<ConstraintViolation<T>> validate1(T t, Class... groups) {
        if (t == null) {
            throw new NullPointerException("arg 't' can't be null !");
        }
        return (HashSet<ConstraintViolation<T>>) VALIDATOR.validate(t, groups);
    }

    /**
     * 校验参数t的值是否符合配置的设定
     *
     * @param t   待校验的值,不允许为null
     * @param <T> 泛型
     * @return 错误信息集合
     */
    public static <T> HashSet<String> validate(T t, Class... groups) {
        Set<ConstraintViolation<T>> temps = validate1(t, groups);
        Set<String> result = temps.stream().filter(e -> e != null)
                                  .map(e -> e.getMessage())
                                  .collect(Collectors.toSet());
        return (HashSet<String>) result;
    }

    @Comment("数据库插入校验组")
    public interface Insert {
    }
    @Comment("数据库更新校验组")
    public interface Update {
    }
    @Comment("参数校验组")
    public interface AsArg {
    }
}
