package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.EquityReDetailType;
import com.lanxi.equity.config.EquityReType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 权益兑换记录
 *
 * @author yangyuanjian created in 2018/2/5 20:24
 */
@Comment("权益兑换记录")
@TableName("equity_record")
public class EquityRecord extends OrgaDeptAct {


    @TableId("record_id")
    @Comment("记录编号")
    @NotNull(message = "记录编号不能为null!", groups = {HibernateValidator.Insert.class})
    @Pattern(regexp = "[0-9]{18}", message = "权益兑换记录编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String recordId;

    @Comment("用户编号")
    @NotNull(message = "用户编号不能为null!", groups = {HibernateValidator.Insert.class})
    @NotEmpty(message = "用户编号不能为空!", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String userId;

    //    @Comment("机构自定用户编号")
    //    private String orgaUserId;

    @Comment("用户姓名")
    private String userName;

    @Comment("记录类型  增加 | 减少")
    @NotNull(message = "记录类型不能为null!", groups = {HibernateValidator.Insert.class})
    @InRange(clazz = EquityReType.class, message = "记录类型必须是在EquityReType中声明的值", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String recordType;

    @Comment("详细类型 增加渠道 | 减少渠道 理由")
    @NotNull(message = "详细类型不能为null!", groups = {HibernateValidator.Insert.class})
    @InRange(clazz = EquityReDetailType.class, message = "权益记录详细类型必须是在EquityReDetailType中声明的值", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String detailType;

    @Comment("记录描述")
    private String recordDesc;

    @Comment("变动积分值")
    @NotNull(message = "变动积分值不能为null!", groups = {HibernateValidator.Insert.class})
    @Pattern(regexp = "[\\+\\-]{1}[0-9]+", message = "变动积分值属性异常", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String veriationValue;

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

    //    public String getOrgaUserId() {
    //        return orgaUserId;
    //    }
    //
    //    public void setOrgaUserId(String orgaUserId) {
    //        this.orgaUserId = orgaUserId;
    //    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getRecordDesc() {
        return recordDesc;
    }

    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }

    public String getVeriationValue() {
        return veriationValue;
    }

    public void setVeriationValue(String veriationValue) {
        this.veriationValue = veriationValue;
    }

    @Override protected Serializable pkVal() {
        return this.recordId;
    }


    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("EquityRecord{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        //        sb.append(", orgaUserId='").append(orgaUserId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", recordType='").append(recordType).append('\'');
        sb.append(", detailType='").append(detailType).append('\'');
        sb.append(", recordDesc='").append(recordDesc).append('\'');
        sb.append(", veriationValue='").append(veriationValue).append('\'');
        sb.append(", orgaId='").append(orgaId).append('\'');
        sb.append(", orgaName='").append(orgaName).append('\'');
        sb.append(", deptId='").append(deptId).append('\'');
        //        sb.append(", orgaDeptId='").append(orgaDeptId).append('\'');
        sb.append(", deptName='").append(deptName).append('\'');
        sb.append(", actId='").append(actId).append('\'');
        //        sb.append(", orgaActId='").append(orgaActId).append('\'');
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
