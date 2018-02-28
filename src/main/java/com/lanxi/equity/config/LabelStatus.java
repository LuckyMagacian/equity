package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 标签状态
 * @author yangyuanjian created in 2018/2/8 14:12
 */
@Comment("标签状态")
public interface LabelStatus {
    @Comment("标签状态-正常使用")
    String NORMAL="10";
    @Comment("标签状态-已注销")
    String CANCELED="20";
    @Comment("标签状态-过期")
    String OVERTIME="21";
    @Comment("标签状态-删除")
    String DELETED="30";
}
