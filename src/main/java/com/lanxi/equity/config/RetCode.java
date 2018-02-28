package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;

/**
 * 返回码
 * @author yangyuanjian created in 2018/2/11 9:11
 */
@Comment("响应码")
public interface RetCode {
    //通用

    @Comment("成功")
    String SUCCESS="0000";

    @Comment("部分成功")
    String PARTIAL_SUCCESS="0909";

    @Comment("全部失败")
    String FAIL="0009";

    @Comment("参数非法")
    String ARG_ILLEGAL="4001";

//    @Comment("系统错误")
    @Comment("服务器繁忙")
    String SYSTEM_ERROR="9999";
    //API
    @Comment("api请求处理前发生异常")
    String API_EXCEPTION_BEFORE_DEAL="3001";

    @Comment("api报文参数转换时发生异常")
    String API_REPORT_CONVERT_EXCEPTION="3002";

    @Comment("api调用时发生异常")
    String API_INVOKE_EXCEPTION="3003";

    @Comment("签名校验不通过")
    String API_SIGN_ERROR="3004";

    @Comment("校验报文参数时发生异常")
    String API_CHECK_PARAM_EXCEPTION="3005";

    @Comment("签名为空或null")
    String API_SIGN_IS_EMPTY="3006";

    @Comment("消息编号为空或null")
    String API_MSGID_IS_EMPTY="3007";

    @Comment("消息已处理")
    String API_MSGID_HAS_DEALED="3008";

    @Comment("机构编号为空或null")
    String API_ORG_ID_IS_EMPTY="3009";

    @Comment("请求日期为空或null")
    String API_SEND_DATE_IS_EMPTY="3010";

    @Comment("请求时间为空或null")
    String API_SEND_TIME_IS_EMPTY="3011";

    @Comment("功能编号为空或null")
    String API_FUN_ID_IS_EMPTY="3011";

    @Comment("功能编号不存在")
    String API_FUN_ID_NOT_EXISTED="3012";

    @Comment("活动不存在")
    String API_ACT_NOT_FOUND="3013";

    @Comment("活动存在多个")
    String API_ACT_MORE_THEN_ONE="3014";

    @Comment("消息已成功处理")
    String API_MSG_HAS_DEALED_SUCCESS="3015";

    //兑换码实例兑换

    @Comment("兑换码实例过期")
    String CODE_INSTACNCE_EXPIRED="4101";

    @Comment("兑换码实例不存在")
    String CODE_INSTACNCE_NOT_EXISTED="4102";

    @Comment("兑换码实例已使用")
    String CODE_INSTACNCE_USED="4103";

    @Comment("兑换码实例已注销")
    String CODE_INSTACNCE_CANCELED="4104";

    @Comment("兑换码实例状态未知")
    String CODE_INSTACNCE_UNKNOWN="4105";

    @Comment("兑换时发生异常")
    String CODE_INSTANCE_EX_EXCEPTION="4109";

    //兑换码实例生成

    @Comment("兑换码原型不存在")
    String CODE_NOT_FOUND ="4111";

    @Comment("兑换码原型多于一个")
    String CODE_MORE_THEN_ONE="4112";

    @Comment("生成兑换码实例时发生异常")
    String CODE_INSTACEN_GENERATOR_EXCEPTION="4119";

    //http调用

    @Comment("请求参数校验不通过")
    String ARG_CHECK_NOT_PASS="5001";


}
