package com.lanxi.equity.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lanxi.equity.assist.ConvertAssist;
import com.lanxi.equity.assist.TimeAssist;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.business.daily.ApiInvoker;
import com.lanxi.equity.business.daily.ApiService;
import com.lanxi.equity.config.ConstConfig;
import com.lanxi.equity.config.ReportDealStatus;
import com.lanxi.equity.config.RetCode;
import com.lanxi.equity.entity.Activity;
import com.lanxi.equity.entity.ReportDeal;
import com.lanxi.equity.report.api.ReportSign;
import com.lanxi.equity.report.api.Req;
import com.lanxi.equity.report.api.Res;
import com.lanxi.util.entity.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/26 14:27
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiInvoker api;
    @Autowired
    private DaoService dao;

    @RequestMapping(value = "/equity", method = {RequestMethod.POST})
    public void codeEx(HttpServletRequest req, HttpServletResponse res) {
        String   response = null;
        String   xml      = null;
        Document document = DocumentHelper.createDocument();
        Element  root     = document.addElement("xml");
        try {
            res.setContentType("txt/xml;charset=utf-8");
            BufferedReader reader    = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
            String         temp      = null;
            StringBuffer   xmlBuffer = new StringBuffer();
            while ((temp = reader.readLine()) != null) {
                xmlBuffer.append(temp);
            }
            xml = xmlBuffer.toString();
        } catch (Throwable t) {
            LogFactory.error(this, "接收api请求报文时发生异常", t);
            try {
                Res xmlRes = new Res();
                xmlRes.setResCode(RetCode.API_EXCEPTION_BEFORE_DEAL);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_EXCEPTION_BEFORE_DEAL"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                root.add(xmlRes.toElement());
                res.getWriter().print(document.asXML());
                return;
            } catch (IOException e) {
                LogFactory.error(this, "返回xml时发生异常", e);
            }
        }


        Req     xmlReq = null;
        Element body   = null;
        Element head   = null;

        try {
            Document doc = DocumentHelper.parseText(xml);
            head = doc.getRootElement().element("head");
            body = doc.getRootElement().element("body");
            xmlReq = new Req();
            xmlReq.fromElement(head);
        } catch (Throwable t) {
            LogFactory.error(this, "转换api请求报文时发生异常", t);
            try {
                Res xmlRes = new Res();
                xmlRes.setResCode(RetCode.API_REPORT_CONVERT_EXCEPTION);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_REPORT_CONVERT_EXCEPTION"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                root.add(xmlRes.toElement());
                res.getWriter().print(document.asXML());
                return;
            } catch (IOException e) {
                LogFactory.error(this, "返回xml时发生异常", e);
            }
        }

        //----------校验区开始

        try {
            //校验机构号
            String orgId = head.elementTextTrim("orgaId");
            if (orgId == null || orgId.isEmpty()) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_ORG_ID_IS_EMPTY);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_ORG_ID_IS_EMPTY"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                Element headRes = xmlRes.toElement();
                root.add(headRes);
                res.getWriter().print(document.asXML());
                return;
            }

            //校验签名
            String sign = head.elementTextTrim("sign");
            if (sign == null || sign.isEmpty()) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_SIGN_IS_EMPTY);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_SIGN_IS_EMPTY"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                Element headRes = xmlRes.toElement();
                root.add(headRes);
                res.getWriter().print(document.asXML());
                return;
            }

            //校验消息编号
            String msgId = head.elementTextTrim("msgId");
            if (msgId == null || msgId.isEmpty()) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_MSGID_IS_EMPTY);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_MSGID_IS_EMPTY"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                Element headRes = xmlRes.toElement();
                root.add(headRes);
                res.getWriter().print(document.asXML());
                return;
            }

            //发送日期
            String sendDate = head.elementTextTrim("sendDate");
            if (sendDate == null || sendDate.isEmpty()) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_SEND_DATE_IS_EMPTY);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_SEND_DATE_IS_EMPTY"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                Element headRes = xmlRes.toElement();
                root.add(headRes);
                res.getWriter().print(document.asXML());
                return;
            }


            //发送时间
            String sendTime = head.elementTextTrim("sendTime");
            if (sendTime == null || sendTime.isEmpty()) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_SEND_TIME_IS_EMPTY);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_SEND_TIME_IS_EMPTY"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                Element headRes = xmlRes.toElement();
                root.add(headRes);
                res.getWriter().print(document.asXML());
                return;
            }

            //发送时间
            String funId = head.elementTextTrim("funId");
            if (funId == null || funId.isEmpty()) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_FUN_ID_IS_EMPTY);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_FUN_ID_IS_EMPTY"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                Element headRes = xmlRes.toElement();
                root.add(headRes);
                res.getWriter().print(document.asXML());
                return;
            }
            //功能编号不存在
            if (!ConstConfig.FUNIDS.contains(funId)) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_FUN_ID_NOT_EXISTED);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_FUN_ID_NOT_EXISTED"));
                Element headRes = xmlRes.toElement();
                root.add(headRes);
                res.getWriter().print(document.asXML());
                return;
            }

            //校验签名正确性
            String localSign = ReportSign.sign(head, body);
            if (!localSign.equals(sign)) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_SIGN_ERROR);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_SIGN_ERROR"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                Element headRes = xmlRes.toElement();
                root.add(headRes);

                if (ConstConfig.DEBUG || ConstConfig.DEVELOP) {
                    headRes.addElement("plainText").setText(ReportSign.plainText(head, body));
                    headRes.addElement("correctSign").setText(ReportSign.sign(head, body));
                    LogFactory.info(this, ReportSign.plainText(head, body));
                }
                res.getWriter().print(document.asXML());
                return;
            }

            //活动检测
            Activity activity = null;
            String   deptId   = head.elementTextTrim("deptId");
            String   actId    = head.elementTextTrim("actId");

            EntityWrapper<Activity> activityEntityWrapper = new EntityWrapper<>();
            activityEntityWrapper.eq("orga_id", orgId);
            activityEntityWrapper.eq("dept_id", deptId);
            activityEntityWrapper.eq("act_id", actId);
            List<Activity> activities = dao.getActivityDao().selectList(activityEntityWrapper);
            if (activities.size() == 0) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_ACT_NOT_FOUND);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_ACT_NOT_FOUND"));
                Element headRes = xmlRes.toElement();
                root.add(headRes);
                res.getWriter().print(document.asXML());
                return;
            } else if (activities.size() > 1) {
                Res xmlRes = new Res();
                xmlRes.fromReq(xmlReq);
                xmlRes.setResCode(RetCode.API_ACT_MORE_THEN_ONE);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_ACT_MORE_THEN_ONE"));
                Element headRes = xmlRes.toElement();
                root.add(headRes);
                res.getWriter().print(document.asXML());
                return;
            } else {
                activity = activities.get(0);
            }


            //消息编号查重
            EntityWrapper<ReportDeal> reportDealEntityWrapper = new EntityWrapper<>();
            reportDealEntityWrapper.eq("orga_id", orgId);
            reportDealEntityWrapper.eq("dept_id", deptId);
            reportDealEntityWrapper.eq("act_id", actId);
            reportDealEntityWrapper.eq("msg_id", msgId);
            List<ReportDeal> reportDeals = dao.getReportDealDao().selectList(reportDealEntityWrapper);
            if (reportDeals.size() > 0) {
               //已成功处理或者已存在数量大于1
                ReportDeal successReport = reportDeals.stream().filter(e -> ReportDealStatus.SUCCESS.equals(e.getDealStatus())).findAny().orElse(null);
                if (successReport != null || reportDeals.size() > 1) {
                    Res xmlRes = new Res();
                    xmlRes.fromReq(xmlReq);
                    xmlRes.setResCode(RetCode.API_MSG_HAS_DEALED_SUCCESS);
                    xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_MSG_HAS_DEALED_SUCCESS"));
                    Element headRes = xmlRes.toElement();
                    root.add(headRes);
                    Element bodyRes=DocumentHelper.parseText(successReport.getResXml()).getRootElement().element("body");
                    root.add(bodyRes.createCopy());
                    res.getWriter().print(document.asXML());
                    return;
                }
            } else {
                //新请求先插
                ReportDeal reportDeal = new ReportDeal();
                reportDeal.setActId(activity.getActId());
                reportDeal.setActName(activity.getActName());
                reportDeal.setAddDate(TimeAssist.today());
                reportDeal.setAddTime(TimeAssist.now());
                reportDeal.setDealStatus(ReportDealStatus.WAIT_DEAL);
                reportDeal.setDeptId(activity.getDeptId());
                reportDeal.setDeptName(activity.getDeptName());
                reportDeal.setMsgId(msgId);
                reportDeal.setOrgaId(activity.getOrgaId());
                reportDeal.setOrgaName(activity.getOrgaName());
                reportDeal.setReportId(IdWorker.getId() + "");
                reportDeal.setReqXml(document.asXML());
                reportDeal.insert();
            }
        } catch (Throwable t) {
            LogFactory.error(this, "校验报文参数时发生异常", t);
            try {
                Res xmlRes = new Res();
                xmlRes.setResCode(RetCode.API_CHECK_PARAM_EXCEPTION);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_CHECK_PARAM_EXCEPTION"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                root.add(xmlRes.toElement());
                res.getWriter().print(document.asXML());
                return;
            } catch (IOException e) {
                LogFactory.error(this, "校验报文参数时发生异常", e);
            }
        }


        //----------校验区结束


        try {
            Document resDoc = api.invoke(xmlReq, body);
            response = resDoc.asXML();
            res.getWriter().print(response);
            return;
        } catch (Throwable t) {
            LogFactory.error(this, "调用api请求报文时发生异常", t);
            try {
                Res xmlRes = new Res();
                xmlRes.setResCode(RetCode.API_INVOKE_EXCEPTION);
                xmlRes.setResMsg(ConvertAssist.extractCommentInfo(RetCode.class, "API_INVOKE_EXCEPTION"));
                xmlRes.setSign(ReportSign.sign(xmlRes.toElement(), null));
                root.add(xmlRes.toElement());
                res.getWriter().print(document.asXML());
                return;
            } catch (IOException e) {
                LogFactory.error(this, "返回xml时发生异常", e);
            }
        }
    }
}
