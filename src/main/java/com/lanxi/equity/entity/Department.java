package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.Status;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 机构部门
 * @author yangyuanjian created in 2018/2/5 19:00
 */
@Comment("机构部门")
@TableName("department")
public class Department extends Model<Department> {

    @TableId("dept_id")
    @Comment("部门编号")
    @NotNull(message = "部门编号不能为Null",groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{18}",message = "部门编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String deptId;

    @Comment("部门名称")
    private String deptName;

    @Comment("机构编号")
    @NotNull(message = "机构编号不能为Null",groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{18}",message = "机构编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String orgaId;

    @Comment("机构名称")
    private String orgaName;
//
//    @Comment("机构自定部门编号")
//    private String orgaDeptId;

    @Comment("部门描述")
    private String deptDesc;

    @Comment("部门状态")
    @NotNull(message = "部门状态不能为Null",groups = HibernateValidator.Insert.class)
    @InRange(clazz = Status.class,message = "部门状态必须是在Status里定义的值", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String deptStatus;

    @Comment("添加日期")
    @NotNull(message = "添加日期不能为Null",groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{8}",message = "日期必须为8位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String addDate;

    @Comment("添加时间")
    @NotNull(message = "添加时间不能为Null",groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-6]{8}",message = "时间必须为6位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String addTime;

    @Comment("添加者")
    private String addBy;

    @Comment("备用字段1")
    private String backup1;

    @Comment("备用字段2")
    private String backup2;

    @Comment("乐观锁字段")
    @NotNull(message = "乐观锁字段不能为Null",groups = HibernateValidator.Insert.class)
    @Version
    private Long version=1l;

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

//    public String getOrgaDeptId() {
//        return orgaDeptId;
//    }
//
//    public void setOrgaDeptId(String orgaDeptId) {
//        this.orgaDeptId = orgaDeptId;
//    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public String getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
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
        return this.deptId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("Department{");
        sb.append("deptId='").append(deptId).append('\'');
        sb.append(", deptName='").append(deptName).append('\'');
        sb.append(", orgaId='").append(orgaId).append('\'');
        sb.append(", orgaName='").append(orgaName).append('\'');
//        sb.append(", orgaDeptId='").append(orgaDeptId).append('\'');
        sb.append(", deptDesc='").append(deptDesc).append('\'');
        sb.append(", deptStatus='").append(deptStatus).append('\'');
        sb.append(", addDate='").append(addDate).append('\'');
        sb.append(", addTime='").append(addTime).append('\'');
        sb.append(", addBy='").append(addBy).append('\'');
        sb.append(", backup1='").append(backup1).append('\'');
        sb.append(", backup2='").append(backup2).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
