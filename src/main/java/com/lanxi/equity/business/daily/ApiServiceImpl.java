package com.lanxi.equity.business.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lanxi.equity.assist.ConvertAssist;
import com.lanxi.equity.assist.RetMessage;
import com.lanxi.equity.assist.TimeAssist;
import com.lanxi.equity.assist.ToJson;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.business.daoservice.ExCodeService;
import com.lanxi.equity.business.daoservice.ExcodeInstanceService;
import com.lanxi.equity.business.daoservice.UserAccountService;
import com.lanxi.equity.config.*;
import com.lanxi.equity.entity.*;
import com.lanxi.equity.report.api.AddEquityRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yangyuanjian created in 2018/2/11 10:11
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private UserAccountService userService;

    @Autowired
    private ExcodeInstanceService excodeInstanceService;

    @Autowired
    private ExCodeService exCodeService;
    @Autowired
    private DaoService    dao;

    @Transactional
    @Override public RetMessage<ArrayList<AddEquityRes.DealResult>> excodeEx(OrgaDeptAct oda, UserAccount user, List<String> codes) {
        //创建机构部门活动查询条件实例
        ExCodeInstance temp = new ExCodeInstance();
        temp.fromOda(oda);
        EntityWrapper<ExCodeInstance> wrapper=ConvertAssist.objToWrapper(temp);
        wrapper.in("code",codes);
        //查兑换码列表
        List<ExCodeInstance> codeInstances = excodeInstanceService.query(wrapper, null);
        //创建处理结果列表
        ArrayList<AddEquityRes.DealResult> results = new ArrayList<>();
        //查询结果按兑换码状态分组
        Map<String, List<ExCodeInstance>> mapByStatus = codeInstances.stream()
                                                                     .peek(e->{
                                                                         if(e.getInstanceStatus()==null){
                                                                             e.setInstanceStatus(CodeInstanceStatus.UNKNOWN);
                                                                         }
                                                                     })
                                                                     .collect(Collectors.groupingBy(e -> e.getInstanceStatus()));
        //处理状态为normal的兑换码列表
        List<ExCodeInstance> normals=Optional.ofNullable(mapByStatus.get(CodeInstanceStatus.NORMAL))
                                             .orElse(new ArrayList<ExCodeInstance>());
        if(!normals.isEmpty()) {
            //创建权益记录
            Equity equity = new Equity();
            equity.setEquityId(IdWorker.getId() + "");
            equity.setUserId(user.getUserId());
            equity.setOrgaUserId(user.getOrgaUserId());
            equity.fromOda(oda);
            equity.setSource(EquitySource.CODE_EX);
            equity.setReason("api兑换码兑换");
            equity.setCodeTokensProto(new ArrayList<>());
            equity.setUseful(0);
            //处理单个兑换码实例
            normals.stream().forEach(e -> {
                //创建处理结果
                AddEquityRes.DealResult result   = new AddEquityRes.DealResult();
                result.setCode(e.getCode());
                //修改实例状态--无用
                e.setInstanceStatus(CodeInstanceStatus.USED);
                //累计权益值
                equity.setTotal(equity.getUseful() + e.getValue());
                equity.setValidate(e.getValidate());
                equity.getCodeTokensProto().add(e.getCode());
                //配置处理结果
                result.setDealStatus(RetCode.SUCCESS);
                result.setDesc(ConvertAssist.extractCommentInfo(RetCode.class, "SUCCESS"));
                results.add(result);
            });
            //更新过期时间与可用值
            equity.setUseful(equity.getTotal());
            equity.setOverDate(TimeAssist.nDayLater(equity.getValidate()));
            equity.setOverTime(TimeAssist.now());
            equity.setEquityStatus(EquityStatus.NORMAL);
            //组装兑换码更新条件
//            EntityWrapper<ExCodeInstance> wrapper=ConvertAssist.objToWrapper(temp);
            wrapper.eq("instance_status",CodeInstanceStatus.NORMAL);
            //组装兑换码更新内容
            ExCodeInstance newState=new ExCodeInstance();
            newState.setExDate(TimeAssist.today());
            newState.setExTime(TimeAssist.now());
            newState.setInstanceStatus(CodeInstanceStatus.USED);
            //更新数据库兑换码信息
            Integer successNum=dao.update(wrapper,newState);
            //若更新数量与实际数量不符,抛出RuntimeException触发事务回滚
            if(successNum!=normals.size()){
                throw new RuntimeException("num of database updated maybe wrong !");
            }
            equity.insert();
            //添加权益记录
            EquityRecord equityRecord=new EquityRecord();
            equityRecord.fromOda(equity);
            equityRecord.setRecordId(IdWorker.getId()+"");
            equityRecord.setUserId(equity.getUserId());
            equityRecord.setRecordType(EquityReType.PLUS);
            equityRecord.setDetailType(EquityReDetailType.CODE_EX);
            equityRecord.setRecordDesc("兑换码兑换增加权益值");
            equityRecord.setVeriationValue("+"+equity.getTotal());
            equityRecord.setAddDate(TimeAssist.today());
            equityRecord.setAddTime(TimeAssist.now());
            equityRecord.insert();
        }
        //处理过期的
        List<ExCodeInstance> expireds=Optional.ofNullable(mapByStatus.get(CodeInstanceStatus.OVERTIME)).orElse(new ArrayList<>());
        expireds.stream().forEach(e->{
            AddEquityRes.DealResult result   = new AddEquityRes.DealResult();
            result.setCode(e.getCode());
            result.setDealStatus(RetCode.CODE_INSTACNCE_EXPIRED);
            result.setDesc(ConvertAssist.extractCommentInfo(RetCode.class, "CODE_INSTACNCE_EXPIRED"));
            results.add(result);
        });
        //处理已使用的
        List<ExCodeInstance> useds=Optional.ofNullable(mapByStatus.get(CodeInstanceStatus.USED)).orElse(new ArrayList<>());
        useds.stream().forEach(e->{
            AddEquityRes.DealResult result   = new AddEquityRes.DealResult();
            result.setCode(e.getCode());
            result.setDealStatus(RetCode.CODE_INSTACNCE_USED);
            result.setDesc(ConvertAssist.extractCommentInfo(RetCode.class, "CODE_INSTACNCE_USED"));
            results.add(result);
        });
        //处理注销的
        List<ExCodeInstance> canceleds=Optional.ofNullable(mapByStatus.get(CodeInstanceStatus.CANCELED)).orElse(new ArrayList<>());
        canceleds.stream().forEach(e->{
            AddEquityRes.DealResult result   = new AddEquityRes.DealResult();
            result.setCode(e.getCode());
            result.setDealStatus(RetCode.CODE_INSTACNCE_NOT_EXISTED);
            result.setDesc(ConvertAssist.extractCommentInfo(RetCode.class, "CODE_INSTACNCE_NOT_EXISTED"));
            results.add(result);
        });
        //处理状态为null的
        List<ExCodeInstance> unknowns=Optional.ofNullable(mapByStatus.get(CodeInstanceStatus.UNKNOWN)).orElse(new ArrayList<>());
        unknowns.stream().forEach(e->{
            AddEquityRes.DealResult result   = new AddEquityRes.DealResult();
            result.setCode(e.getCode());
            result.setDealStatus(RetCode.CODE_INSTACNCE_UNKNOWN);
            result.setDesc(ConvertAssist.extractCommentInfo(RetCode.class, "CODE_INSTACNCE_UNKNOWN"));
            results.add(result);
        });
        //处理codes中未查询到的兑换码
        List<String> dealedCodes=codeInstances.stream().map(e->e.getCode()).collect(Collectors.toList());
        codes.stream().filter(e->!dealedCodes.contains(e)).forEach(e->{
            AddEquityRes.DealResult result   = new AddEquityRes.DealResult();
            result.setCode(e);
            result.setDealStatus(RetCode.CODE_INSTACNCE_NOT_EXISTED);
            result.setDesc(ConvertAssist.extractCommentInfo(RetCode.class, "CODE_INSTACNCE_NOT_EXISTED"));
            results.add(result);
        });
        //创建返回结果
        RetMessage<ArrayList<AddEquityRes.DealResult>> retMessage=new RetMessage<>();
        retMessage.setRetCode(RetCode.SUCCESS);
        retMessage.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"SUCCESS"));
        retMessage.setDetail(results);
        return retMessage;
    }

    @Override public synchronized RetMessage<ArrayList<ExCodeInstance>> generateExCodeInstance(ExCode exCode, int count) {
        RetMessage<ArrayList<ExCodeInstance>> result=new RetMessage<>();

        if(exCode==null){
            throw new NullPointerException("exCode can't be null !");
        }
        if(exCode.getCodeId()==null){
            throw new NullPointerException("exCode.codeId can't be null !");
        }
        ExCode temp=new ExCode();
        temp.fromOda(exCode);
        temp.setCodeId(exCode.getCodeId());

        EntityWrapper<ExCode> wrapper=new EntityWrapper<>();
        if(exCode.getCodeId()!=null&&!exCode.getCodeId().isEmpty()){
            wrapper.eq("code_id",exCode.getCodeId());
        }else {
            wrapper.eq("orga_id", exCode.getOrgaId());
            wrapper.eq("dept_id", exCode.getDeptId());
            wrapper.eq("act_id", exCode.getActId());
            wrapper.ne("code_status", CodeStatus.DELETED);
        }

        List<ExCode> codes=exCodeService.query(wrapper);
        codes=ConvertAssist.optinalDeal(codes, ArrayList::new);
        if(codes.size()==0){
            result.setRetCode(RetCode.CODE_NOT_FOUND);
            result.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"CODE_NOT_FOUND"));
            result.setDetail(null);
            return result;
        }
        if(codes.size()>1){
            result.setRetCode(RetCode.CODE_MORE_THEN_ONE);
            result.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"CODE_MORE_THEN_ONE"));
            result.setDetail(null);
            return result;
        }
        ExCode code=codes.get(0);
        ArrayList<ExCodeInstance> list=new ArrayList<>();
        while(count-->0){
            ExCodeInstance instance=new ExCodeInstance();
            instance.fromOda(code);
            instance.setInstanceId(IdWorker.getId());
            instance.setExCodeId(code.getCodeId());
            instance.setExCodeInfo(ToJson.toJson(code));
            instance.setInstanceStatus(CodeInstanceStatus.NORMAL);
            instance.setOverDate(TimeAssist.nDayLater(Integer.getInteger(code.getValidate())));
            instance.setOverTime(TimeAssist.now());
            instance.setCode(code.generateCode()+"");
            instance.setValue(code.getValue());
            instance.setValidate(Integer.parseInt(code.getValidate()));
            instance.insert();
            list.add(instance);
        }
        result.setRetCode(RetCode.SUCCESS);
        result.setRetMessage(ConvertAssist.extractCommentInfo(RetCode.class,"SUCCESS"));
        result.setDetail(list);
        return result;
    }

//    @Override public RetMessage<ArrayList<AddEquityRes.DealResult>> excodeExTest(OrgaDeptAct oda, UserAccount user, List<String> codes) {
//        return null;
//    }


}
