package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.CommStatus;
import com.lanxi.equity.config.EquityStatus;
import com.lanxi.equity.entity.Equity;
import com.lanxi.equity.entity.EquityRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/28 14:01
 */
@Service
public class EquityRecordServiceImpl implements EquityRecordService {

    @Autowired
    private DaoService dao;

    @Override public Integer insert(EquityRecord... equityRecords) {
        return dao.insert(equityRecords);
    }

    @Override public List<EquityRecord> query(EntityWrapper<EquityRecord> wrapper, Page<EquityRecord> page) {
        return dao.query(EquityRecord.class, wrapper, page);
    }

    @Override public List<EquityRecord> query(EntityWrapper<EquityRecord> wrapper) {
        return dao.query(EquityRecord.class, wrapper, null);
    }

    @Override public Integer delete(EntityWrapper<EquityRecord> wrapper) {
        return dao.delete(EquityRecord.class,wrapper);
    }

    @Override public Integer update(EntityWrapper<EquityRecord> wrapper, EquityRecord newState) {
        return dao.update(wrapper, newState);
    }
}
