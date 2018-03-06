package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.Version;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.ExtractFields;
import com.lanxi.equity.assist.HibernateValidator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 机构 部门 活动 公有字段
 * @author yangyuanjian created in 2018/2/6 13:50
 */
@Comment("机构部门活动公有字段")
public abstract class OrgaDeptAct extends Model<OrgaDeptAct> {

    @Comment("机构编号")
    @NotNull(message = "机构编号不能为null",groups = {HibernateValidator.Insert.class})
    @Pattern(regexp = "[0-9]{18}",message = "机构编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    protected String orgaId;

    @Comment("机构名称")
    protected String orgaName;

    @Comment("部门编号")
    @Pattern(regexp = "[0-9]{18}",message = "部门编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    protected String deptId;

//    @Comment("机构自定部门编号")
//    protected String orgaDeptId;

    @Comment("部门名称")
    protected String deptName;

    @Comment("活动编号")
    @Pattern(regexp = "[0-9]{18}",message = "活动编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    protected String actId;

//    @Comment("机构自定活动编号")
//    protected String orgaActId;

    @Comment("活动名称")
    protected String actName;

    @Comment("添加日期")
    @Pattern(regexp = "[0-9]{8}",message = "日期必须为8位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    protected String addDate;

    @Comment("添加时间")
    @Pattern(regexp = "[0-6]{8}",message = "时间必须为6位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    protected String addTime;

    @Comment("添加者姓名")
    protected String addByName;

    @Comment("添加者编号")
    @Pattern(regexp = "[0-9]{18}",message = "添加者编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    protected String addById;

    @Comment("备用字段1")
    protected String backup1;

    @Comment("备用字段2")
    protected String backup2;

    @Comment("乐观锁字段")
    @NotNull(message = "乐观锁字段不能为null",groups = {HibernateValidator.Insert.class, HibernateValidator.Update.class})
    @Version
    protected Long version=1L;

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

//    public String getOrgaDeptId() {
//        return orgaDeptId;
//    }
//
//    public void setOrgaDeptId(String orgaDeptId) {
//        this.orgaDeptId = orgaDeptId;
//    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

//    public String getOrgaActId() {
//        return orgaActId;
//    }
//
//    public void setOrgaActId(String orgaActId) {
//        this.orgaActId = orgaActId;
//    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
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

    public String getAddByName() {
        return addByName;
    }

    public void setAddByName(String addByName) {
        this.addByName = addByName;
    }

    public String getAddById() {
        return addById;
    }

    public void setAddById(String addById) {
        this.addById = addById;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("OrgaDeptAct{");
        sb.append("orgaId='").append(orgaId).append('\'');
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

    public void fromOda(OrgaDeptAct oda){
        List<String> excludes= Arrays.asList((new String[]{"version","backup1","backup2","addById","addByName","addDate","addTime"}));
        List<Field> superFields= ExtractFields.getClassFields(OrgaDeptAct.class,false);
//        List<Field> fields=ExtractFields.getClassFields(this.getClass(),true);
//        Map<String,Field> map=fields.stream().collect(Collectors.toMap(f->f.getName(),v->v));
        superFields.stream()
                   .filter(f->!excludes.contains(f.getName()))
                   .forEach(f->{
            f.setAccessible(true);
            try {
                f.set(this,f.get(oda));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("exception occured when init from OrgaDeptAct !");
            }
        });
    }

}
