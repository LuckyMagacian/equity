package com.lanxi.equity.config;

import com.google.common.collect.HashBiMap;
import com.lanxi.equity.entity.*;

import java.util.Map;

/**
 * @author yangyuanjian created in 2018/2/11 9:16
 */
@Deprecated
public abstract class CNMap {
    public static final Map<String,Class> CLASS_CN=HashBiMap.create();

    public static final Map<String,Class> ENUM_CN=HashBiMap.create();

    static {
        //                 CLASS_CN.put("机构部门活动通用字段",OrgaDeptAct.class);
        CLASS_CN.put("活动", Activity.class);
        CLASS_CN.put("算法", Algorithm.class);
        CLASS_CN.put("商品图片", CommPicture.class);
        CLASS_CN.put("商品", Commodity.class);
        CLASS_CN.put("部门", Department.class);
        CLASS_CN.put("权益", Equity.class);
        CLASS_CN.put("权益记录", EquityRecord.class);
        CLASS_CN.put("权益兑换记录", EquityExchangeRecord.class);
        CLASS_CN.put("权益订单", EquityOrder.class);
        CLASS_CN.put("兑换码", ExCode.class);
        CLASS_CN.put("兑换码实体", ExCodeInstance.class);
        CLASS_CN.put("功能接口", FunInterface.class);
        CLASS_CN.put("商品分类标签", Label.class);
        CLASS_CN.put("管理员账户", ManageAccount.class);
        CLASS_CN.put("操作记录", OperateRecord.class);
        CLASS_CN.put("机构", Organization.class);
        CLASS_CN.put("用户账户", UserAccount.class);
    }

    static {
        ENUM_CN.put("活动状态", ActStatus.class);
        ENUM_CN.put("商品类型", CommodityType.class);
        ENUM_CN.put("商品状态", CommStatus.class);
        ENUM_CN.put("通用状态", Status.class);
        ENUM_CN.put("兑换码实例状态", CodeInstanceStatus.class);
        ENUM_CN.put("兑换码状态", CodeStatus.class);
        ENUM_CN.put("权益记录详细类型", EquityReDetailType.class);
        ENUM_CN.put("权益记录类型", EquityReType.class);
        ENUM_CN.put("权益值来源", EquitySource.class);
        ENUM_CN.put("权益值状态", EquityStatus.class);
        ENUM_CN.put("兑换记录状态", ExStatus.class);
        ENUM_CN.put("功能接口状态", FunStatus.class);
        ENUM_CN.put("商品分类标签状态", LabelStatus.class);
        ENUM_CN.put("电子礼品平台订单状态", OrderStatus.class);
        ENUM_CN.put("返回码列表",RetCode.class);
    }
}
