package com.lanxi.equity.business.daoservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.entity.UserAccount;

import java.sql.Wrapper;
import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/11 10:54
 */
public interface UserAccountService {
    UserAccount queryUserAccount(UserAccount account);
    List<UserAccount> queryUserAccounts(UserAccount account, Page<UserAccount> page);
    List<UserAccount> queryUserAccounts(EntityWrapper<UserAccount> wrapper, Page<UserAccount> page);
}
