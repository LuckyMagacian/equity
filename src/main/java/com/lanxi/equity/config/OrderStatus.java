package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 电子礼品平台订单状态
 * @author yangyuanjian created in 2018/2/8 11:32
 */
@Comment("电子礼品平台订单状态")
public interface OrderStatus {
    @Comment("订单状态-待处理")
    String WAIT_DEAL="10";
    @Comment("订单状态-处理中")
    String DEALING="20";
    @Comment("订单状态-成功")
    String SUCCESS="30";
    @Comment("订单状态-失败")
    String FAIL="31";
    @Comment("订单状态-异常")
    String EXCEPTION="32";
    @Comment("订单状态-取消")
    String CANCELED="33";
    @Comment("订单状态-删除")
    String DELETED="40";
    @Comment("测试订单")
    String TEST="50";
}
