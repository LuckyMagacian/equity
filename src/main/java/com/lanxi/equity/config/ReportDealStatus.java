package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 报文处理状态
 * @author yangyuanjian created in 2018/2/11 10:22
 */
@Comment("报文处理状态")
public interface ReportDealStatus {
    @Comment("待处理")
    String WAIT_DEAL="10";
    @Comment("处理中")
    String DEALING="20";
    @Comment("处理成功")
    String SUCCESS="30";
    @Comment("处理失败")
    String FAIL="31";
    @Comment("发生异常")
    String EXCEPTION="32";
    @Comment("取消")
    String CANCELED="40";
    @Comment("已删除")
    String DELETED="50";
}
