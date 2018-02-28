package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 兑换码实例状态
 * @author yangyuanjian created in 2018/2/8 14:04
 */
@Comment("兑换码实例状态")
public interface CodeInstanceStatus {
    @Comment("兑换码实例状态-待发放")
    String WAIT_OUPUT="10";
    @Comment("兑换码实例状态-正常")
    String NORMAL="20";
    @Comment("兑换码实例状态-已绑定")
    String BINDING="21";
    @Comment("兑换码实例状态-已使用")
    String USED="30";
    @Comment("兑换码实例状态-已过期")
    String OVERTIME="31";
    @Comment("兑换码实例状态-已注销")
    String CANCELED="32";
    @Comment("兑换码实例状态-已删除")
    String DELETED="40";
    @Comment("兑换码实例状态-未知,仅限业务逻辑中使用")
    String UNKNOWN="50";
}
