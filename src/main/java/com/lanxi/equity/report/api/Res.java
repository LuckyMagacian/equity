package com.lanxi.equity.report.api;

import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.ConvertAssist;
import com.lanxi.equity.assist.TimeAssist;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Optional;

/**
 * 外部报文响应消息头
 * @author yangyuanjian created in 2018/2/6 9:28
 */
public class Res extends Req implements ResProto{


    @Comment("响应码")
    private String resCode;

    @Comment("响应消息")
    private String resMsg;

//    @Comment("响应值")
//    private String values;
//
//    @Comment("响应体")
//    private String body;
    @Override
    public Element toElement(){
        Element head=DocumentHelper.createElement("head");
        Optional.ofNullable(orgaId).ifPresent(e->head.addElement("orgaId").setText(e));
        Optional.ofNullable(deptId).ifPresent(e->head.addElement("deptId").setText(e));
        Optional.ofNullable(actId).ifPresent(e->head.addElement("actId").setText(e));
        Optional.ofNullable(sendDate).ifPresent(e->head.addElement("sendDate").setText(e));
        Optional.ofNullable(sendTime).ifPresent(e->head.addElement("sendTime").setText(e));
        Optional.ofNullable(msgId).ifPresent(e->head.addElement("msgId").setText(e));
        Optional.ofNullable(funId).ifPresent(e->head.addElement("funId").setText(e));
        Optional.ofNullable(sign).ifPresent(e->head.addElement("sign").setText(e));
        Optional.ofNullable(resCode).ifPresent(e->head.addElement("resCode").setText(e));
        Optional.ofNullable(resMsg).ifPresent(e->head.addElement("resMsg").setText(e));




//        head.addElement("orgaId").setText(ConvertAssist.nullAsEmpty(orgaId));
//        head.addElement("deptId").setText(ConvertAssist.nullAsEmpty(deptId));
//        head.addElement("actId").setText(ConvertAssist.nullAsEmpty(actId));
//        head.addElement("sendDate").setText(ConvertAssist.nullAsEmpty(sendDate));
//        head.addElement("sendTime").setText(ConvertAssist.nullAsEmpty(sendTime));
//        head.addElement("msgId").setText(ConvertAssist.nullAsEmpty(msgId));
//        head.addElement("funId").setText(ConvertAssist.nullAsEmpty(funId));
//        head.addElement("sign").setText(ConvertAssist.nullAsEmpty(sign));
//        head.addElement("resCode").setText(ConvertAssist.nullAsEmpty(resCode));
//        head.addElement("resMsg").setText(ConvertAssist.nullAsEmpty(resMsg));
        return head;
    }

    public void fromReq(Req req){
        this.orgaId=req.orgaId;
        this.deptId=req.deptId;
        this.actId=req.actId;
        this.sendDate= TimeAssist.today();
        this.sendTime=TimeAssist.now();
        this.msgId=req.msgId;
        this.funId=req.funId;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
}
