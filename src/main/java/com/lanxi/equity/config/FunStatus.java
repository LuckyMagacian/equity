package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 功能接口状态
 * @author yangyuanjian created in 2018/2/8 14:10
 */
@Comment("功能接口状态")
public interface FunStatus {
    @Comment("接口状态-正常")
    String NORMAL="10";
    @Comment("接口状态-暂停服务")
    String PAUSE="21";
    @Comment("接口状态-停止服务")
    String STOP="22";
    @Comment("接口状态-已删除")
    String DELETED="30";
}
