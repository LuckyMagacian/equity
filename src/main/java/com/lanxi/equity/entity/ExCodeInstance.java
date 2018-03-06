package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.CodeInstanceStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 兑换码实例
 * @author yangyuanjian created in 2018/2/6 14:17
 */
@Comment("兑换码实例")
@TableName("ex_code_instance")
public class ExCodeInstance extends OrgaDeptAct {

    @TableId("instance_id")
    @Comment("实例编号")
    @NotNull(message = "兑换码实例编号不能为null",groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{18}",message = "兑换码实例编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String instanceId;

    @Comment("兑换码编号")
    @NotNull(message = "兑换码编号不能为null",groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{18}",message = "兑换码编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String exCodeId;

    @Comment("兑换码信息")
    private String exCodeInfo;

    @Comment("实例状态")
    @NotNull(message = "实例状态不能为null",groups = HibernateValidator.Insert.class)
    @InRange(clazz = CodeInstanceStatus.class,message = "兑换码实例状态必须是在CodeInstanceStatus中声明的值", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String instanceStatus;

    @Comment("过期日期")
    @Pattern(regexp = "[0-9]{8}",message = "日期必须为8位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String overDate;

    @Comment("过期时间")
    @Pattern(regexp = "[0-6]{6}",message = "时间必须为6位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String overTime;

    @Comment("兑换日期")
    @Pattern(regexp = "[0-9]{8}",message = "日期必须为8位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String exDate;

    @Comment("兑换时间")
    @Pattern(regexp = "[0-6]{6}",message = "时间必须为6位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String exTime;

    @Comment("兑换码")
    @NotNull(message = "兑换码不能为null",groups = HibernateValidator.Insert.class)
    private String code;

    @Comment("绑定手机")
    private String bindPhone;

    @Comment("权益值")
    @NotNull(message = "权益值不能为null",groups = HibernateValidator.Insert.class)
    private Integer value;

    @Comment("有效期(天)")
    private Integer validate;


    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

    public String getExTime() {
        return exTime;
    }

    public void setExTime(String exTime) {
        this.exTime = exTime;
    }

    public Integer getValidate() {
        return validate;
    }

    public void setValidate(Integer validate) {
        this.validate = validate;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId+"";
    }

    public String getExCodeId() {
        return exCodeId;
    }

    public void setExCodeId(String exCodeId) {
        this.exCodeId = exCodeId;
    }

    public String getExCodeInfo() {
        return exCodeInfo;
    }

    public void setExCodeInfo(String exCodeInfo) {
        this.exCodeInfo = exCodeInfo;
    }

    public String getInstanceStatus() {
        return instanceStatus;
    }

    public void setInstanceStatus(String instanceStatus) {
        this.instanceStatus = instanceStatus;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }

    @Override protected Serializable pkVal() {
        return null;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("ExCodeInstance{");
        sb.append("instanceId='").append(instanceId).append('\'');
        sb.append(", exCodeId='").append(exCodeId).append('\'');
        sb.append(", exCodeInfo='").append(exCodeInfo).append('\'');
        sb.append(", instanceStatus='").append(instanceStatus).append('\'');
        sb.append(", overDate='").append(overDate).append('\'');
        sb.append(", overTime='").append(overTime).append('\'');
        sb.append(", exDate='").append(exDate).append('\'');
        sb.append(", exTime='").append(exTime).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", bindPhone='").append(bindPhone).append('\'');
        sb.append(", value=").append(value);
        sb.append(", validate=").append(validate);
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
