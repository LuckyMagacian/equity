package com.lanxi.equity.report.api;

import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.ConvertAssist;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.ReportDealStatus;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * 增加权益接口调用结果详情
 * @author yangyuanjian created in 2018/2/6 11:28
 */
@Comment("增加权益接口响应")
public class AddEquityRes implements ResProto{

    @Comment("用户编号")
    private String userId;

//    @Comment("机构自定用户编号")
//    private String orgaUserId;

    @Comment("对每个code的响应")
    private List<DealResult> resdetail;

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

    public List<DealResult> getResdetail() {
        return resdetail;
    }

    public void setResdetail(List<DealResult> resdetail) {
        this.resdetail = resdetail;
    }

    @Override public Element toElement() {

        Element body= DocumentHelper.createElement("body");
//        body.addElement("userId").setText(userId);
        Optional.ofNullable(userId).ifPresent(e->body.addElement("userId").setText(e));


        Element detail=body.addElement("result");
        resdetail.stream().forEach(e->{
            Element element=DocumentHelper.createElement("entry");

            Optional.ofNullable(e.getCode()).ifPresent(f->element.addElement("code").setText(e.getCode()));
            Optional.ofNullable(e.getDealStatus()).ifPresent(f->element.addElement("status").setText(e.getDealStatus()));
            Optional.ofNullable(e.getDesc()).ifPresent(f->element.addElement("desc").setText(e.getDesc()));

//            element.addElement("code").setText(ConvertAssist.nullAsEmpty(e.getCode()));
//            element.addElement("status").setText(ConvertAssist.nullAsEmpty(e.getDealStatus()));
//            element.addElement("desc").setText(ConvertAssist.nullAsEmpty(e.getDesc()));
            detail.add(element);
        });
        return body;
    }


    public static class DealResult{
        @Comment("兑换码")
        private String code;
        @Comment("处理结果描述")
        private String desc;
        @InRange(clazz= ReportDealStatus.class,message="兑换码处理结果必须是在ReportDealStatus中声明的值")
        @Comment("处理结果状态")
        private String dealStatus;

        public DealResult(){}

        public DealResult(String code,String desc,String dealStatus){
            this.code=code;
            this.desc=desc;
            this.dealStatus=dealStatus;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDealStatus() {
            return dealStatus;
        }

        public void setDealStatus(String dealStatus) {
            this.dealStatus = dealStatus;
        }

        @Override public String toString() {
            final StringBuffer sb = new StringBuffer("DealResult{");
            sb.append("code='").append(code).append('\'');
            sb.append(", desc='").append(desc).append('\'');
            sb.append(", dealStatus='").append(dealStatus).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("AddEquityRes{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", resdetail=").append(resdetail);
        sb.append('}');
        return sb.toString();
    }
}
