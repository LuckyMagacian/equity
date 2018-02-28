package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.Status;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/6 14:53
 */
public class ManageAccount extends OrgaDeptAct {

    @TableId("user_id")
    @Comment("管理员编号")
    @Pattern(regexp = "[0-9]{18}",message = "管理员用户编号必须位18位数字")
    private String userId;

    @Comment("管理员姓名")
    private String userName;

    @Comment("管理权限等级")
    private String level;

    @Comment("越级接口列表")
    private List<String> boostFuns;

    @Comment("账户状态")
    @InRange(clazz = Status.class,message = "管理员账户状态必须是在Status中声明的值")
    private String accountStatus;

    @Comment("账户密码")
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getBoostFuns() {
        return boostFuns;
    }

    public void setBoostFuns(List<String> boostFuns) {
        this.boostFuns = boostFuns;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override protected Serializable pkVal() {
        return this.userId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("ManageAccount{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", level='").append(level).append('\'');
        sb.append(", boostFuns=").append(boostFuns);
        sb.append(", accountStatus='").append(accountStatus).append('\'');
        sb.append(", password='").append(password).append('\'');
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
