package com.lanxi.equity.business.daily;

import com.lanxi.equity.assist.RetMessage;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangyuanjian created in 2018/3/5 9:36
 */
@Service
public class ManageServicveImpl implements ManageServicve {

    @Autowired
    private DaoService dao;



    @Override public Boolean addCommodity(Commodity commodity) {
        return commodity.insert();
    }
}
