package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.GetFieldValue;

/**
 * 活动状态<br>
 *
 * @author yangyuanjian created in 2018/2/6 19:42
 */
public interface ActStatus extends GetFieldValue{

    @Comment("活动状态-待开始")

    String WAIT_START = "10";
    @Comment("活动状态-进行中")

    String DURING     = "20";
    @Comment("活动状态-暂停")

    String PAUSE      = "21";
    @Comment("活动状态-结束")

    String END        = "30";
    @Comment("活动状态-终止")

    String STOP       = "31";
    @Comment("活动状态-删除")
    String DELETED    = "40";
}
