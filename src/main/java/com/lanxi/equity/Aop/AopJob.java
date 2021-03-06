package com.lanxi.equity.Aop;

import com.lanxi.util.entity.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by yangyuanjian on 2017/11/23.
 */
public interface AopJob<T> {
    T doJob(Class targetClass, Method targetMethod, Object[] methodArgs, ProceedingJoinPoint joinPoint) throws NoSuchMethodException;
    default <T> T work(ProceedingJoinPoint joinPoint) {
        T t = null;
        //获取代理方法参数值
        Object[] args = joinPoint.getArgs();
        //获取代理目标类
        Class clazz = joinPoint.getTarget().getClass();
        Method method;
        try {
            //获取代理方法对象
            method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            t = (T)doJob(clazz, method, args, joinPoint);
            return t;
        } catch (Throwable e) {
            LogFactory.error(AopJob.class, "调用aop通用模块时发生异常!", e);
            return t;
        }
    }

    static <T> T workJob(AopJob<T> job, ProceedingJoinPoint joinPoint) {
        T t = null;
        //获取代理方法参数值
        Object[] args = joinPoint.getArgs();
        //获取代理目标类
        Class clazz = joinPoint.getTarget().getClass();
        Method method;
        try {
            //获取代理方法对象
            method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            t = job.doJob(clazz, method, args, joinPoint);
            return t;
        } catch (Throwable e) {
            LogFactory.error(AopJob.class, "调用aop通用模块时发生异常!", e);
            return t;
        }
    }


}
