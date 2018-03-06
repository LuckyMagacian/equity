package com.lanxi.equity.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.assist.*;
import com.lanxi.equity.business.daily.UserService;
import com.lanxi.equity.config.ConstConfig;
import com.lanxi.equity.config.RetCode;
import com.lanxi.equity.entity.*;
import com.lanxi.util.entity.LogFactory;
import org.apache.ibatis.annotations.Arg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    //            retMessage.setRetCode(RetCode.SUCCESS);
    //            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
    //            retMessage.setDetail(equity);
    //        }
    //        return retMessage.toJson();
    //    }

    @RequestMapping(value = "/equity", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryEquity(HttpServletRequest req, HttpServletResponse res) {
        LogFactory.info(this,"收到http请求可用权益值查询");
        RetMessage retMessage = new RetMessage<>();
        try {
            String orgaId = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "orgaId");
            String deptId = ArgAssist.getArgNE.apply(req, "deptId");
            String actId  = ArgAssist.getArgNE.apply(req, "actId");
            String userId = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "userId");

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
                LogFactory.info(this,"参数校验不通过:"+odaCheck);
                //            return retMessage.toJson();
            } else {
                LogFactory.info(this,"参数校验通过");
                UserAccount account = new UserAccount();
                account.setUserId(userId);
                LogFactory.info(this,"调用服务层代码,参数:"+oda+","+account);
                Equity equity = userService.equityQueryTotal(oda, account);
                LogFactory.info(this,"可用权益值查询成功,响应原文:"+equity);
                retMessage.setRetCode(RetCode.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                retMessage.setDetail(equity);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户权益值时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }

    @RequestMapping(value = "/equityList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryEquityList(HttpServletRequest req, HttpServletResponse res) {
        LogFactory.info(this,"收到http请求可用权益列表查询");
        RetMessage retMessage = new RetMessage<>();
        try {
            String orgaId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "orgaId");
            String deptId      = ArgAssist.getArgNE.apply(req, "deptId");
            String actId       = ArgAssist.getArgNE.apply(req, "actId");
            String userId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "userId");
            String pageNumStr  = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageNum");
            String pageSizeStr = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageSize");

            if (pageNumStr == null || pageSizeStr == null || !pageNumStr.matches("[0-9]+") || !pageSizeStr.matches("[0-9]+")) {
                LogFactory.info(this,"分页参数校验不通过");
                retMessage.setRetCode(RetCode.PAGE_ARG_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "PAGE_ARG_NOT_PASS"));
                retMessage.setDetail("分页参数错误!");
                return retMessage.toJson();
            }

            Page<Equity> page = new Page<>(Integer.parseInt(pageNumStr), Integer.parseInt(pageSizeStr));

            Equity oda = new Equity();
            oda.setOrgaId(orgaId);
            oda.setDeptId(deptId);
            oda.setActId(actId);
            oda.setUserId(userId);

            HashSet<String> odaCheck = HibernateValidator.validate(oda, HibernateValidator.AsArg.class);
            if (!odaCheck.isEmpty()) {
                LogFactory.info(this,"参数校验不通过:"+odaCheck);
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(odaCheck);
                //            return retMessage.toJson();
            } else {
                LogFactory.info(this,"参数校验通过");
                UserAccount account = new UserAccount();
                account.setUserId(userId);
                LogFactory.info(this,"调用服务层,参数:"+oda+","+account+","+page);
                ArrayList<Equity> list = userService.equityList(oda, account, page);
                LogFactory.info(this,"服务层响应原文:"+list);
                retMessage.setRetCode(RetCode.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                HashMap<String, Object> map = new HashMap<>();
                map.put("list", list);
                map.put("page", page);
                retMessage.setDetail(map);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户权益列表时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }

    @RequestMapping(value = "/expire", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryBeExpireEquity(HttpServletRequest req, HttpServletResponse res) {
        LogFactory.info(this,"收到http请求将过期可用权益值查询");
        RetMessage retMessage = new RetMessage<>();
        try {
            String orgaId = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "orgaId");
            String deptId = ArgAssist.getArgNE.apply(req, "deptId");
            String actId  = ArgAssist.getArgNE.apply(req, "actId");
            String userId = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "userId");
            String dayStr = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "day");

            if (dayStr != null) {
                if (!dayStr.matches("[0-9]+")) {
                    LogFactory.info(this,"过期日期限定参数校验不通过");
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
                LogFactory.info(this,"参数校验不通过:"+odaCheck);
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(odaCheck);
                //            return retMessage.toJson();
            } else {
                LogFactory.info(this,"参数校验通过");
                UserAccount account = new UserAccount();
                account.setUserId(userId);
                LogFactory.info(this,"调用服务层参数:"
                                     + ","+oda
                                     + "," +account
                                     + ","+day);
                Equity equity = userService.beExpire(oda, account, day);
                LogFactory.info(this,"服务层响应原文:"+equity);
                retMessage.setRetCode(RetCode.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                retMessage.setDetail(equity);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户即将过期权益值时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }

    @RequestMapping(value = "/expireList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryBeExpireEquityList(HttpServletRequest req, HttpServletResponse res) {
        LogFactory.info(this,"收到http请求将过期可用权益值列表查询");
        RetMessage retMessage = new RetMessage<>();
        try {
            String orgaId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "orgaId");
            String deptId      = ArgAssist.getArgNE.apply(req, "deptId");
            String actId       = ArgAssist.getArgNE.apply(req, "actId");
            String userId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "userId");
            String dayStr      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "day");
            String pageNumStr  = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageNum");
            String pageSizeStr = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageSize");

            if (pageNumStr == null || pageSizeStr == null || !pageNumStr.matches("[0-9]+") || !pageSizeStr.matches("[0-9]+")) {
                LogFactory.info(this,"分页参数校验不通过");
                retMessage.setRetCode(RetCode.PAGE_ARG_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "PAGE_ARG_NOT_PASS"));
                retMessage.setDetail("分页参数错误!");
                return retMessage.toJson();
            }
            if (dayStr != null) {
                if (!dayStr.matches("[0-9]+")) {
                    LogFactory.info(this,"日期限定参数校验不通过");
                    retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                    retMessage.setDetail("过期限定时间格式不是数字!");
                    return retMessage.toJson();
                }
            }
            Page<Equity> page = new Page<>(Integer.parseInt(pageNumStr), Integer.parseInt(pageSizeStr));
            Integer      day  = dayStr == null ? null : Integer.valueOf(dayStr);
            Equity       oda  = new Equity();
            oda.setOrgaId(orgaId);
            oda.setDeptId(deptId);
            oda.setActId(actId);
            oda.setUserId(userId);

            HashSet<String> odaCheck = HibernateValidator.validate(oda, HibernateValidator.AsArg.class);
            if (!odaCheck.isEmpty()) {
                LogFactory.info(this,"参数校验不通过:"+odaCheck);
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(odaCheck);
                //            return retMessage.toJson();
            } else {
                LogFactory.info(this,"参数校验通过");
                UserAccount account = new UserAccount();
                account.setUserId(userId);
                LogFactory.info(this,"调用服务层代码,参数:"
                                     + ","+oda
                                     + ","+account
                                     + ","+day
                                     + ","+page);
                ArrayList<Equity> list = userService.beExpireList(oda, account, day, page);
                LogFactory.info(this,"服务层响应原文:"+list);
                retMessage.setRetCode(RetCode.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                HashMap<String, Object> map = new HashMap<>();
                map.put("list", list);
                map.put("page", page);
                retMessage.setDetail(map);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户即将过期权益列表时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }

    @RequestMapping(value = "/equityRecordList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryEquityRecordList(HttpServletRequest req, HttpServletResponse res) {
        LogFactory.info(this,"收到http请求权益记录列表查询");
        RetMessage retMessage = new RetMessage<>();
        try {
            String orgaId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "orgaId");
            String deptId      = ArgAssist.getArgNE.apply(req, "deptId");
            String actId       = ArgAssist.getArgNE.apply(req, "actId");
            String userId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "userId");
            String pageNumStr  = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageNum");
            String pageSizeStr = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageSize");

            if (pageNumStr == null || pageSizeStr == null || !pageNumStr.matches("[0-9]+") || !pageSizeStr.matches("[0-9]+")) {
                LogFactory.info(this,"分页参数校验不通过");
                retMessage.setRetCode(RetCode.PAGE_ARG_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "PAGE_ARG_NOT_PASS"));
                retMessage.setDetail("分页参数错误!");
                return retMessage.toJson();
            }
            Page<EquityRecord> page = new Page<>(Integer.parseInt(pageNumStr), Integer.parseInt(pageSizeStr));
            EquityRecord       oda  = new EquityRecord();
            oda.setOrgaId(orgaId);
            oda.setDeptId(deptId);
            oda.setActId(actId);
            oda.setUserId(userId);

            HashSet<String> odaCheck = HibernateValidator.validate(oda, HibernateValidator.AsArg.class);
            if (!odaCheck.isEmpty()) {
                LogFactory.info(this,"参数校验不通过:"+odaCheck);
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(odaCheck);
                //            return retMessage.toJson();
            } else {
                LogFactory.info(this,"参数校验通过");
                UserAccount account = new UserAccount();
                account.setUserId(userId);
                LogFactory.info(this,"调用服务层代码,参数:"
                                     + ","+oda
                                     + ","+account
                                     + ","+page);
                ArrayList<EquityRecord> list = userService.equityRecordList(oda, account, page);
                LogFactory.info(this,"服务层响应原文:"+list);
                retMessage.setRetCode(RetCode.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                HashMap<String, Object> map = new HashMap<>();
                map.put("list", list);
                map.put("page", page);
                retMessage.setDetail(map);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户权益记录列表时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }

    @RequestMapping(value = "/equityExchangeRecordList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryEquityExchangeRecordList(HttpServletRequest req, HttpServletResponse res) {
        LogFactory.info(this,"收到http请求权益兑换记录列表查询");
        RetMessage retMessage = new RetMessage<>();
        try {
            String orgaId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "orgaId");
            String deptId      = ArgAssist.getArgNE.apply(req, "deptId");
            String actId       = ArgAssist.getArgNE.apply(req, "actId");
            String userId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "userId");
            String pageNumStr  = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageNum");
            String pageSizeStr = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageSize");
            String recordStatus= ArgAssist.getArgNE.apply(req,"status");
            if (pageNumStr == null || pageSizeStr == null || !pageNumStr.matches("[0-9]+") || !pageSizeStr.matches("[0-9]+")) {
                LogFactory.info(this,"分页参数校验不通过");
                retMessage.setRetCode(RetCode.PAGE_ARG_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "PAGE_ARG_NOT_PASS"));
                retMessage.setDetail("分页参数错误!");
                return retMessage.toJson();
            }
            Page<EquityExchangeRecord> page = new Page<>(Integer.parseInt(pageNumStr), Integer.parseInt(pageSizeStr));
            EquityExchangeRecord       oda  = new EquityExchangeRecord();
            oda.setExStatus(recordStatus);
            oda.setOrgaId(orgaId);
            oda.setDeptId(deptId);
            oda.setActId(actId);
            oda.setUserId(userId);

            HashSet<String> odaCheck = HibernateValidator.validate(oda, HibernateValidator.AsArg.class);
            if (!odaCheck.isEmpty()) {
                LogFactory.info(this,"参数校验不通过:"+odaCheck);
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(odaCheck);
                //            return retMessage.toJson();
            } else {
                LogFactory.info(this,"参数校验通过");
                UserAccount account = new UserAccount();
                account.setUserId(userId);
                LogFactory.info(this,"调用服务层代码,参数:"
                                     + ","+oda
                                     + ","+account
                                     + ","+page);
                ArrayList<EquityExchangeRecord> list = userService.equityExchangeRecordList(oda, account, page);
                LogFactory.info(this,"服务层响应原文:"+list);
                retMessage.setRetCode(RetCode.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                HashMap<String, Object> map = new HashMap<>();
                map.put("list", list);
                map.put("page", page);
                retMessage.setDetail(map);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户权益兑换记录列表时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }


    @RequestMapping(value = "/equityOrderList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryEquityOrderList(HttpServletRequest req, HttpServletResponse res) {
        LogFactory.info(this,"收到http请求权益订单列表查询");
        RetMessage retMessage = new RetMessage<>();
        try {
            String orgaId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "orgaId");
            String deptId      = ArgAssist.getArgNE.apply(req, "deptId");
            String actId       = ArgAssist.getArgNE.apply(req, "actId");
            String userId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "userId");
            String orderStatus  = ArgAssist.getArgNE.apply(req,"status");
            String pageNumStr  = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageNum");
            String pageSizeStr = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "pageSize");
            if (pageNumStr == null || pageSizeStr == null || !pageNumStr.matches("[0-9]+") || !pageSizeStr.matches("[0-9]+")) {
                LogFactory.info(this,"分页参数校验不通过");
                retMessage.setRetCode(RetCode.PAGE_ARG_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "PAGE_ARG_NOT_PASS"));
                retMessage.setDetail("分页参数错误!");
                return retMessage.toJson();
            }
            Page<EquityOrder> page = new Page<>(Integer.parseInt(pageNumStr), Integer.parseInt(pageSizeStr));
            EquityOrder       oda  = new EquityOrder();
            oda.setOrgaId(orgaId);
            oda.setDeptId(deptId);
            oda.setActId(actId);
            oda.setUserId(userId);
            oda.setOrderStatus(orderStatus);

            HashSet<String> odaCheck = HibernateValidator.validate(oda, HibernateValidator.AsArg.class);
            if (!odaCheck.isEmpty()) {
                LogFactory.info(this,"参数校验不通过:"+odaCheck);
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(odaCheck);
                //            return retMessage.toJson();
            } else {
                LogFactory.info(this,"参数校验通过");
                UserAccount account = new UserAccount();
                account.setUserId(userId);
                LogFactory.info(this,"调用服务层代码,参数:"
                                     + ","+oda
                                     + ","+account
                                     + ","+page);
                ArrayList<EquityOrder> list = userService.equityOrderList(oda, account, page);
                LogFactory.info(this,"服务层响应原文:"+list);
                retMessage.setRetCode(RetCode.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                HashMap<String, Object> map = new HashMap<>();
                map.put("list", list);
                map.put("page", page);
                retMessage.setDetail(map);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户权益兑换订单列表时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }


    @RequestMapping(value = "/equityOrder", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String queryEquityOrder(HttpServletRequest req, HttpServletResponse res) {
        LogFactory.info(this,"收到http请求权益兑换订单详情查询");
        RetMessage retMessage = new RetMessage<>();
        try {
            String      orderId = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "orderId");
            String      userId=ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req,"userId");
            EquityOrder oda     = new EquityOrder();
            oda.setOrderId(orderId);
            oda.setUserId(userId);
            HashSet<String> odaCheck = HibernateValidator.validate(oda, HibernateValidator.AsArg.class);
            if (!odaCheck.isEmpty()) {
                LogFactory.info(this,"参数校验不通过:"+odaCheck);
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(odaCheck);
            } else {
                LogFactory.info(this,"参数校验通过");
                LogFactory.info(this,"调用服务层代码,参数:"
                                     + ","+oda);
                EquityOrder order = userService.orderInfo(orderId);
                LogFactory.info(this,"服务层响应原文:"+order);
                retMessage.setRetCode(RetCode.SUCCESS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
//                HashMap<String, Object> map = new HashMap<>();
                retMessage.setDetail(order);
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "查询用户权益兑换订单时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }
    }
    @RequestMapping(value = "/equityExchange", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public String equityExchange(HttpServletRequest req,HttpServletResponse res){
        LogFactory.info(this,"收到http请求权益兑换商品");
        RetMessage retMessage = new RetMessage<>();


        try {
            String orgaId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "orgaId");
            String deptId      = ArgAssist.getArgNE.apply(req, "deptId");
            String actId       = ArgAssist.getArgNE.apply(req, "actId");
            String userId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req, "userId");
            String commId      = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req,"commId");
            String countStr    = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req,"count");
            String phone       = ArgAssist.getArgNE.andThen(ArgAssist.nullAsEmpty).apply(req,"phone");



            if(countStr==null||!countStr.matches("[1-9]{1}[0-9]{0,1}")||Integer.valueOf(countStr)>99){
                LogFactory.info(this,"兑换数量校验不通过");
                retMessage.setRetCode(RetCode.COUNT_NOT_NUM);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "COUNT_NOT_NUM"));
                retMessage.setDetail("兑换数量错误!");
                return retMessage.toJson();
            }

            EquityOrder temp=new EquityOrder();
            temp.setOrgaId(orgaId);
            temp.setDeptId(deptId);
            temp.setActId(actId);
            temp.setUserId(userId);
            temp.setCommId(commId);
            temp.setCommCount(countStr);
            temp.setPhone(phone);

//            if(commId.length()==4&&!ConstConfig.COMMO_ID_EX){
//                //开启转换
//                temp.setBackup1(temp.getCommId());
//                temp.setCommId(ConstConfig.TEST_COMMOID);
//            }

            HashSet<String> checkResult=HibernateValidator.validate(temp, HibernateValidator.AsArg.class);

            if(!checkResult.isEmpty()){
                LogFactory.info(this,"参数校验不通过:"+checkResult);
                retMessage.setRetCode(RetCode.ARG_CHECK_NOT_PASS);
                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "ARG_CHECK_NOT_PASS"));
                retMessage.setDetail(checkResult);
                return retMessage.toJson();
            }
            UserAccount account=new UserAccount();
            account.setUserId(userId);

            try {
                LogFactory.info(this,"参数校验通过");
                LogFactory.info(this,"调用服务层代码,参数:"
                                     + ","+temp
                                     + ","+account);
                RetMessage<EquityOrder> order=userService.equityExchange(temp,account);
                LogFactory.info(this,"服务层响应原文:"+order);
//                retMessage.setRetCode(RetCode.SUCCESS);
//                retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"SUCCESS"));
//                retMessage.setDetail(order);
                return order.toJson();
            } catch (Exception e) {

                LogFactory.info(this, "用户权益兑换商品时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), e);
                if(e.getMessage().equals(RetCode.EQUITY_SUB_FAIL)){
                    retMessage.setRetCode(RetCode.EQUITY_SUB_FAIL);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"EQUITY_SUB_FAIL"));
                }else if(e.getMessage().equals(RetCode.INSERT_EQUITY_EX_RECORD_FAIL)){
                    retMessage.setRetCode(RetCode.INSERT_EQUITY_EX_RECORD_FAIL);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"INSERT_EQUITY_EX_RECORD_FAIL"));
                }else if(e.getMessage().equals(RetCode.INSERT_EQUITY_ORDER_FAIL)){
                    retMessage.setRetCode(RetCode.INSERT_EQUITY_ORDER_FAIL);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"INSERT_EQUITY_ORDER_FAIL"));
                }else if(e.getMessage().equals(RetCode.MAKE_REPORT_FAIL)){
                    retMessage.setRetCode(RetCode.MAKE_REPORT_FAIL);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"MAKE_REPORT_FAIL"));
                }else if(e.getMessage().equals(RetCode.GET_REPORT_RESPONSE_FAIL)){
                    retMessage.setRetCode(RetCode.GET_REPORT_RESPONSE_FAIL);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"GET_REPORT_RESPONSE_FAIL"));
                }else if(e.getMessage().equals(RetCode.UPDATE_EQUITY_EX_RECORD_FAIL)){
                    retMessage.setRetCode(RetCode.UPDATE_EQUITY_EX_RECORD_FAIL);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"UPDATE_EQUITY_EX_RECORD_FAIL"));
                }else if(e.getMessage().equals(RetCode.UPDATE_EQUITY_ORDER_FAIL)){
                    retMessage.setRetCode(RetCode.UPDATE_EQUITY_ORDER_FAIL);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"UPDATE_EQUITY_ORDER_FAIL"));
                }else if(e.getMessage().equals(RetCode.INSERT_EQUITY_RECORD_FAIL)){
                    retMessage.setRetCode(RetCode.INSERT_EQUITY_RECORD_FAIL);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"INSERT_EQUITY_RECORD_FAIL"));
                }else{
                    retMessage.setRetCode(RetCode.EQUITY_EXCHANGE_FAIL);
                    retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"EQUITY_EXCHANGE_FAIL"));
                }
            }
            return retMessage.toJson();
        } catch (Throwable t) {
            LogFactory.error(this, "=用户权益兑换商品时发生异常,请求参数表:" + ToJson.toJson(req.getParameterMap()), t);
            retMessage.setRetCode(RetCode.SYSTEM_ERROR);
            retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SYSTEM_ERROR"));
            retMessage.setDetail(null);
            return retMessage.toJson();
        }


    }
}
