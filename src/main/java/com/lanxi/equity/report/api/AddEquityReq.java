package com.lanxi.equity.report.api;

import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.EasyToElement;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 增加权益报文请求消息体
 * @author yangyuanjian created in 2018/2/6 9:31
 */
public class AddEquityReq implements ReqProto,EasyToElement {

    @Comment("用户编号")
    private String userId;
//
//    @Comment("机构自定用户编号")
//    private String orgaUserId;

    @Comment("串码列表")
    private List<String> codes;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public String getOrgaUserId() {
//        return orgaUserId;
//    }
//
//    public void setOrgaUserId(String orgaUserId) {
//        this.orgaUserId = orgaUserId;
//    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

//    public static AddEquityReq fromXml(Element element){
//        if(element==null){
//            throw new NullPointerException("element can't be null !");
//        }
//        AddEquityReq req=new AddEquityReq();
////        req.setUserId(element.elementTextTrim("userId"));
//        req.setOrgaUserId(element.elementTextTrim("userId"));
//        List<String> codes=new ArrayList<>();
//        List<Element> list=element.elements("codes");
//        list.stream().map(e->e.getTextTrim()).forEach(codes::add);
//        req.setCodes(codes);
//        return req;
//    }

    @Override public <T> T fromElement(Element element) {
        if(element==null){
            throw new NullPointerException("element can't be null !");
        }
        this.setUserId(element.elementTextTrim("userId"));
        List<String> codes=new ArrayList<>();
        List<Element> list=element.element("codes").elements();
        list.stream().map(e->e.getTextTrim()).forEach(codes::add);
        this.setCodes(codes);
        return (T) this;
    }
}
