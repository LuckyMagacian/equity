package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.Version;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.FunStatus;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 功能接口
 * @author yangyuanjian created in 2018/2/6 14:24
 */
@Comment("功能接口")
public class FunInterface extends Model<FunInterface>{
    @TableId("fun_id")
    @Comment("功能编号")
    @Pattern(regexp = "[0-9]{18}",message = "接口编号必须位18位数字")
    private String funId;

    @Comment("功能名称")
    private String funName;

    @Comment("权限等级")
    private String level;

    @Comment("路由路径")
    private String mapRoute;

    @Comment("所属类名")
    private String className;

    @Comment("所属方法名")
    private String methodName;

    @Comment("功能描述")
    private String funDesc;

    @Comment("功能状态")
    @InRange(clazz = FunStatus.class,message = "接口状态必须是在FunStatus中声明的值")
    private String funStatus;

    @Comment("添加日期")
    @Pattern(regexp = "[0-9]{8}",message = "日期必须为8位数字")
    private String addDate;

    @Comment("添加时间")
    @Pattern(regexp = "[0-6]{8}",message = "时间必须为6位数字")
    private String addTime;

    @Comment("乐观锁字段")
    @Version
    private Long version;



    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMapRoute() {
        return mapRoute;
    }

    public void setMapRoute(String mapRoute) {
        this.mapRoute = mapRoute;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFunDesc() {
        return funDesc;
    }

    public void setFunDesc(String funDesc) {
        this.funDesc = funDesc;
    }

    public String getFunStatus() {
        return funStatus;
    }

    public void setFunStatus(String funStatus) {
        this.funStatus = funStatus;
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override protected Serializable pkVal() {
        return this.funId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("FunInterface{");
        sb.append("funId='").append(funId).append('\'');
        sb.append(", funName='").append(funName).append('\'');
        sb.append(", level='").append(level).append('\'');
        sb.append(", mapRoute='").append(mapRoute).append('\'');
        sb.append(", className='").append(className).append('\'');
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", funDesc='").append(funDesc).append('\'');
        sb.append(", funStatus='").append(funStatus).append('\'');
        sb.append(", addDate='").append(addDate).append('\'');
        sb.append(", addTime='").append(addTime).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
