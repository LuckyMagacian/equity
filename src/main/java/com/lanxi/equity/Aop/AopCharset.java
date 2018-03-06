package com.lanxi.equity.Aop;

import com.lanxi.equity.assist.RetMessage;
import com.lanxi.equity.assist.ToJson;
import com.lanxi.equity.config.RetCode;
import com.lanxi.util.entity.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/26 14:34
 */
@Aspect
@Component
public class AopCharset {

    @Pointcut(value = "execution(* com.lanxi.equity.controller..*.*(javax.servlet.http.HttpServletRequest))")
    private void httpReq(){};

    @Pointcut(value = "execution(* com.lanxi.equity.controller..*.*(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse))")
    private void httpReqRes(){};


    @Around(value = "httpReq()||httpReqRes()")
    public String setReqUtf8(ProceedingJoinPoint proceedingJoinPoint){
        AopJob<String> job=(targetClass, targetMethod, methodArgs, joinPoint)->{
            try {
                Stream.of(methodArgs).forEach(e->{
                    if(e instanceof HttpServletRequest){
                        HttpServletRequest req= (HttpServletRequest) e;
                        try {
                            req.setCharacterEncoding("utf-8");

                        } catch (UnsupportedEncodingException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if(e instanceof HttpServletResponse){
                        HttpServletResponse res= (HttpServletResponse) e;
                        LogFactory.debug(this,"插入跨域头");
                        res.setCharacterEncoding("utf-8");
                        res.addHeader("Access-Control-Allow-Origin", "*");
                        res.addHeader("Access-Control-Allow-Methods", "POST");
                        res.addHeader("Access-Control-Max-Age", "1000");
                    }
                });
                return joinPoint.proceed(methodArgs)+"";
            } catch (Throwable e) {
                LogFactory.error(targetClass, targetClass + "." + targetMethod + " error when args:" + ToJson.toJson(methodArgs), e);
                return new RetMessage<String>(RetCode.SYSTEM_ERROR,"exception occured when set http charset !").toJson();
            }
        };
        return AopJob.workJob(job,proceedingJoinPoint);
    }

}
