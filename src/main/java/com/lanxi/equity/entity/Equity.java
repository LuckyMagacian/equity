package com.lanxi.equity.entity;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.assist.ToJson;
import com.lanxi.equity.config.EquitySource;
import com.lanxi.equity.config.EquityStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

/**
 * 权益
 *
 * @author yangyuanjian created in 2018/2/5 20:08
 */
@Comment("权益")
@TableName("equity")
public class Equity extends OrgaDeptAct {

    @TableId("equity_id")
    @Comment("权益编号")
    @NotNull(message = "权益编号不能为空", groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{18}", message = "权益编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String equityId;

    @Comment("用户编号")
    @NotNull(message = "用户编号不能为空", groups = {HibernateValidator.Insert.class, HibernateValidator.AsArg.class})
    @Pattern(regexp = "[0-9]{18}", message = "用户编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String userId;

    @Comment("机构自定用户编号")
    @Deprecated
    private String orgaUserId;

    @Comment("用户名称")
    private String userName;

    @Comment("权益来源")
    @InRange(clazz = EquitySource.class, message = "权益来源必须是在EquitySource中声明的值")
    private String source;

    @Comment("权益新增原因")
    private String reason;

    @Comment("总权益值")
    private Integer total;

    @Comment("剩余可用权益值")
    private Integer useful;

    @Comment("有效期")
    private Integer validate;

    @Comment("过期日期")
    @Pattern(regexp = "[0-9]{8}", message = "日期必须为8位数字")
    private String overDate;

    @Comment("过期时间")
    @Pattern(regexp = "[0-6]{8}", message = "时间必须为6位数字")
    private String overTime;

    @Comment("兑换凭证列表")
    private List<String> codeTokens;

    @Comment("权益值状态")
    @InRange(clazz = EquityStatus.class, message = "权益值状态必须是在EquityStatus中声明的值")
    private String equityStatus;

    public String getEquityId() {
        return equityId;
    }

    public void setEquityId(String equityId) {
        this.equityId = equityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgaUserId() {
        return orgaUserId;
    }

    public void setOrgaUserId(String orgaUserId) {
        this.orgaUserId = orgaUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getUseful() {
        return useful;
    }

    public void setUseful(Integer useful) {
        this.useful = useful;
    }

    public Integer getValidate() {
        return validate;
    }

    public void setValidate(Integer validate) {
        this.validate = validate == null ? this.validate
                                         : this.validate == null ? validate
                                                                 : this.validate > validate ? validate :
                                                                   this.validate;
    }

    public String getOverDate() {
        return overDate;
    }

    public void setOverDate(String overDate) {
        this.overDate = overDate;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getCodeTokens() {
        return ToJson.collectionToJsonArray(this.codeTokens).toJSONString();
    }

    public void setCodeTokens(String codeTokens) {
        this.codeTokens = JSONArray.parseArray(codeTokens, String.class);
    }

    public List<String> getCodeTokensProto() {
        return codeTokens;
    }

    public void setCodeTokensProto(List<String> codeTokens) {
        this.codeTokens = codeTokens;
    }

    public String getEquityStatus() {
        return equityStatus;
    }

    public void setEquityStatus(String equityStatus) {
        this.equityStatus = equityStatus;
    }

    @Override protected Serializable pkVal() {
        return this.equityId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("Equity{");
        sb.append("equityId='").append(equityId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", orgaUserId='").append(orgaUserId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", source='").append(source).append('\'');
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", total=").append(total);
        sb.append(", useful=").append(useful);
        sb.append(", validate=").append(validate);
        sb.append(", overDate='").append(overDate).append('\'');
        sb.append(", overTime='").append(overTime).append('\'');
        sb.append(", codeTokens=").append(codeTokens);
        sb.append(", equityStatus='").append(equityStatus).append('\'');
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
