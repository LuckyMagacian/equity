package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.CommStatus;
import com.lanxi.equity.config.CommodityType;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * 商品
 * @author yangyuanjian created in 2018/2/5 19:52
 */
@Comment("商品")
@TableName("commodity")
public class Commodity extends Model<Commodity> {

    @TableId("comm_id")
    @Comment("商品编号")
    @Pattern(regexp = "[0-9]{18}",message = "编号必须位18位数字")
    private String commId;

    @Comment("电子礼品平台商品编号")
    private String eleCommId;

    @Comment("商品类型")
    @InRange(clazz = CommodityType.class,message = "商品类型必须是在CommodityTYpe中声明的值")
    private String commType;

    @Comment("商品名称")
    private String commName;

    @Comment("分类标签列表")
    private List<String> lables;

    @Comment("有效期")
    private Integer validate;

    @Comment("使用规则")
    private String useRule;

    @Comment("可用门店列表")
    private List<String> acceptShops;

    @Comment("不可用门店列表")
    private List<String> refuseShops;

    @Comment("商品描述")
    private String commDesc;

    @Comment("商品总库存")
    private Long total;

    @Comment("商品剩余库存")
    private Long inventory;

    @Comment("添加日期")
    @Pattern(regexp = "[0-9]{8}",message = "日期必须为8位数字")
    private String addDate;

    @Comment("添加时间")
    @Pattern(regexp = "[0-6]{8}",message = "时间必须为6位数字")

    private String addTime;

    @Comment("添加者")
    private String addBy;

    @Comment("商品价值")
    private Integer value;

    @Comment("商品所需权益值")
    private Integer price;

    @Comment("商品状态")
    @InRange(clazz = CommStatus.class,message = "商品状态必须是在CommStatus中声明的值")
    private String commStatus;

    @Comment("单用户兑换上限")
    private Integer userLimit;

    @Comment("单用户日兑换上限")
    private Integer userDailyLimit;

    @Comment("日兑换上限")
    private Integer dailyLimit;

    @Comment("备用字段1")
    private String backup1;

    @Comment("备用字段2")
    private String backup2;

    @Comment("乐观锁字段")
    @Version
    private Long version=1l;

    public String getEleCommId() {
        return eleCommId;
    }

    public void setEleCommId(String eleCommId) {
        this.eleCommId = eleCommId;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public List<String> getLables() {
        return lables;
    }

    public void setLables(List<String> lables) {
        this.lables = lables;
    }

    public Integer getValidate() {
        return validate;
    }

    public void setValidate(Integer validate) {
        this.validate = validate;
    }

    public String getUseRule() {
        return useRule;
    }

    public void setUseRule(String useRule) {
        this.useRule = useRule;
    }

    public List<String> getAcceptShops() {
        return acceptShops;
    }

    public void setAcceptShops(List<String> acceptShops) {
        this.acceptShops = acceptShops;
    }

    public List<String> getRefuseShops() {
        return refuseShops;
    }

    public void setRefuseShops(List<String> refuseShops) {
        this.refuseShops = refuseShops;
    }

    public String getCommDesc() {
        return commDesc;
    }

    public void setCommDesc(String commDesc) {
        this.commDesc = commDesc;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCommStatus() {
        return commStatus;
    }

    public void setCommStatus(String commStatus) {
        this.commStatus = commStatus;
    }

    public Integer getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(Integer userLimit) {
        this.userLimit = userLimit;
    }

    public Integer getUserDailyLimit() {
        return userDailyLimit;
    }

    public void setUserDailyLimit(Integer userDailyLimit) {
        this.userDailyLimit = userDailyLimit;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
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

    @Override protected Serializable pkVal() {
        return this.commId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("Commodity{");
        sb.append("commId='").append(commId).append('\'');
        sb.append(", commName='").append(commName).append('\'');
        sb.append(", lables=").append(lables);
        sb.append(", validate=").append(validate);
        sb.append(", useRule='").append(useRule).append('\'');
        sb.append(", acceptShops=").append(acceptShops);
        sb.append(", refuseShops=").append(refuseShops);
        sb.append(", commDesc='").append(commDesc).append('\'');
        sb.append(", total=").append(total);
        sb.append(", inventory=").append(inventory);
        sb.append(", addDate='").append(addDate).append('\'');
        sb.append(", addTime='").append(addTime).append('\'');
        sb.append(", addBy='").append(addBy).append('\'');
        sb.append(", value=").append(value);
        sb.append(", price=").append(price);
        sb.append(", commStatus='").append(commStatus).append('\'');
        sb.append(", userLimit=").append(userLimit);
        sb.append(", userDailyLimit=").append(userDailyLimit);
        sb.append(", dailyLimit=").append(dailyLimit);
        sb.append(", backup1='").append(backup1).append('\'');
        sb.append(", backup2='").append(backup2).append('\'');
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
