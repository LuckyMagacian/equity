package com.lanxi.equity.business.daily;

import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.assist.RetMessage;
import com.lanxi.equity.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/28 10:46
 */
public interface UserService {
    Equity equityQueryTotal(Equity oda, UserAccount account);
    ArrayList<Equity> equityList(Equity oda, UserAccount account, Page<Equity> page);

    ArrayList<Equity> beExpireList(Equity oda,UserAccount account,Integer dayRange,Page<Equity> page);
    Equity beExpire(Equity oda,UserAccount account,Integer dayRange);

    ArrayList<EquityRecord> equityRecordList(EquityRecord oda,UserAccount account,Page<EquityRecord> page);
    ArrayList<EquityExchangeRecord> equityExchangeRecordList(EquityExchangeRecord oda,UserAccount account,Page<EquityExchangeRecord> page);

    ArrayList<EquityOrder> equityOrderList(EquityOrder oda,UserAccount account,Page<EquityOrder> page);

    EquityOrder orderInfo(String orderId);

    Commodity commodityInfo(String commId);

//    ArrayList<Commodity> commodityList();

    RetMessage<EquityOrder> equityExchange(EquityOrder oda, UserAccount account);

}
