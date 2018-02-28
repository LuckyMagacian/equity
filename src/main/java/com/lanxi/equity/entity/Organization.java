package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.Status;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 合作机构
 *
 * @author yangyuanjian created in 2018/2/5 18:47
 */
@Comment("合作机构")
@TableName("organization")
public class Organization extends Model<Organization> {

    @TableId("org_id")
    @Comment("机构编号")
    @Pattern(regexp = "[0-9]{18}",message = "机构编号必须位18位数字")
    private String orgId;

    @Comment("机构名称")
    private String orgName;

    @Comment("机构地址")
    private String orgAddress;

    @Comment("机构描述")
    private String orgDesc;

    @Comment("机构状态")
    @InRange(clazz = Status.class,message = "机构状态必须是在Status中声明的值")
    private String orgStatus;

    @Comment("添加者")
    private String addBy;

    @Comment("添加日期")
    @Pattern(regexp = "[0-9]{8}",message = "日期必须为8位数字")
    private String addDate;

    @Comment("添加时间")
    @Pattern(regexp = "[0-6]{8}",message = "时间必须为6位数字")

    private String addTime;

    @Comment("电子邮箱")
    @Email(regexp = "\\w+@\\w\\.\\w",message = "邮箱格式错误!")
    private String email;

    @Comment("联系电话")
    private String phone;

    @Comment("备用字段1")
    private String backup1;

    @Comment("备用字段2")
    private String backup2;

    @Comment("乐观锁字段")
    @Version
    private Long version=1l;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        return this.orgId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("Organization{");
        sb.append("orgId='").append(orgId).append('\'');
        sb.append(", orgName='").append(orgName).append('\'');
        sb.append(", orgAddress='").append(orgAddress).append('\'');
        sb.append(", orgDesc='").append(orgDesc).append('\'');
        sb.append(", orgStatus='").append(orgStatus).append('\'');
        sb.append(", addBy='").append(addBy).append('\'');
        sb.append(", addDate='").append(addDate).append('\'');
        sb.append(", addTime='").append(addTime).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", backup1='").append(backup1).append('\'');
        sb.append(", backup2='").append(backup2).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
