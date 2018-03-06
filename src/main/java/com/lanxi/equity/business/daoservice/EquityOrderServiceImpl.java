package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.OrderStatus;
import com.lanxi.equity.entity.EquityOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/28 14:24
 */
@Service
public class EquityOrderServiceImpl implements EquityOrderService {
    @Autowired
    private DaoService dao;

    @Override public Integer insert(EquityOrder... equityOrders) {
        return dao.insert(equityOrders);
    }

    @Override public List<EquityOrder> query(EntityWrapper<EquityOrder> wrapper, Page<EquityOrder> page) {
        wrapper.ne("order_status", OrderStatus.DELETED);
        wrapper.orderBy("add_date",false );
        wrapper.orderBy("add_time",false );
        return dao.query(EquityOrder.class, wrapper, page);
    }

    @Override public List<EquityOrder> query(EntityWrapper<EquityOrder> wrapper) {
        return query(wrapper, null);
    }

    @Override public Integer delete(EntityWrapper<EquityOrder> wrapper) {
        EquityOrder equity = new EquityOrder();
        equity.setOrderStatus(OrderStatus.DELETED);
        return update(wrapper, equity);
    }

    @Override public Integer update(EntityWrapper<EquityOrder> wrapper, EquityOrder newState) {
        wrapper.ne("order_status", OrderStatus.DELETED);
        return dao.update(wrapper, newState);
    }
}
