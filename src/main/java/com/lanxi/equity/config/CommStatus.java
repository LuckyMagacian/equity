package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 商品状态
 * @author yangyuanjian created in 2018/2/6 19:54
 */
public interface CommStatus {
    @Comment("上架")
    String UP="10";

    @Comment("上架转下架")
    String UP_TO_DOWN="11";

    @Comment("下架")
    String DOWN="20";

    @Comment("下架转上架")
    String DOWN_TO_UP="21";

    @Comment("缺货")
    String NONE="30";

    @Comment("删除")
    String DELETED="40";
}
