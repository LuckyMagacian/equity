package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.entity.ExCode;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/12 17:47
 */
public interface ExCodeService {
    Integer insert(ExCode... exCodeInstances);

    List<ExCode> query(EntityWrapper<ExCode> wrapper, Page<ExCode> page);

    List<ExCode> query(EntityWrapper<ExCode> wrapper);

    Integer delete(EntityWrapper<ExCode> wrapper);

    Integer update(EntityWrapper<ExCode> wrapper, ExCode newState);
}
