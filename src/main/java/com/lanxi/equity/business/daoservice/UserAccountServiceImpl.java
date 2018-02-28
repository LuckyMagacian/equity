package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.assist.ConvertAssist;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/11 10:55
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {


    @Autowired
    private DaoService dao;

    @Override public UserAccount queryUserAccount(UserAccount account) {
        return dao.getUserAccountDao().selectOne(account);
    }

    @Override public List<UserAccount> queryUserAccounts(UserAccount account, Page<UserAccount> page) {
        return queryUserAccounts(ConvertAssist.objToWrapper(account),page);
    }

    @Override public List<UserAccount> queryUserAccounts(EntityWrapper<UserAccount> wrapper, Page<UserAccount> page) {
        return page == null ? dao.getUserAccountDao().selectList(wrapper)
                                   : dao.getUserAccountDao().selectPage(page, wrapper);
    }

}
