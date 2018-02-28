package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.assist.ConvertAssist;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.CodeInstanceStatus;
import com.lanxi.equity.config.CodeStatus;
import com.lanxi.equity.entity.ExCodeInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/11 15:26
 */
@Service
public class ExcodeInstanceServiceImpl implements ExcodeInstanceService {

    @Autowired
    private DaoService dao;

    @Override public Integer insert(ExCodeInstance... exCodeInstances) {
        return dao.insert(exCodeInstances);
    }

    @Override public List<ExCodeInstance> query(EntityWrapper<ExCodeInstance> wrapper, Page<ExCodeInstance> page) {
        wrapper.ne("instance_status", CodeInstanceStatus.DELETED);
        return dao.query(ExCodeInstance.class, wrapper, page);
    }

    @Override public List<ExCodeInstance> query(EntityWrapper<ExCodeInstance> wrapper) {
        wrapper.ne("instance_status", CodeInstanceStatus.DELETED);
        return query(wrapper, null);
    }

    @Override public Integer delete(EntityWrapper<ExCodeInstance> wrapper) {
        ExCodeInstance temp = new ExCodeInstance();
        temp.setInstanceStatus(CodeInstanceStatus.DELETED);
        return update(wrapper, temp);
    }

    @Override public Integer update(EntityWrapper<ExCodeInstance> wrapper, ExCodeInstance newState) {
        wrapper.ne("instance_status", CodeInstanceStatus.DELETED);
        return dao.update(wrapper, newState);
    }

    //
    //
    //    @Override public ExCodeInstance queryUserExCodeInstance(ExCodeInstance exCodeInstance) {
    //        return dao.getExCodeInstanceDao().selectOne(exCodeInstance);
    //    }
    //
    //    @Override public List<ExCodeInstance> queryUserExCodeInstances(ExCodeInstance exCodeInstance, Page<ExCodeInstance> page) {
    //        return queryUserExCodeInstances(ConvertAssist.objToWrapper(exCodeInstance), page);
    //    }
    //
    //    @Override public List<ExCodeInstance> queryUserExCodeInstances(EntityWrapper<ExCodeInstance> wrapper, Page<ExCodeInstance> page) {
    //        return page == null ? dao.getExCodeInstanceDao().selectList(wrapper)
    //                            : dao.getExCodeInstanceDao().selectPage(page, wrapper);
    //    }
    //
    //    @Override public List<ExCodeInstance> queryUserExCodeInstances(ExCodeInstance exCodeInstance, List<String> codes) {
    //        EntityWrapper wrapper=ConvertAssist.objToWrapper(exCodeInstance);
    //        Optional.ofNullable(codes).ifPresent(codeList->wrapper.in("code",codes));
    //        wrapper.ne("instance_status", CodeStatus.DELETED);
    //        wrapper.orderBy("add_date",false);
    //        wrapper.orderBy("add_time",false);
    //        return dao.getExCodeInstanceDao().selectList(wrapper);
    //    }
    //
    //    @Override public Integer updateExcodeInstances(EntityWrapper<ExCodeInstance> wrapper, List<String> codes, ExCodeInstance newExCode) {
    //        wrapper.in("code",codes);
    //        return dao.getExCodeInstanceDao().update(newExCode,wrapper);
    //    }
    //
    //    @Override public Integer updateExcodeInstances(ExCodeInstance exCodeInstance, List<String> codes, ExCodeInstance newExCode) {
    //        EntityWrapper<ExCodeInstance> wrapper= ConvertAssist.objToWrapper(exCodeInstance);
    //        return updateExcodeInstances(wrapper,codes,newExCode);
    //    }

}
