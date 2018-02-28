package com.lanxi.equity.report.api;

import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.EasyToElement;
import org.dom4j.Element;

/**
 * 外部报文请求消息头
 * @author yangyuanjian created in 2018/2/6 9:20
 */
@Comment("对外报文消息头")
public class Req implements ReqProto,EasyToElement{
    @Comment("机构编号")
    protected String orgaId;

    @Comment("部门编号")
    protected String deptId;

//    @Comment("机构自定部门编号")
//    protected String orgaDeptId;

    @Comment("活动编号")
    protected String actId;

//    @Comment("机构自定活动编号")
//    protected String orgaActId;

    @Comment("报文发送日期")
    protected String sendDate;

    @Comment("报文发送时间")
    protected String sendTime;

    @Comment("报文消息编号")
    protected String msgId;

    @Comment("报文请求功能编号")
    protected String funId;

    @Comment("报文签名")
    protected String sign;

//    @Comment("参数-json格式")
//    protected String params;
//
//    @Comment("参数 -请求体")
//    protected String body;

//    public static Req fromXml(Element element){
//        Req req=new Req();
//        req.orgaId=element.elementTextTrim("orgaId");
//        req.deptId=element.elementTextTrim("deptId");
//        req.actId=element.elementTextTrim("actId");
//        req.sendDate =element.elementTextTrim("sendDate");
//        req.sendTime =element.elementTextTrim("sendTime");
//        req.msgId=element.elementTextTrim("msgId");
//        req.funId=element.elementTextTrim("funId");
//        req.sign=element.elementTextTrim("sign");
//        return req;
//    }

    @Override public <T> T fromElement(Element element) {
        if(element==null){
            throw new NullPointerException("element can't be null !");
        }
        this.orgaId=element.elementTextTrim("orgaId");
        this.deptId=element.elementTextTrim("deptId");
        this.actId=element.elementTextTrim("actId");
        this.sendDate =element.elementTextTrim("sendDate");
        this.sendTime =element.elementTextTrim("sendTime");
        this.msgId=element.elementTextTrim("msgId");
        this.funId=element.elementTextTrim("funId");
        this.sign=element.elementTextTrim("sign");
        return (T) this;
    }

    public String getOrgaId() {
        return orgaId;
    }

    public void setOrgaId(String orgaId) {
        this.orgaId = orgaId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
