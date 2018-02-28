package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.CodeStatus;
import com.lanxi.equity.entity.ExCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/12 17:47
 */
@Service
public class ExCodeServiceImpl implements ExCodeService {
    @Resource
    private DaoService dao;
    @Override public Integer insert(ExCode... exCodes) {
        return dao.insert(exCodes);
    }

    @Override public List<ExCode> query(EntityWrapper<ExCode> wrapper, Page<ExCode> page) {
        wrapper.ne("code_status",CodeStatus.DELETED);
        return dao.query(ExCode.class,wrapper,page);
    }

    @Override public List<ExCode> query(EntityWrapper<ExCode> wrapper) {
        wrapper.ne("code_status",CodeStatus.DELETED);
        return query(wrapper,null);
    }

    @Override public Integer delete(EntityWrapper<ExCode> wrapper) {
        ExCode exCode=new ExCode();
        exCode.setCodeStatus(CodeStatus.DELETED);
        return update(wrapper,exCode);
    }

    @Override public Integer update(EntityWrapper<ExCode> wrapper, ExCode newState) {
        wrapper.ne("code_status",CodeStatus.DELETED);
        return dao.update(wrapper,newState);
    }
}
