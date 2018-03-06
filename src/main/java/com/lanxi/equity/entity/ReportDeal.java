package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.ReportDealStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 报文处理记录
 * @author yangyuanjian created in 2018/2/11 10:19
 */
@Comment("报文处理记录")
public class ReportDeal extends OrgaDeptAct {
    @Comment("报文处理记录唯一编号")
    @Pattern(regexp = "[0-9]{18}", message = "报文处理记录编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    @TableId("report_id")
    @NotNull(message = "报文处理记录唯一编号不能为null", groups = {HibernateValidator.Insert.class})
    private String reportId;
    @Comment("消息编号")
    @Pattern(regexp = "[0-9]{18}", message = "消息编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    @NotNull(message = "消息编号不能为null",groups = HibernateValidator.Insert.class)
    @NotEmpty(message = "消息编号不能为空", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String msgId;
    @Comment("请求xml原文")
    private String reqXml;
    @Comment("响应xml原文")
    private String resXml;
    @Comment("处理结果状态")
    @InRange(clazz = ReportDealStatus.class,message = "报文处理状态必须是在ReportDealStatus中声明的值", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String dealStatus;


    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getReqXml() {
        return reqXml;
    }

    public void setReqXml(String reqXml) {
        this.reqXml = reqXml;
    }

    public String getResXml() {
        return resXml;
    }

    public void setResXml(String resXml) {
        this.resXml = resXml;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    @Override protected Serializable pkVal() {
        return this.reportId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("ReportDeal{");
        sb.append("reportId='").append(reportId).append('\'');
        sb.append(", msgId='").append(msgId).append('\'');
        sb.append(", reqXml='").append(reqXml).append('\'');
        sb.append(", resXml='").append(resXml).append('\'');
        sb.append(", dealStatus='").append(dealStatus).append('\'');
        sb.append(", orgaId='").append(orgaId).append('\'');
        sb.append(", orgaName='").append(orgaName).append('\'');
        sb.append(", deptId='").append(deptId).append('\'');
        sb.append(", deptName='").append(deptName).append('\'');
        sb.append(", actId='").append(actId).append('\'');
        sb.append(", actName='").append(actName).append('\'');
        sb.append(", addDate='").append(addDate).append('\'');
        sb.append(", addTime='").append(addTime).append('\'');
        sb.append(", addByName='").append(addByName).append('\'');
        sb.append(", addById='").append(addById).append('\'');
        sb.append(", backup1='").append(backup1).append('\'');
        sb.append(", backup2='").append(backup2).append('\'');
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
