package com.lanxi.equity.business.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lanxi.equity.assist.ConvertAssist;
import com.lanxi.equity.assist.RetMessage;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.ConstConfig;
import com.lanxi.equity.config.ReportDealStatus;
import com.lanxi.equity.config.RetCode;
import com.lanxi.equity.entity.Activity;
import com.lanxi.equity.entity.OrgaDeptAct;
import com.lanxi.equity.entity.ReportDeal;
import com.lanxi.equity.entity.UserAccount;
import com.lanxi.equity.report.api.*;
import com.lanxi.util.entity.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yangyuanjian created in 2018/2/26 16:21
 */
@Service
public class ApiInvoker {
    @Autowired
    private ApiService api;
    @Autowired
    private DaoService dao;

    private OrgaDeptAct odaSuppliy(Element head) {
        Req req = new Req();
        req.fromElement(head);
        return odaSuppliy(req);
    }

    private OrgaDeptAct odaSuppliy(Req req) {
        OrgaDeptAct oda = new OrgaDeptAct() {
            @Override protected Serializable pkVal() {
                return null;
            }
        };
        oda.setOrgaId(req.getOrgaId());
        oda.setDeptId(req.getDeptId());
        oda.setActId(req.getActId());
        return oda;
    }

    @Transactional
    public Document invoke(Req req, Element body) {
        LogFactory.info(this,"调用api,参数:"+req+","+body);
        String funId = req.getFunId();

        AddEquityReq reqBody = new AddEquityReq();
        reqBody.fromElement(body);

        Res res = new Res();
        res.fromReq(req);
        RetMessage result=null;

        Document document = DocumentHelper.createDocument();
        Element  root     = document.addElement("xml");
        switch (funId) {
            case ConstConfig.FUN_ID_EXCODE_INSTANCE_EXCHANGE:
                LogFactory.info(this, "调用" + ConvertAssist.extractCommentInfo(ConstConfig.class,"FUN_ID_EXCODE_INSTANCE_EXCHANGE"));
                UserAccount user = new UserAccount();
                user.setUserId(reqBody.getUserId());
                LogFactory.info(this,"调用服务实现层,参数:"+req+","+user+","+reqBody.getCodes());
                result = api.excodeEx(odaSuppliy(req), user, reqBody.getCodes());
                LogFactory.info(this,"服务层实现层响应原文:"+result);
                res.setResCode(result.getRetCode());
                res.setResMsg(result.getRetMessage());

                AddEquityRes resBody = new AddEquityRes();
                resBody.setUserId(reqBody.getUserId());
                if (result.getDetail() != null) {
                    resBody.setResdetail((List<AddEquityRes.DealResult>) result.getDetail());
                }
                res.setSign(ReportSign.sign(res.toElement(), resBody.toElement()));
                root.add(res.toElement());
                root.add(resBody.toElement());
                LogFactory.info(this,"apiinvoker响应报文:"+root.asXML());
//                EntityWrapper<ReportDeal> wrapper = new EntityWrapper<>();
//                wrapper.eq("orga_id", req.getOrgaId());
//                wrapper.eq("dept_id", req.getDeptId());
//                wrapper.eq("act_id", req.getActId());
//                wrapper.eq("msg_id", req.getMsgId());
//                wrapper.ne("deal_status", ReportDealStatus.SUCCESS);
//                List<ReportDeal> reportDeals = dao.getReportDealDao().selectList(wrapper);
//                //若消息记录数量不正确,抛出runtime异常触发事务回滚
//                if (reportDeals.size() != 1) {
//                    throw new RuntimeException("update date exception !");
//                }
//                ReportDeal report = reportDeals.get(0);
//                if (RetCode.SUCCESS.equals(result.getRetCode()) || RetCode.PARTIAL_SUCCESS.equals(result.getRetCode())) {
//                    report.setDealStatus(ReportDealStatus.SUCCESS);
//                    report.setResXml(document.asXML());
//                } else {
//                    report.setDealStatus(ReportDealStatus.FAIL);
//                }
//                report.updateById();
                break;
//            case ConstConfig.FUN_ID_GENERATE_EXCODE_INSTANCE:
//                break;
            default:
                res.setSign(ReportSign.sign(res.toElement(), null));
                root.add(res.toElement());
        }

        EntityWrapper<ReportDeal> wrapper = new EntityWrapper<>();
        wrapper.eq("orga_id", req.getOrgaId());
        wrapper.eq("dept_id", req.getDeptId());
        wrapper.eq("act_id", req.getActId());
        wrapper.eq("msg_id", req.getMsgId());
        wrapper.ne("deal_status", ReportDealStatus.SUCCESS);
        LogFactory.info(this,"查询消息处理记录");
        List<ReportDeal> reportDeals = dao.getReportDealDao().selectList(wrapper);
        //若消息记录数量不正确,抛出runtime异常触发事务回滚
        if (reportDeals.size() != 1) {
            LogFactory.info(this,"消息处理记录异常!");
            throw new RuntimeException("update date exception !");
        }
        ReportDeal report = reportDeals.get(0);
        if(result==null){
            report.setDealStatus(ReportDealStatus.SUCCESS);
        }else if (RetCode.SUCCESS.equals(result.getRetCode()) || RetCode.PARTIAL_SUCCESS.equals(result.getRetCode())) {
            report.setDealStatus(ReportDealStatus.SUCCESS);
        } else {
            report.setDealStatus(ReportDealStatus.FAIL);
        }
        report.setResXml(document.asXML());
        LogFactory.info(this,"更新消息处理记录!");
        report.updateById();
        return document;
    }
}
