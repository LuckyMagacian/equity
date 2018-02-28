package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.lanxi.equity.assist.Comment;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 操作记录
 * @author yangyuanjian created in 2018/2/6 14:57
 */
@Comment("操作记录")
public class OperateRecord extends OrgaDeptAct{

    @TableId("record_id")
    @Comment("操作记录编号")
    @Pattern(regexp = "[0-9]{18}",message = "操作记录编号必须位18位数字")
    private String recordId;

    @Comment("操作类型")
    private String operType;

    @Comment("操作详细类型")
    private String detailType;

    @Comment("操作描述")
    private String operDesc;


    @Override protected Serializable pkVal() {
        return null;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getOperDesc() {
        return operDesc;
    }

    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("OperateRecord{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", operType='").append(operType).append('\'');
        sb.append(", detailType='").append(detailType).append('\'');
        sb.append(", operDesc='").append(operDesc).append('\'');
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
