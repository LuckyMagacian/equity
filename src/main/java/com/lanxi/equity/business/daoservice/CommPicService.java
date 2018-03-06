package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.entity.CommPicture;
import com.lanxi.equity.entity.Commodity;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/3/1 9:06
 */
public interface CommPicService {
    Integer insert(CommPicture... commPictures);

    List<CommPicture> query(EntityWrapper<CommPicture> wrapper, Page<CommPicture> page);

    List<CommPicture> query(EntityWrapper<CommPicture> wrapper);

    Integer delete(EntityWrapper<CommPicture> wrapper);

    Integer update(EntityWrapper<CommPicture> wrapper, CommPicture newState);
}
