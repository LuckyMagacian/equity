package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.EquityStatus;
import com.lanxi.equity.entity.Equity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/11 15:26
 */
@Service
public class EquityServiceImpl implements EquityService {

    @Autowired
    private DaoService dao;

    @Override public Integer insert(Equity... equities) {
        return dao.insert(equities);
    }

    @Override public List<Equity> query(EntityWrapper<Equity> wrapper, Page<Equity> page) {
        wrapper.ne("equity_status",EquityStatus.DELETED);
        wrapper.orderBy("over_date",false);
        wrapper.orderBy("over_time",false);
        return dao.query(Equity.class, wrapper, page);
    }

    @Override public List<Equity> query(EntityWrapper<Equity> wrapper) {
        return query(wrapper, null);
    }

    @Override public Integer delete(EntityWrapper<Equity> wrapper) {
        Equity equity = new Equity();
        equity.setEquityStatus(EquityStatus.DELETED);
        return update(wrapper, equity);
    }

    @Override public Integer update(EntityWrapper<Equity> wrapper, Equity newState) {
        wrapper.ne("code_status", EquityStatus.DELETED);
        return dao.update(wrapper, newState);
    }
}
