package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.entity.EquityRecord;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/28 14:00
 */
public interface EquityRecordService {

    Integer insert(EquityRecord... equityRecords);

    List<EquityRecord> query(EntityWrapper<EquityRecord> wrapper, Page<EquityRecord> page);

    List<EquityRecord> query(EntityWrapper<EquityRecord> wrapper);

    Integer delete(EntityWrapper<EquityRecord> wrapper);

    Integer update(EntityWrapper<EquityRecord> wrapper, EquityRecord newState);
}
