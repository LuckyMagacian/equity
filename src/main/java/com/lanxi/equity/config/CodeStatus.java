package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 兑换码状态
 * @author yangyuanjian created in 2018/2/8 14:02
 */
@Comment("兑换码状态")
public interface CodeStatus {
    @Comment("兑换码状态-正常")
    String NORMAL="10";
    @Comment("兑换码状态-暂停生成")
    String PAUSE="20";
    @Comment("兑换码状态-作废")
    String CANCELED="30";
    @Comment("兑换码状态-删除")
    String DELETED="40";
}
