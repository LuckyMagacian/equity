package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.entity.EquityOrder;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/28 14:24
 */
public interface EquityOrderService {
    Integer insert(EquityOrder... equityOrders);

    List<EquityOrder> query(EntityWrapper<EquityOrder> wrapper, Page<EquityOrder> page);

    List<EquityOrder> query(EntityWrapper<EquityOrder> wrapper);

    Integer delete(EntityWrapper<EquityOrder> wrapper);

    Integer update(EntityWrapper<EquityOrder> wrapper, EquityOrder newState);
}
