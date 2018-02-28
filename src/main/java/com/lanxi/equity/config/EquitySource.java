package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 权益来源
 * @author yangyuanjian created in 2018/2/8 13:58
 */
@Comment("权益值来源")
public interface EquitySource {
    @Comment("权益来源-兑换码兑换")
    String CODE_EX="10";
    @Comment("权益来源-接口调用")
    String FUN_ADD="11";
}
