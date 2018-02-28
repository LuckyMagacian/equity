package com.lanxi.equity.business.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.TimeAssist;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.business.daoservice.EquityExchangeRecordService;
import com.lanxi.equity.business.daoservice.EquityOrderService;
import com.lanxi.equity.business.daoservice.EquityRecordService;
import com.lanxi.equity.business.daoservice.EquityService;
import com.lanxi.equity.config.ConstConfig;
import com.lanxi.equity.config.EquityStatus;
import com.lanxi.equity.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Comment("转换查询条件为wrapper")
    private EntityWrapper<Equity> convertEquityWrapper(OrgaDeptAct oda, UserAccount account){
        EntityWrapper<Equity> wrapper= new EntityWrapper<>();
        Optional.ofNullable(oda.getOrgaId()).ifPresent(e->wrapper.eq("orga_id",e));
        Optional.ofNullable(oda.getDeptId()).ifPresent(e->wrapper.eq("dept_id",e));
        Optional.ofNullable(oda.getActId()).ifPresent(e->wrapper.eq("act_id",e));
        Optional.ofNullable(account.getUserId()).ifPresent(e->wrapper.eq("user_id",e));
        return wrapper;
    }

    @Comment("合并equity列表")
    private Equity equitiesMerge(OrgaDeptAct oda, UserAccount account,List<Equity> list){
        Equity equity=new Equity();
        equity.fromOda(oda);
        equity.setUserId(account.getUserId());
        equity.setUserName(account.getUserName());
        equity.setUseful(0);
        equity.setTotal(0);
        list.stream().map(e->e.getUseful()).forEach(e->{
            equity.setUseful(equity.getUseful()+e);
        });
        return equity;
    }

    @Override public Equity equityQueryTotal(Equity oda, UserAccount account) {
        List<Equity> list = equityList(oda,account,null);
        Equity equity=equitiesMerge(oda,account,list);
        return equity;
    }

    @Override public List<Equity> equityList(Equity oda, UserAccount account, Page<Equity> page) {
        EntityWrapper<Equity> wrapper= convertEquityWrapper(oda,account);
        wrapper.in("equity_status", Arrays.asList(new String[]{EquityStatus.NORMAL,EquityStatus.USING}));
        List<Equity> list = equityService.query(wrapper,page);
        return list;
    }

    @Override public List<Equity> beExpireList(Equity oda, UserAccount account, Integer dayRange, Page<Equity> page) {
        EntityWrapper<Equity> wrapper= convertEquityWrapper(oda,account);
        dayRange=Optional.ofNullable(dayRange).orElse(ConstConfig.EQUITY_BE_EXPIRE_DAYS);
        wrapper.le("over_date", TimeAssist.nDayLater(dayRange));
        List<Equity> list = equityService.query(wrapper,page);
        return list;
    }

    @Override public Equity beExpire(Equity oda, UserAccount account, Integer dayRange) {
        List<Equity> list=beExpireList(oda,account,dayRange,null);
        Equity equity=equitiesMerge(oda,account,list);
        return equity;
    }

    @Override public List<EquityRecord> equityRecordList(EquityRecord oda, UserAccount account, Page<EquityRecord> page) {
        EntityWrapper<EquityRecord> wrapper= new EntityWrapper<>();
        Optional.ofNullable(oda.getOrgaId()).ifPresent(e->wrapper.eq("orga_id",e));
        Optional.ofNullable(oda.getDeptId()).ifPresent(e->wrapper.eq("dept_id",e));
        Optional.ofNullable(oda.getActId()).ifPresent(e->wrapper.eq("act_id",e));
        Optional.ofNullable(account.getUserId()).ifPresent(e->wrapper.eq("user_id",e));
        List<EquityRecord> list=equityRecordService.query(wrapper,page);
        return list;
    }

    @Override public List<EquityExchangeRecord> equityExchangeRecordList(EquityExchangeRecord oda, UserAccount account, Page<EquityExchangeRecord> page) {
        EntityWrapper<EquityExchangeRecord> wrapper= new EntityWrapper<>();
        Optional.ofNullable(oda.getOrgaId()).ifPresent(e->wrapper.eq("orga_id",e));
        Optional.ofNullable(oda.getDeptId()).ifPresent(e->wrapper.eq("dept_id",e));
        Optional.ofNullable(oda.getActId()).ifPresent(e->wrapper.eq("act_id",e));
        Optional.ofNullable(account.getUserId()).ifPresent(e->wrapper.eq("user_id",e));
        List<EquityExchangeRecord> list=equityExchangeRecordService.query(wrapper,page);
        return list;
    }

    @Override public List<EquityOrder> equityOrderList(EquityOrder oda, UserAccount account, Page<EquityOrder> page) {
        EntityWrapper<EquityOrder> wrapper= new EntityWrapper<>();
        Optional.ofNullable(oda.getOrgaId()).ifPresent(e->wrapper.eq("orga_id",e));
        Optional.ofNullable(oda.getDeptId()).ifPresent(e->wrapper.eq("dept_id",e));
        Optional.ofNullable(oda.getActId()).ifPresent(e->wrapper.eq("act_id",e));
        Optional.ofNullable(account.getUserId()).ifPresent(e->wrapper.eq("user_id",e));
        Optional.ofNullable(oda.getOrderStatus()).ifPresent(e->wrapper.eq("order_status",e));
        List<EquityOrder> list=equityOrderService.query(wrapper,page);
        return list;
    }

    @Override public EquityOrder orderInfo(String orderId) {
        orderId=Optional.ofNullable(orderId).orElse("null");
        return dao.getEquityOrderDao().selectById(orderId);
    }
}
