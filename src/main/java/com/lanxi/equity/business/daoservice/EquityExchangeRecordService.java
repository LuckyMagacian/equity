package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.entity.EquityExchangeRecord;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/28 14:17
 */
public interface EquityExchangeRecordService  {
    Integer insert(EquityExchangeRecord... equityExchangeRecords);

    List<EquityExchangeRecord> query(EntityWrapper<EquityExchangeRecord> wrapper, Page<EquityExchangeRecord> page);

    List<EquityExchangeRecord> query(EntityWrapper<EquityExchangeRecord> wrapper);

    Integer delete(EntityWrapper<EquityExchangeRecord> wrapper);

    Integer update(EntityWrapper<EquityExchangeRecord> wrapper, EquityExchangeRecord newState);
}
