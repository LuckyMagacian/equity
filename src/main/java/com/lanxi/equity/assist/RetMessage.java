package com.lanxi.equity.assist;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.equity.config.RetCode;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.interfaces.ToMap;

import java.io.Serializable;

/**
 * Created by yangyuanjian on 2017/11/2.
 */
public class RetMessage<T extends Serializable> implements ToJson, ToMap, Serializable {
    @InRange(clazz = RetCode.class,message = "错误代码必须是在RetCode中声明的值")
    private String retCode;
    private String retMessage;
    private T detail;

    public RetMessage() {

    }

    public RetMessage(String retCode, String retMessage) {
        this(retCode, retMessage, null);
    }


    public RetMessage(RetCodeEnum retCode, String retMessage) {
        this(retCode, retMessage, null);
    }

    public RetMessage(RetCodeEnum retCode, String retMessage, T detail) {
        this(retCode.toString(), retMessage, detail);
    }


    public RetMessage(String retCode, String retMessage, T detail) {
        this.retCode = retCode;
        this.retMessage = retMessage;
        this.detail = detail;
    }

    public void setCodeAndMessage(RetCodeEnum code, String message) {
        setRetCode(code);
        setRetMessage(message);
    }

    public void setAll(RetCodeEnum code, String message, T deatil) {
        setCodeAndMessage(code, message);
        setDetail(deatil);
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public void setRetCode(RetCodeEnum retCode) {
        this.retCode = retCode.toString();
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "RetMessage{" +
                "retCode='" + retCode + '\'' +
                ", retMessage='" + retMessage + '\'' +
                ", detail=" + detail +
                '}';
    }

    public static <T> RetMessage fromJson(String json) {
        return JSONObject.parseObject(json, RetMessage.class);
    }

    ;
}
