package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 权益记录详细来源
 * @author yangyuanjian created in 2018/2/8 13:56
 */
@Comment("权益记录详细类型")
public interface EquityReDetailType {
    @Comment("权益值记录详细类型-兑换码兑换")
    String CODE_EX="10";
    @Comment("权益值记录详细类型-接口调用累加")
    String FUN_ADD="11";
    @Comment("权益值记录详细类型-兑换商品扣减")
    String EX_SUB="20";
    @Comment("权益值记录详细类型-接口调用扣减")
    String FUN_SUB="21";
}
