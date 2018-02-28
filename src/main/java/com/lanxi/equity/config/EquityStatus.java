package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 权益状态
 * @author yangyuanjian created in 2018/2/8 11:15
 */
public interface EquityStatus {
    @Comment("正常未使用")
    String NORMAL   = "10";
    @Comment("部分使用")
    String USING    = "20";
    @Comment("已用完")
    String USAED    = "30";
    @Comment("已过期")
    String OVERTIME = "31";
    @Comment("已注销")
    String CANCELED = "32";
    @Comment("已删除")
    String DELETED  = "50";
}
