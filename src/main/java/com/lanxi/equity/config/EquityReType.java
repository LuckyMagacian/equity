package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 权益记录类型
 * @author yangyuanjian created in 2018/2/8 13:52
 */
@Comment("权益记录类型")
public interface EquityReType {
    @Comment("累加")
    String PLUS="0";
    @Comment("扣减")
    String SUB="1";
}
