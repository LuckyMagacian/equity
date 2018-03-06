package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.LabelStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 标签
 * @author yangyuanjian created in 2018/2/5 19:20
 */
@TableName("label")
public class Label extends OrgaDeptAct{

    @TableId("lable_id")
    @Comment("标签编号")
    @NotNull(message = "标签编号不能为null", groups = {HibernateValidator.Insert.class})
    @Pattern(regexp = "[0-9]{18}",message = "商品分类标签编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String labelId;

    @Comment("标签名称")
    private String labelName;

    @Comment("标签描述")
    private String labelDesc;

    @Comment("添加者")
    private String addBy;

    @Comment("标签状态")
    @NotNull(message = "标签状态不能为null", groups = {HibernateValidator.Insert.class})
    @InRange(clazz = LabelStatus.class,message = "标签状态必须是在LabelStatus中声明的值", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.AsArg.class,
            HibernateValidator.Update.class
    })
    private String labelStatus;

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelDesc() {
        return labelDesc;
    }

    public void setLabelDesc(String labelDesc) {
        this.labelDesc = labelDesc;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getLabelStatus() {
        return labelStatus;
    }

    public void setLabelStatus(String labelStatus) {
        this.labelStatus = labelStatus;
    }

    @Override protected Serializable pkVal() {
        return this.labelId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("Label{");
        sb.append("labelId='").append(labelId).append('\'');
        sb.append(", labelName='").append(labelName).append('\'');
        sb.append(", labelDesc='").append(labelDesc).append('\'');
        sb.append(", addBy='").append(addBy).append('\'');
        sb.append(", labelStatus='").append(labelStatus).append('\'');
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
