package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.entity.Commodity;
import com.lanxi.equity.entity.Commodity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyuanjian created in 2018/3/1 9:06
 */
public interface CommService {
    Integer insert(Commodity... comms);

    List<Commodity> query(EntityWrapper<Commodity> wrapper, Page<Commodity> page);

    List<Commodity> query(EntityWrapper<Commodity> wrapper);

    Integer delete(EntityWrapper<Commodity> wrapper);

    Integer update(EntityWrapper<Commodity> wrapper, Commodity newState);
}
