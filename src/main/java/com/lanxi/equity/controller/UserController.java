package com.lanxi.equity.controller;

import com.alibaba.fastjson.JSONArray;
import com.lanxi.equity.assist.*;
import com.lanxi.equity.business.daily.UserService;
import com.lanxi.equity.config.RetCode;
import com.lanxi.equity.entity.Equity;
import com.lanxi.equity.entity.UserAccount;
import com.lanxi.util.consts.RetCodeEnum;
import com.lanxi.util.entity.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author yangyuanjian created in 2018/2/28 11:12
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //    @RequestMapping(value = "/equity/{orgaId}/{deptId}/{actId}/{userId}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    //    @ResponseBody
    //    @Comment("restful接口")
    //    public String queryEquityRest(@PathVariable(name = "orgaId") String orgaId,
    //                                  @PathVariable(name = "deptId") String deptId,
    //                                  @PathVariable(name = "actId") String actId,
    //                                  @PathVariable(name = "userId") String userId){
    //        RetMessage retMessage=new RetMessage<>();
    //        Equity oda=new Equity();
    //        oda.setOrgaId(orgaId);
    //        oda.setDeptId(deptId);
    //        oda.setActId(actId);
    //        oda.setUserId(userId);
    //
    //        HashSet<String> odaCheck =HibernateValidator.validate(oda, HibernateValidator.AsArg.class);
    //        if(!odaCheck.isEmpty()){
    //            retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
    //            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"ARG_CHECK_NOT_PASS"));
    //            retMessage.setDetail(odaCheck);
    //            //            return retMessage.toJson();
    //        }else {
    //            UserAccount account = new UserAccount();
    //            account.setUserId(userId);
    //            Equity equity = userService.equityQueryTotal(oda, account);
    //            retMessage.setRetCode(RetCodeEnum.SUCCESS);
    //            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
    //            retMessage.setDetail(equity);
    //        }
    //        return retMessage.toJson();
    //    }

    @RequestMapping(value = "/equity", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryEquity(HttpServletRequest req, HttpServletResponse res) {

        RetMessage retMessage = new RetMessage<>();
        try {
            String orgaId = ArgAssist.getArgNE.apply(req, "orgaId");
            String deptId = ArgAssist.getArgNE.apply(req, "deptId");
            String actId  = ArgAssist.getArgNE.apply(req, "actId");
            String userId = ArgAssist.getArgNE.apply(req, "userId");

            Equity oda = new Equity();
            oda.setOrgaId(orgaId);
            oda.setDeptId(deptId);
            oda.setActId(actId);
            oda.setUserId(userId);

            HashSet<String> odaCheck = HibernateValidator.validate(oda, HibernateValidator.AsArg.class);
            if (!odaCheck.isEmpty()) {
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(odaCheck);
                //            return retMessage.toJson();
            } else {
                UserAccount account = new UserAccount();
                account.setUserId(userId);
                Equity equity = userService.equityQueryTotal(oda, account);
                retMessage.setRetCode(RetCodeEnum.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                retMessage.setDetail(equity);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户权益值时发生异常,请求参数表:" + req.getParameterMap(), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }

    @RequestMapping(value = "/expire",method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryBeExpireEquity(HttpServletRequest req, HttpServletResponse res) {
        RetMessage retMessage = new RetMessage<>();
        try {
            String orgaId = ArgAssist.getArgNE.apply(req, "orgaId");
            String deptId = ArgAssist.getArgNE.apply(req, "deptId");
            String actId  = ArgAssist.getArgNE.apply(req, "actId");
            String userId = ArgAssist.getArgNE.apply(req, "userId");
            String dayStr = ArgAssist.getArgNE.apply(req, "day");

            if (dayStr != null) {
                if (!dayStr.matches("[0-9]+")) {
                    retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                    retMessage.setDetail("过期限定时间格式不是数字!");
                    return retMessage.toJson();
                }
            }

            Integer day = dayStr == null ? null : Integer.valueOf(dayStr);
            Equity  oda = new Equity();
            oda.setOrgaId(orgaId);
            oda.setDeptId(deptId);
            oda.setActId(actId);
            oda.setUserId(userId);

            HashSet<String> odaCheck = HibernateValidator.validate(oda, HibernateValidator.AsArg.class);
            if (!odaCheck.isEmpty()) {
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(odaCheck);
                //            return retMessage.toJson();
            } else {
                UserAccount account = new UserAccount();
                account.setUserId(userId);
                Equity equity = userService.beExpire(oda, account, day);
                retMessage.setRetCode(RetCodeEnum.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                retMessage.setDetail(equity);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户即将过期权益值时发生异常,请求参数表:" + req.getParameterMap(), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }

}
