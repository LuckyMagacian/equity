package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.lanxi.equity.assist.Comment;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户
 * @author yangyuanjian created in 2018/2/6 14:21
 */
@Comment("用户")
public class UserAccount extends OrgaDeptAct {

    @TableId("user_id")
    @Comment("用户编号")
    private String userId;

    @Comment("机构自定用户编号")
    private String orgaUserId;

    @Comment("用户姓名")
    private String userName;

    @Comment("用户账户权益值 | 滞后 具体以 权益实例的累计为准")
    private Long equity;

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

    public Long getEquity() {
        return equity;
    }

    public void setEquity(Long equity) {
        this.equity = equity;
    }



    @Override protected Serializable pkVal() {
        return this.userId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("UserAccount{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", equity=").append(equity);
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
