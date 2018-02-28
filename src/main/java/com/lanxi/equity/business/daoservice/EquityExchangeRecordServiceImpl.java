package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.entity.EquityExchangeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/28 14:17
 */
@Service
public class EquityExchangeRecordServiceImpl implements EquityExchangeRecordService {
    @Autowired
    private DaoService dao;

    @Override public Integer insert(EquityExchangeRecord... equityExchangeRecords) {
        return dao.insert(equityExchangeRecords);
    }

    @Override public List<EquityExchangeRecord> query(EntityWrapper<EquityExchangeRecord> wrapper, Page<EquityExchangeRecord> page) {
        return dao.query(EquityExchangeRecord.class,wrapper,page);
    }

    @Override public List<EquityExchangeRecord> query(EntityWrapper<EquityExchangeRecord> wrapper) {
        return query(wrapper,null);
    }

    @Override public Integer delete(EntityWrapper<EquityExchangeRecord> wrapper) {
        return dao.delete(EquityExchangeRecord.class,wrapper);
    }

    @Override public Integer update(EntityWrapper<EquityExchangeRecord> wrapper, EquityExchangeRecord newState) {
        return dao.update(wrapper,newState);
    }
}
