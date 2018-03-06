package com.lanxi.equity.business.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lanxi.equity.assist.TimeAssist;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.CodeInstanceStatus;
import com.lanxi.equity.config.EquityStatus;
import com.lanxi.equity.entity.Equity;
import com.lanxi.equity.entity.ExCodeInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/28 13:47
 */
@Service
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private DaoService dao;

    @Override public synchronized void codeInstanceOverTime() {
        EntityWrapper<ExCodeInstance> wrapper=new EntityWrapper<>();
        wrapper.le("over_date", TimeAssist.today());
        wrapper.le("over_time",TimeAssist.now());
        wrapper.in("instance_status", new String[]{CodeInstanceStatus.NORMAL,CodeInstanceStatus.BINDING,CodeInstanceStatus.WAIT_OUPUT});
        List<ExCodeInstance>list=dao.getExCodeInstanceDao().selectList(wrapper);
        list.stream().parallel().forEach(e->{
            e.setInstanceStatus(CodeInstanceStatus.OVERTIME);
            e.updateById();
        });
    }

    @Override public void equityOverTime() {
        EntityWrapper<Equity> wrapper=new EntityWrapper<>();
        wrapper.le("",TimeAssist.today());
        wrapper.le("",TimeAssist.now());
        wrapper.in("",new String[]{EquityStatus.USING,EquityStatus.NORMAL});
        List<Equity> list=dao.getEquityDao().selectList(wrapper);
        list.parallelStream().forEach(e->{
            e.setEquityStatus(EquityStatus.OVERTIME);
            e.updateById();
        });
    }
}
