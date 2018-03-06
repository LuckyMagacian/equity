package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.ActStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 活动
 *
 * @author yangyuanjian created in 2018/2/5 19:14
 */
@Comment("活动")
@TableName("activity")
public class Activity extends Model<Activity> {

    @TableId("act_id")
    @Comment("活动编号")
    @NotNull(message = "活动编号不能为null", groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{18}", message = "活动编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String actId;

    @Comment("活动名称")
    private String actName;

    //    @Comment("机构自定活动编号")
    //    private String orgaActId;

    @Comment("机构编号")
    @NotNull(message = "活动编号不能为null", groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{18}", message = "机构编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String orgaId;

    @Comment("机构名称")
    @NotNull(message = "活动编号不能为null", groups = HibernateValidator.Insert.class)
    private String orgaName;

    @Comment("部门编号")
    @Pattern(regexp = "[0-9]{18}", message = "部门编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String deptId;

    @Comment("部门名称")
    private String deptName;

    //    @Comment("机构自定部门编号")
    //    private String orgaDeptId;

    @Comment("活动描述")
    private String actDesc;

    @Comment("活动开始日期")
    @Pattern(regexp = "[0-9]{8}", message = "日期必须为8位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String startDate;

    @Comment("活动开始时间")
    @Pattern(regexp = "[0-6]{8}", message = "时间必须为6位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String startTime;

    @Comment("活动结束日期")
    @Pattern(regexp = "[0-9]{8}", message = "日期必须为8位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String stopDate;

    @Comment("活动结束时间")
    @Pattern(regexp = "[0-6]{8}", message = "时间必须为6位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })

    private String stopTime;

    @Comment("活动状态")
    @InRange(clazz = ActStatus.class, message = "活动状态必须是在ActStatus中声明的值", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String actStatus;

    @Comment("添加日期")
    @Pattern(regexp = "[0-9]{8}", message = "日期必须为8位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String addDate;

    @Comment("添加时间")
    @Pattern(regexp = "[0-6]{8}", message = "时间必须为6位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String addTime;

    @Comment("添加者")
    @Pattern(regexp = "[0-9]{18}", message = "活动添加者编号必须为18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String addBy;

    @Comment("备用字段1")
    private String backup1;

    @Comment("备用字段2")
    private String backup2;

    @Comment("乐观锁字段")
    @NotNull(message = "乐观锁版本号不能为null",groups = {HibernateValidator.Insert.class, HibernateValidator.Update.class})
    @Version
    private Long version = 1l;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    //    public String getOrgaActId() {
    //        return orgaActId;
    //    }
    //
    //    public void setOrgaActId(String orgaActId) {
    //        this.orgaActId = orgaActId;
    //    }

    public String getOrgaId() {
        return orgaId;
    }

    public void setOrgaId(String orgaId) {
        this.orgaId = orgaId;
    }

    public String getOrgaName() {
        return orgaName;
    }

    public void setOrgaName(String orgaName) {
        this.orgaName = orgaName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    //    public String getOrgaDeptId() {
    //        return orgaDeptId;
    //    }
    //
    //    public void setOrgaDeptId(String orgaDeptId) {
    //        this.orgaDeptId = orgaDeptId;
    //    }

    public String getActDesc() {
        return actDesc;
    }

    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getBackup1() {
        return backup1;
    }

    public void setBackup1(String backup1) {
        this.backup1 = backup1;
    }

    public String getBackup2() {
        return backup2;
    }

    public void setBackup2(String backup2) {
        this.backup2 = backup2;
    }

    @Override protected Serializable pkVal() {
        return this.actId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("Activity{");
        sb.append("actId='").append(actId).append('\'');
        sb.append(", actName='").append(actName).append('\'');
        //        sb.append(", orgaActId='").append(orgaActId).append('\'');
        sb.append(", orgaId='").append(orgaId).append('\'');
        sb.append(", orgaName='").append(orgaName).append('\'');
        sb.append(", deptId='").append(deptId).append('\'');
        sb.append(", deptName='").append(deptName).append('\'');
        //        sb.append(", orgaDeptId='").append(orgaDeptId).append('\'');
        sb.append(", actDesc='").append(actDesc).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", stopDate='").append(stopDate).append('\'');
        sb.append(", stopTime='").append(stopTime).append('\'');
        sb.append(", actStatus='").append(actStatus).append('\'');
        sb.append(", addDate='").append(addDate).append('\'');
        sb.append(", addTime='").append(addTime).append('\'');
        sb.append(", addBy='").append(addBy).append('\'');
        sb.append(", backup1='").append(backup1).append('\'');
        sb.append(", backup2='").append(backup2).append('\'');
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
