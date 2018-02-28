package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.CodeInstanceStatus;
import com.lanxi.equity.entity.Activity;
import com.lanxi.equity.entity.ExCodeInstance;
import com.lanxi.equity.entity.OrgaDeptAct;
import com.lanxi.equity.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/11 11:26
 */
public interface ExcodeInstanceService {




    Integer insert(ExCodeInstance... exCodeInstances);

    List<ExCodeInstance> query(EntityWrapper<ExCodeInstance> wrapper,Page<ExCodeInstance> page);

    List<ExCodeInstance> query(EntityWrapper<ExCodeInstance> wrapper);

    Integer delete(EntityWrapper<ExCodeInstance> wrapper);

    Integer update(EntityWrapper<ExCodeInstance> wrapper,ExCodeInstance newState);

}
