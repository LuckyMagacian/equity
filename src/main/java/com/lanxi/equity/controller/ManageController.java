package com.lanxi.equity.controller;

import com.lanxi.equity.assist.ConvertAssist;
import com.lanxi.equity.assist.RetMessage;
import com.lanxi.equity.business.daily.ManageServicve;
import com.lanxi.equity.config.RetCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangyuanjian created in 2018/2/28 11:12
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private ManageServicve manageServicve;

    @RequestMapping(value = "/addComm",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String addCommodity(HttpServletRequest req, HttpServletResponse res){
     return new RetMessage<>(RetCode.UNSUPPORTFUN, ConvertAssist.extractCommentInfo(RetCode.class,"UNSUPPORTFUN"),null).toJson();
    }

    @RequestMapping(value = "/commodity",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String queryCommodity(HttpServletRequest req, HttpServletResponse res){
        return null;
    }
}
