package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.entity.CommPicture;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/3/1 9:06
 */
public class CommPicServiceImpl implements CommPicService {
    @Autowired
    private DaoService dao;

    @Override public Integer insert(CommPicture... commPictures) {
        return dao.insert(commPictures);
    }

    @Override public List<CommPicture> query(EntityWrapper<CommPicture> wrapper, Page<CommPicture> page) {
        return dao.query(CommPicture.class, wrapper, page);
    }

    @Override public List<CommPicture> query(EntityWrapper<CommPicture> wrapper) {
        return query(wrapper, null);
    }

    @Override public Integer delete(EntityWrapper<CommPicture> wrapper) {
        return dao.delete(CommPicture.class,wrapper);
    }

    @Override public Integer update(EntityWrapper<CommPicture> wrapper, CommPicture newState) {
        return dao.update(wrapper, newState);
    }
}
