package com.lanxi.equity.business.common;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lanxi.equity.dao.*;
import com.lanxi.equity.entity.*;
import javafx.geometry.Orientation;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/8 15:27
 */
public interface DaoService {
    ActivityDao getActivityDao();

    void setActivityDao(ActivityDao activityDao);

    AlgorithmDao getAlgorithmDao();

    void setAlgorithmDao(AlgorithmDao algorithmDao);

    CommPictureDao getCommPictureDao();

    void setCommPictureDao(CommPictureDao commPictureDao);

    CommodityDao getCommodityDao();

    void setCommodityDao(CommodityDao commodityDao);

    DepartmentDao getDepartmentDao();

    void setDepartmentDao(DepartmentDao departmentDao);

    EquityDao getEquityDao();

    void setEquityDao(EquityDao equityDao);

    EquityRecordDao getEquityRecordDao();

    void setEquityRecordDao(EquityRecordDao equityRecordDao);

    EquityExchangeRecordDao getEquityExchangeRecordDao();

    void setEquityExchangeRecordDao(EquityExchangeRecordDao equityExchangeRecordDao);

    EquityOrderDao getEquityOrderDao();

    void setEquityOrderDao(EquityOrderDao equityOrderDao);

    ExCodeDao getExCodeDao();

    void setExCodeDao(ExCodeDao exCodeDao);

    ExCodeInstanceDao getExCodeInstanceDao();

    void setExCodeInstanceDao(ExCodeInstanceDao exCodeInstanceDao);

    FunInterfaceDao getFunInterfaceDao();

    void setFunInterfaceDao(FunInterfaceDao funInterfaceDao);

    LabelDao getLabelDao();

    void setLabelDao(LabelDao labelDao);

    ManageAccountDao getManageAccountDao();

    void setManageAccountDao(ManageAccountDao manageAccountDao);

    OperateRecordDao getOperateRecordDao();

    void setOperateRecordDao(OperateRecordDao operateRecordDao);

    OrganizationDao getOrganizationDao();

    void setOrganizationDao(OrganizationDao organizationDao);

    ReportDealDao getReportDealDao();

    void setReportDealDao(ReportDealDao reportDealDao);

    UserAccountDao getUserAccountDao();

    void setUserAccountDao(UserAccountDao userAccountDao);

    //----------------------------------------------------------
    default BaseMapper getDao(Class clazz) {
        if (Activity.class.equals(clazz)) {
            return getActivityDao();
        }else if(Algorithm.class.equals(clazz)){
            return getAlgorithmDao();
        }else if(CommPicture.class.equals(clazz)){
            return getCommPictureDao();
        }else if(Commodity.class.equals(clazz)){
            return getCommodityDao();
        }else if(Department.class.equals(clazz)){
            return getDepartmentDao();
        }else if(Equity.class.equals(clazz)){
            return getEquityDao();
        }else if(EquityRecord.class.equals(clazz)){
            return getEquityRecordDao();
        }else if(EquityExchangeRecord.class.equals(clazz)){
            return getEquityExchangeRecordDao();
        }else if(EquityOrder.class.equals(clazz)){
            return getEquityOrderDao();
        }else if(ExCode.class.equals(clazz)){
            return getExCodeDao();
        }else if(ExCodeInstance.class.equals(clazz)){
            return getExCodeInstanceDao();
        }else if(FunInterface.class.equals(clazz)){
            return getFunInterfaceDao();
        }else if(Label.class.equals(clazz)){
            return getLabelDao();
        }else if(ManageAccount.class.equals(clazz)){
            return getManageAccountDao();
        }else if(OperateRecord.class.equals(clazz)){
            return getOperateRecordDao();
        }else if(Orientation.class.equals(clazz)){
            return getOrganizationDao();
        }else if(UserAccount.class.equals(clazz)){
            return getUserAccountDao();
        }else {
            return null;
        }
    }

    <T extends Model> boolean insertBatch(T... ts);

    default <T extends Model> Integer insert(T... ts) {
        if (ts == null) {
            throw new NullPointerException("arg : ts can't be null !");
        }
        List<T> lists = Stream.of(ts).filter(e -> e != null).collect(Collectors.toList());
        if (lists.isEmpty()) {
            return 0;
        }
        AtomicInteger num    = new AtomicInteger(0);
        BaseMapper    mapper = lists.stream().findAny().map(e -> getDao(e.getClass())).orElse(null);
        if (mapper == null) {
            throw new IllegalArgumentException("can't find sutiable mapper for arg !");
        }
        lists.stream().forEach(e -> num.addAndGet(mapper.insert(e)));
        return num.get();
    }

    default <T extends Model> List<T> query(Class clazz, EntityWrapper<T> wrapper, Page<T> page) {
        if (wrapper == null) {
            throw new NullPointerException("wrapper can't be null !");
        }
        if (clazz == null) {
            throw new NullPointerException("clazz can't be null !");
        }
        BaseMapper mapper = getDao(clazz);
        if (mapper == null) {
            throw new IllegalArgumentException("can't find sutiable mapper for arg !");
        }
        return page == null ? mapper.selectList(wrapper)
                            : mapper.selectPage(page, wrapper);
    }

    default <T extends Model> List<T> query(Class clazz, EntityWrapper<T> wrapper) {
        return query(clazz, wrapper, null);
    }

    default <T extends Model> Integer delete(Class clazz, EntityWrapper<T> wrapper) {
        throw new UnsupportedOperationException("delete method is not allowed be invoked !");
//        if (wrapper == null) {
//            throw new NullPointerException("wrapper can't be null !");
//        }
//        if (clazz == null) {
//            throw new NullPointerException("clazz can't be null !");
//        }
//        BaseMapper mapper = getDao(clazz);
//        if (mapper == null) {
//            throw new IllegalArgumentException("can't find sutiable mapper for arg !");
//        }
//        return mapper.delete(wrapper);
    }

    default <T extends Model> Integer update(EntityWrapper<T> wrapper,T newState){
        if (wrapper == null) {
            throw new NullPointerException("wrapper can't be null !");
        }
        if(newState==null){
            throw new NullPointerException("newstate can't be null !");
        }
        BaseMapper mapper = getDao(newState.getClass());
        if (mapper == null) {
            throw new IllegalArgumentException("can't find sutiable mapper for arg !");
        }
        return mapper.update(newState,wrapper);
    }

}

