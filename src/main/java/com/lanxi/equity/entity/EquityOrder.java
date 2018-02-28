package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.OrderStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * 权益兑换订单
 * @author yangyuanjian created in 2018/2/6 13:40
 */
@Comment("权益兑换订单")
public class EquityOrder extends OrgaDeptAct{

    @TableId("order_id")
    @Comment("订单编号")
    @Pattern(regexp = "[0-9]{18}",message = "权益兑换订单编号必须位18位数字")
    private String orderId;

    @Comment("兑换记录编号")
    @Pattern(regexp = "[0-9]{18}",message = "权益兑换记录编号必须位18位数字")
    private String exRecordId;

    @Comment("用户编号")
    private String userId;

    @Comment("商品编号")
    @Pattern(regexp = "[0-9]{18}",message = "商品编号必须位18位数字")
    private String commId;

    @Comment("电子礼品平台商品编号")
    private String eleCommId;

    @Comment("商品名称")
    private String commName;

    @Comment("商品数量")
    @Max(value = 100,message = "兑换数量最多为100")
    @Min(value = 1,message = "兑换数量最少为1")
    private String commCount;

    @Comment("订单状态")
    @InRange(clazz = OrderStatus.class,message = "订单状态必须是在OrderStatus中申明的值")
    private String orderStatus;

    @Comment("电子礼品返回码")
    private String retCode;

    @Comment("电子礼品返回信息")
    private String retMsg;

    @Comment("兑换串码列表")
    private List<String> codes;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getExRecordId() {
        return exRecordId;
    }

    public void setExRecordId(String exRecordId) {
        this.exRecordId = exRecordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCommCount() {
        return commCount;
    }

    public void setCommCount(String commCount) {
        this.commCount = commCount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    @Override protected Serializable pkVal() {
        return this.orderId;
    }

    public String getEleCommId() {
        return eleCommId;
    }

    public void setEleCommId(String eleCommId) {
        this.eleCommId = eleCommId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("EquityOrder{");
        sb.append("codes=").append(codes);
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
