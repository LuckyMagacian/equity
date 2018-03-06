package com.lanxi.equity.business.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.ConvertAssist;
import com.lanxi.equity.assist.RetMessage;
import com.lanxi.equity.assist.TimeAssist;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.business.daoservice.EquityExchangeRecordService;
import com.lanxi.equity.business.daoservice.EquityOrderService;
import com.lanxi.equity.business.daoservice.EquityRecordService;
import com.lanxi.equity.business.daoservice.EquityService;
import com.lanxi.equity.config.*;
import com.lanxi.equity.entity.*;
import com.lanxi.equity.report.order.*;
import com.lanxi.util.entity.LogFactory;
import com.lanxi.util.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangyuanjian created in 2018/2/28 10:46
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private DaoService dao;

    @Autowired
    private EquityService equityService;

    @Autowired
    private EquityRecordService equityRecordService;

    @Autowired
    private EquityExchangeRecordService equityExchangeRecordService;

    @Autowired
    private EquityOrderService equityOrderService;

    @Comment("辅助处理整型数据")
    private static volatile Integer tempInt;

    @Comment("辅助处理布尔型数据")
    private static volatile Boolean tempBool;

    @Comment("辅助转换long型数据")
    private static volatile Long    tempLong;

    @Comment("转换查询条件为wrapper")
    private EntityWrapper<Equity> convertEquityWrapper(OrgaDeptAct oda, UserAccount account) {
        EntityWrapper<Equity> wrapper = new EntityWrapper<>();
        Optional.ofNullable(oda.getOrgaId()).ifPresent(e -> wrapper.eq("orga_id", e));
        Optional.ofNullable(oda.getDeptId()).ifPresent(e -> wrapper.eq("dept_id", e));
        Optional.ofNullable(oda.getActId()).ifPresent(e -> wrapper.eq("act_id", e));
        Optional.ofNullable(account.getUserId()).ifPresent(e -> wrapper.eq("user_id", e));
        return wrapper;
    }

    @Comment("合并equity列表")
    private Equity equitiesMerge(OrgaDeptAct oda, UserAccount account, List<Equity> list) {
        Equity equity = new Equity();
        equity.fromOda(oda);
        equity.setUserId(account.getUserId());
        equity.setUserName(account.getUserName());
        equity.setUseful(0);
        equity.setTotal(0);
        list.stream().map(e -> e.getUseful()).forEach(e -> {
            equity.setUseful(equity.getUseful() + e);
        });
        return equity;
    }

    @Override public Equity equityQueryTotal(Equity oda, UserAccount account) {
        ArrayList<Equity> list   = equityList(oda, account, null);
        Equity            equity = equitiesMerge(oda, account, list);
        return equity;
    }

    @Override public ArrayList<Equity> equityList(Equity oda, UserAccount account, Page<Equity> page) {
        EntityWrapper<Equity> wrapper = convertEquityWrapper(oda, account);
        wrapper.in("equity_status", Arrays.asList(new String[] {EquityStatus.NORMAL, EquityStatus.USING}));
        ArrayList<Equity> list = (ArrayList<Equity>) equityService.query(wrapper, page);
        return list;
    }

    @Override public ArrayList<Equity> beExpireList(Equity oda, UserAccount account, Integer dayRange, Page<Equity> page) {
        EntityWrapper<Equity> wrapper = convertEquityWrapper(oda, account);
        dayRange = Optional.ofNullable(dayRange).orElse(ConstConfig.EQUITY_BE_EXPIRE_DAYS);
        wrapper.le("over_date", TimeAssist.nDayLater(dayRange));
        ArrayList<Equity> list = (ArrayList<Equity>) equityService.query(wrapper, page);
        return list;
    }

    @Override public Equity beExpire(Equity oda, UserAccount account, Integer dayRange) {
        ArrayList<Equity> list   = beExpireList(oda, account, dayRange, null);
        Equity            equity = equitiesMerge(oda, account, list);
        return equity;
    }

    @Override public ArrayList<EquityRecord> equityRecordList(EquityRecord oda, UserAccount account, Page<EquityRecord> page) {
        EntityWrapper<EquityRecord> wrapper = new EntityWrapper<>();
        Optional.ofNullable(oda.getOrgaId()).ifPresent(e -> wrapper.eq("orga_id", e));
        Optional.ofNullable(oda.getDeptId()).ifPresent(e -> wrapper.eq("dept_id", e));
        Optional.ofNullable(oda.getActId()).ifPresent(e -> wrapper.eq("act_id", e));
        Optional.ofNullable(account.getUserId()).ifPresent(e -> wrapper.eq("user_id", e));
        ArrayList<EquityRecord> list = (ArrayList<EquityRecord>) equityRecordService.query(wrapper, page);
        return list;
    }

    @Override public ArrayList<EquityExchangeRecord> equityExchangeRecordList(EquityExchangeRecord oda, UserAccount account, Page<EquityExchangeRecord> page) {
        EntityWrapper<EquityExchangeRecord> wrapper = new EntityWrapper<>();
        Optional.ofNullable(oda.getOrgaId()).ifPresent(e -> wrapper.eq("orga_id", e));
        Optional.ofNullable(oda.getDeptId()).ifPresent(e -> wrapper.eq("dept_id", e));
        Optional.ofNullable(oda.getActId()).ifPresent(e -> wrapper.eq("act_id", e));
        Optional.ofNullable(account.getUserId()).ifPresent(e -> wrapper.eq("user_id", e));
        Optional.ofNullable(oda.getExStatus()).ifPresent(e->wrapper.eq("ex_status",e));
        ArrayList<EquityExchangeRecord> list = (ArrayList<EquityExchangeRecord>) equityExchangeRecordService.query(wrapper, page);
        return list;
    }

    @Override public ArrayList<EquityOrder> equityOrderList(EquityOrder oda, UserAccount account, Page<EquityOrder> page) {
        EntityWrapper<EquityOrder> wrapper = new EntityWrapper<>();
        Optional.ofNullable(oda.getOrgaId()).ifPresent(e -> wrapper.eq("orga_id", e));
        Optional.ofNullable(oda.getDeptId()).ifPresent(e -> wrapper.eq("dept_id", e));
        Optional.ofNullable(oda.getActId()).ifPresent(e -> wrapper.eq("act_id", e));
        Optional.ofNullable(account.getUserId()).ifPresent(e -> wrapper.eq("user_id", e));
        Optional.ofNullable(oda.getOrderStatus()).ifPresent(e -> wrapper.eq("order_status", e));
        ArrayList<EquityOrder> list = (ArrayList<EquityOrder>) equityOrderService.query(wrapper, page);
        return list;
    }

    @Override public EquityOrder orderInfo(String orderId) {
        orderId = Optional.ofNullable(orderId).orElse("null");
        return dao.getEquityOrderDao().selectById(orderId);
    }

    @Override public Commodity commodityInfo(String commId) {
        commId = Optional.ofNullable(commId).orElse("null");
        return dao.getCommodityDao().selectById(commId);
    }

    @Transactional
    @Override public RetMessage<EquityOrder> equityExchange(EquityOrder oda, UserAccount account) {
        RetMessage<EquityOrder> result = new RetMessage<>();
        if(ConstConfig.DEVELOP){
            LogFactory.debug(this,"替换商品编号为测试商品:970483126258700288");
            oda.setCommId("970483126258700288");
        }
        String commId = Optional.ofNullable(oda.getCommId()).orElse("null");
        Commodity commodity =null;
        if(commId.length()==4) {
            EntityWrapper<Commodity> wrapper=new EntityWrapper<>();
            wrapper.eq("ele_commId",commId);
            List<Commodity> list=dao.getCommodityDao().selectList(wrapper);
            if(list.size()==1){
                commodity=list.get(0);
            }
            if(list.size()>1){
                LogFactory.info(this,"短位商品编号不唯一");
                result.setRetCode(RetCode.SHORT_COMM_ID_NOT_UNIQUE);
                result.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SHORT_COMM_ID_NOT_UNIQUE"));
                return result;
            }
        }else{
            commodity = dao.getCommodityDao().selectById(commId);
        }



        if(!ConstConfig.DEVELOP)
        if (commodity == null) {
            LogFactory.info(this,"兑换商品不存在");
            result.setRetCode(RetCode.COMMODITY_NOT_FOUND);
            result.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "COMMODITY_NOT_FOUND"));
            return result;
        }
        //查询权益记录
        LogFactory.info(this,"查询权益记录");
        Equity equity = new Equity();
        equity.fromOda(oda);
        equity.setUserId(account.getUserId());
        int subValue = commodity.getValue()*Integer.valueOf(oda.getCommCount());
        List<Equity> equities = equityList(equity, account, null);
        Equity       sum      = equityQueryTotal(equity, account);
        if (sum.getUseful() < subValue) {
            LogFactory.info(this,"权益值不足无法兑换");
            result.setRetCode(RetCode.EQUITY_NOT_ENOUGH);
            result.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "EQUITY_NOT_ENOUGH"));
            return result;
        }

        try {
            equities.sort(Comparator.comparing((Equity e) -> e.getOverDate()).thenComparing((Equity e) -> e.getOverTime()));
            tempInt = 0;
            tempBool=true;
            synchronized (tempInt) {
                equities = equities.stream().peek(e -> {
                    if (e.getUseful() < subValue&&tempBool) {
                        e.setUseful(0);
                        e.setEquityStatus(EquityStatus.USAED);
                        tempInt += e.getUseful();
                    } else {
                        if(tempBool){
                            e.setUseful(e.getUseful() - (subValue - tempInt));
                            tempBool=false;
                        }
                        if(e.getUseful()==0){
                            e.setEquityStatus(EquityStatus.USAED);
                        }else{
                            e.setEquityStatus(EquityStatus.USING);
                        }
                    }
                }).collect(Collectors.toList());
            }
            equities.forEach(Equity::updateById);
        } catch (Throwable e) {
            LogFactory.info(this,"扣减权益值时发生异常",e);
            throw new RuntimeException(RetCode.EQUITY_SUB_FAIL, e);
            //            result.setRetCode(RetCode.EQUITY_SUB_FAIL);
            //            result.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "EQUITY_SUB_FAIL"));
            //            return result;
        }

        //插入兑换记录
        LogFactory.info(this,"插入兑换记录");
        EquityExchangeRecord record = null;
        try {
            record = new EquityExchangeRecord();
            record.fromOda(oda);
            record.setRecordId(IdWorker.getId() + "");
            record.setUserId(account.getUserId());
            record.setCommId(commId);
            record.setPhone(oda.getPhone());
            record.setCommNum(oda.getCommCount());
            record.setCommName(commodity.getCommName());
            record.setAddDate(TimeAssist.today());
            record.setAddTime(TimeAssist.now());

            record.insert();
        } catch (Exception e) {
            throw new RuntimeException(RetCode.INSERT_EQUITY_EX_RECORD_FAIL, e);
        }

        EquityOrder order = null;
        try {
            //插入兑换订单
            LogFactory.info(this,"插入兑换订单");
            order = new EquityOrder();
            order.setOrderId(IdWorker.getId() + "");
            order.fromOda(oda);
            order.setUserId(account.getUserId());
            order.setCommId(commId);
            order.setEleCommId(commodity.getEleCommId());
            order.setCommName(commodity.getCommName());
            order.setCommCount(oda.getCommCount());
            order.setExRecordId(record.getRecordId());
            order.setOrderStatus(OrderStatus.WAIT_DEAL);

            if (ConstConfig.DEVELOP) {
                order.setBackup1("测试订单");
            }
            order.insert();
        } catch (Exception e) {
            throw new RuntimeException(RetCode.INSERT_EQUITY_ORDER_FAIL, e);
        }
        BaoWen baoWen = null;

        try {
            //生成请求报文
            baoWen = new BaoWen();
            ReqHead head = new ReqHead();
            ReqMsg  body = new ReqMsg();
            LogFactory.info(this,"生成请求电子礼品平台报文:"+baoWen.toDocument().asXML());

            head.setVer(ConstConfig.VER);
            head.setAdd(ConstConfig.ADD);
            head.setApp(ConstConfig.APP);
            head.setMsgNo(ConstConfig.MSG_NO_BUY);
            head.setChkDate(TimeAssist.today());
            head.setWorkDate(TimeAssist.today());
            head.setSrc(ConstConfig.COUPON_REPORT_SRC);
            head.setDes(ConstConfig.COUPON_REPORT_DES);

            synchronized (ConstConfig.MSG_NO_SUPPLIER) {
                head.setMsgId(ConstConfig.MSG_NO_SUPPLIER.get() + "");
            }

            body.setCount(oda.getCommCount());
            body.setNeedSend(ConstConfig.NEED_SEND);
            body.setPhone(oda.getPhone());
            body.setSkuCode(commodity.getEleCommId());

//            if (ConstConfig.DEVELOP) {
//                if (CommodityType.ELE_COUPON.equals(commodity.getCommType()))
//                    body.setSkuCode(ConstConfig.TEST_COUPON);
//                else
//                    body.setSkuCode(ConstConfig.TEST_CHARGE);
//            }

            body.setType(commodity.getCommType());
            body.setRemark(ConstConfig.DEVELOP ? "权益项目兑换测试" : "权益项目兑换");

            baoWen.setHead(head);
            baoWen.setMsg(body);
            baoWen.sign();
        } catch (Exception e) {
            LogFactory.info(this,ConvertAssist.extractCommentInfo(RetCode.class,"MAKE_REPORT_FAIL"),e);
            throw new RuntimeException(RetCode.MAKE_REPORT_FAIL, e);
        }
        ResHead resHead = null;
        ResMsg  resBody = null;

        try {
            LogFactory.debug(this,"请求报文:"+baoWen.toDocument().asXML());
            //获取响应结果
            String xml = HttpUtil.post(baoWen.toDocument().asXML(), ConstConfig.COUPON_URL, "gbk", MediaType.TEXT_XML_VALUE);
            LogFactory.info(this,"电子礼品平台响应原文:"+xml);
            LogFactory.debug(this,"响应报文:"+xml);
            BaoWen res = BaoWen.formDocumentStr(xml);
            resHead = (ResHead) res.getHead();
            resBody = (ResMsg) res.getMsg();
        } catch (Exception e) {
            LogFactory.info(this,ConvertAssist.extractCommentInfo(RetCode.class,"GET_REPORT_RESPONSE_FAIL"),e);
            throw new RuntimeException(RetCode.GET_REPORT_RESPONSE_FAIL, e);
        }


        //兑换记录的备用字段被设定为返回码与返回信息
        record.setBackup1(resHead.getResCode());
        record.setBackup2(resHead.getResMsg());

        order.setRetCode(resHead.getResCode());
        order.setRetMsg(resHead.getResMsg());

        if (resHead.getResCode().equals(RetCode.SUCCESS)) {
            LogFactory.info(this,"处理成功,更新信息");
            try {
                record.setExStatus(ExStatus.SUCCESS);
                record.setCodesProto(resBody.getSkuList().stream().map(e -> e.getCode()).collect(Collectors.toList()));
                if (ConstConfig.DEVELOP) {
                    record.setExStatus(ExStatus.TEST);
                }
//                dao.getEquityExchangeRecordDao().update(record,ConvertAssist.objToWrapper(record));
                record.updateById();
            } catch (Exception e) {
                LogFactory.info(this,ConvertAssist.extractCommentInfo(RetCode.class,"UPDATE_EQUITY_EX_RECORD_FAIL"),e);
                throw new RuntimeException(RetCode.UPDATE_EQUITY_EX_RECORD_FAIL, e);
            }
            try {
                order.setOrderStatus(OrderStatus.SUCCESS);
                order.setCodes(record.getCodes());
                order.updateById();
            } catch (Exception e) {
                LogFactory.info(this,ConvertAssist.extractCommentInfo(RetCode.class,"UPDATE_EQUITY_ORDER_FAIL"),e);
                throw new RuntimeException(RetCode.UPDATE_EQUITY_ORDER_FAIL, e);
            }

            //插入权益记录
            try {
                EquityRecord equityRecord = new EquityRecord();
                equityRecord.fromOda(equity);
                equityRecord.setRecordId(IdWorker.getId() + "");
                equityRecord.setUserId(equity.getUserId());
                equityRecord.setRecordType(EquityReType.SUB);
                equityRecord.setDetailType(EquityReDetailType.EX_SUB);
                equityRecord.setRecordDesc("兑换商品扣减权益值");
                equityRecord.setVeriationValue("-" + commodity.getValue());
                equityRecord.setAddDate(TimeAssist.today());
                equityRecord.setAddTime(TimeAssist.now());
                equityRecord.insert();
            } catch (Exception e) {
                LogFactory.info(this,ConvertAssist.extractCommentInfo(RetCode.class,"INSERT_EQUITY_RECORD_FAIL"),e);
                throw new RuntimeException(RetCode.INSERT_EQUITY_RECORD_FAIL, e);
            }

            result.setRetCode(RetCode.SUCCESS);
            result.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
            result.setDetail(order);
        } else {
            try {
                record.setExStatus(ExStatus.FAIL);
                record.updateById();
            } catch (Exception e) {
                LogFactory.info(this,ConvertAssist.extractCommentInfo(RetCode.class,"UPDATE_EQUITY_EX_RECORD_FAIL"),e);
                throw new RuntimeException(RetCode.UPDATE_EQUITY_EX_RECORD_FAIL, e);
            }
            try {
                order.setOrderStatus(OrderStatus.FAIL);
                order.updateById();
            } catch (Exception e) {
                LogFactory.info(this,ConvertAssist.extractCommentInfo(RetCode.class,"UPDATE_EQUITY_ORDER_FAIL"),e);
                throw new RuntimeException(RetCode.UPDATE_EQUITY_ORDER_FAIL, e);
            }
            LogFactory.info(this,ConvertAssist.extractCommentInfo(RetCode.class,"UPDATE_EQUITY_ORDER_FAIL"));
            result.setRetCode(RetCode.EQUITY_EXCHANGE_FAIL);
            result.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class, "EQUITY_EXCHANGE_FAIL"));
            result.setDetail(order);
        }
        return result;
    }
}
