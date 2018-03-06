package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.CommStatus;
import com.lanxi.equity.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/3/1 9:06
 */
public class CommServiceImpl implements CommService {
    @Autowired
    private DaoService dao;


    @Override public Integer insert(Commodity... comms) {
        return dao.insert(comms);
    }

    @Override public List<Commodity> query(EntityWrapper<Commodity> wrapper, Page<Commodity> page) {
        wrapper.ne("comm_status", CommStatus.DELETED);
        return dao.query(Commodity.class, wrapper, page);
    }

    @Override public List<Commodity> query(EntityWrapper<Commodity> wrapper) {
        return query(wrapper, null);
    }

    @Override public Integer delete(EntityWrapper<Commodity> wrapper) {
        Commodity comm=new Commodity();
        comm.setCommStatus(CommStatus.DELETED);
        return update(wrapper,comm);
    }

    @Override public Integer update(EntityWrapper<Commodity> wrapper, Commodity newState) {
        wrapper.ne("comm_status", CommStatus.DELETED);
        return dao.update(wrapper, newState);
    }
}
