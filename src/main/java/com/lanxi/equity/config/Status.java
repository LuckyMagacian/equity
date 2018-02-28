package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

import java.io.Serializable;

/**
 * 通用状态
 *
 * @author yangyuanjian created in 2018/2/6 9:07
 */
@Comment("通用状态")
public interface Status {

    @Comment("通用状态-正常")
    String NORMAL = "10";

    @Comment("通用状态-暂停")
    String PAUSE = "20";

    @Comment("通用状态-停止")
    String STOP = "30";

    @Comment("通用状态-删除")
    String DELETED = "40";
}
