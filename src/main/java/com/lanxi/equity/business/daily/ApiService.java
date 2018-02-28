package com.lanxi.equity.business.daily;

import com.lanxi.equity.assist.RetMessage;
import com.lanxi.equity.entity.ExCode;
import com.lanxi.equity.entity.ExCodeInstance;
import com.lanxi.equity.entity.OrgaDeptAct;
import com.lanxi.equity.entity.UserAccount;
import com.lanxi.equity.report.api.AddEquityRes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyuanjian created in 2018/2/11 10:10
 */
public interface ApiService {
    RetMessage<ArrayList<AddEquityRes.DealResult>> excodeEx(OrgaDeptAct oda, UserAccount user, List<String> codes);
    RetMessage<ArrayList<ExCodeInstance>> generateExCodeInstance(ExCode exCode,int count);

//    RetMessage<ArrayList<AddEquityRes.DealResult>> excodeExTest(OrgaDeptAct oda,UserAccount user,List<String> codes);
}

