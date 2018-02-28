package com.lanxi.equity.assist;

import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/11 15:33
 */
public interface CheckAssist {

    static void nullCheck(Object... objs){
        Stream.of(objs).filter(o->o==null).findAny().ifPresent(o->{
            throw new NullPointerException(Thread.currentThread().getStackTrace()[1].getMethodName()+"'s arg maybe null !");
        });
    }
}
