package com.lanxi.equity.entity;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.assist.ToJson;
import com.lanxi.equity.config.ExStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * 权益兑换记录
 *
 * @author yangyuanjian created in 2018/2/5 20:32
 */
@Comment("权益兑换记录")
@TableName("equity_exchange_record")
public class EquityExchangeRecord extends OrgaDeptAct {

    @TableId("record_id")
    @Comment("记录编号")
    @NotNull(message = "兑换记录编号不能为null", groups = {HibernateValidator.Insert.class})
    @Pattern(regexp = "[0-9]{18}", message = "权益兑换记录编号必须位18位数字", groups = {HibernateValidator.Insert.class, HibernateValidator.AsArg.class})
    private String recordId;

    @Comment("用户编号")
    @NotNull(message = "用户编号不能为null", groups = {HibernateValidator.Insert.class})
    @NotEmpty(message = "用户编号不能为空!", groups = {HibernateValidator.Insert.class, HibernateValidator.AsArg.class, HibernateValidator.Update.class})
    private String userId;

    @Comment("机构自定用户编号")
    private String orgaUserId;

    @Comment("用户姓名")
    private String userName;

    @Comment("商品编号")
    @NotNull(message = "商品编号不能为null", groups = {HibernateValidator.Insert.class})
    @Pattern(regexp = "([0-9]{18})|([0-9]{4})", message = "编号必须为18位数字", groups = {HibernateValidator.Insert.class, HibernateValidator.AsArg.class})
    private String commId;

    @Comment("商品名称")
    private String commName;

    @Comment("兑换手机号")
    private String phone;

    @Comment("兑换数量")
    @NotNull(message = "兑换数量不能为null", groups = {HibernateValidator.Insert.class})
    private String commNum;

    @Comment("串码列表")
    private List<String> codes;

    @Comment("兑换结果")
    private String result;

    @Comment("兑换状态")
    @NotNull(message = "兑换状态不能为null", groups = {HibernateValidator.Insert.class})
    @InRange(clazz = ExStatus.class, message = "兑换状态必须是在ExStatus中声明的值", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String exStatus;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommNum() {
        return commNum;
    }

    public void setCommNum(String commNum) {
        this.commNum = commNum;
    }

    public List<String> getCodesProto() {
        return codes;
    }

    public void setCodesProto(List<String> codes) {
        this.codes = codes;
    }

    public String getCodes() {
        return JSONArray.toJSONString(this.codes);
    }

    public void setCodes(String codes) {
        this.codes=JSONArray.parseArray(codes,String.class);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExStatus() {
        return exStatus;
    }

    public void setExStatus(String exStatus) {
        this.exStatus = exStatus;
    }

    @Override protected Serializable pkVal() {
        return this.recordId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("EquityExchangeRecord{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", orgaUserId='").append(orgaUserId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", commId='").append(commId).append('\'');
        sb.append(", commName='").append(commName).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", commNum='").append(commNum).append('\'');
        sb.append(", codes=").append(codes);
        sb.append(", result='").append(result).append('\'');
        sb.append(", exStatus='").append(exStatus).append('\'');
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
