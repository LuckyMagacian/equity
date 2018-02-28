package com.lanxi.equity.Aop;

import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.RetMessage;
import com.lanxi.equity.assist.ToJson;
import com.lanxi.equity.config.RetCode;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/9 17:45
 */
//@Aspect
//@Component
public class AopInertUpdateCheck {

    @Pointcut("execution(* com.baomidou.mybatisplus.activerecord.Model.insert*(..))")
    private void modelInsert() {
    }

    @Pointcut("execution(* com.baomidou.mybatisplus.activerecord.Model.update*(..))")
    private void modelUpdate() {
    }

    @Pointcut("execution(* com.baomidou.mybatisplus.mapper.BaseMapper.insert*(..))")
    private void mapperInsert() {
    }

    @Pointcut("execution(* com.baomidou.mybatisplus.mapper.BaseMapper.update*(..))")
    private void mapperUpdate() {
    }

    public RetMessage checkWhenUpdate(ProceedingJoinPoint proceedingJoinPoint){
        return null;
    }




    @Around("modelInsert()||modelUpdate()||mapperInsert()||mapperUpdate()")
    public<T> T checkField(ProceedingJoinPoint proeedingJoinPoint) {

        AopJob<RetMessage> job = (targetClass, targetMethod, methodArgs, joinPoint) -> {
            try {
                List<String> checkResult = Stream.of(methodArgs)
                                                 .map(HibernateValidator::validate)
                                                 .filter(set->!set.isEmpty())
                                                 .flatMap(set -> set.stream())
                                                 .filter(string->string!=null&&!string.isEmpty())
                                                 .collect(Collectors.toList());
                if(!checkResult.isEmpty()){
                    return new RetMessage(RetCode.ARG_ILLEGAL, targetClass.getSimpleName() + "." + targetMethod + "参数校验不通过!", checkResult.toString());
                }else{
                    Object result=joinPoint.proceed(methodArgs);
                    if(result instanceof RetMessage){
                        return (RetMessage) result;
                    }else{
                        return new RetMessage(RetCode.SUCCESS, "", (Serializable) result);
                    }
                }
            } catch (Throwable e) {
                LogFactory.error(targetClass, targetClass + "." + targetMethod + " error when args:" + ToJson.toJson(methodArgs),e);
                return new RetMessage(RetCode.SYSTEM_ERROR,"服务器繁忙!");
            }
        };
        return AopJob.workJob((AopJob<T>) job, proeedingJoinPoint);
    }
}
