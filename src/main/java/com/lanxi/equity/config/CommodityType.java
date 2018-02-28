package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 商品类型
 * @author yangyuanjian created in 2018/2/6 19:32
 */
@Comment("商品类型")
public interface CommodityType {
    @Comment("商品类型-电子券")
    String ELE_COUPON="30";
    @Comment("商品类型-话费")
    String TEL_CHARGE="40";
    @Comment("商品类型-实物")
    String ENTITY="50";

}