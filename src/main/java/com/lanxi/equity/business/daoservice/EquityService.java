package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.entity.Equity;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/11 11:26
 */
public interface EquityService {

    Integer insert(Equity... equities);

    List<Equity> query(EntityWrapper<Equity> wrapper, Page<Equity> page);

    List<Equity> query(EntityWrapper<Equity> wrapper);

    Integer delete(EntityWrapper<Equity> wrapper);

    Integer update(EntityWrapper<Equity> wrapper, Equity newState);
}
