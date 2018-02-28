package com.lanxi.equity.business.common;

import com.baomidou.mybatisplus.activerecord.Model;
import com.google.common.util.concurrent.AtomicDouble;
import com.lanxi.equity.assist.ExtractFields;
import com.lanxi.equity.dao.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/8 15:27
 */
@Service("daoService")
@Lazy
public class DaoServiceImpl implements DaoService {
    @Autowired
    private ApplicationContext      ac;
    @Autowired
    private ActivityDao             activityDao;
    @Autowired
    private AlgorithmDao            algorithmDao;
    @Autowired
    //    @Resource
    private CommPictureDao          commPictureDao;
    @Autowired
    private CommodityDao            commodityDao;
    @Autowired
    private DepartmentDao           departmentDao;
    @Autowired
    private EquityDao               equityDao;
    @Autowired
    private EquityRecordDao         equityRecordDao;
    @Autowired
    private EquityExchangeRecordDao equityExchangeRecordDao;
    @Autowired
    private EquityOrderDao          equityOrderDao;
    @Autowired
    private ExCodeDao               exCodeDao;
    @Autowired
    private ExCodeInstanceDao       exCodeInstanceDao;
    @Autowired
    private FunInterfaceDao         funInterfaceDao;
    @Autowired
    private LabelDao                labelDao;
    @Autowired
    private ManageAccountDao        manageAccountDao;
    @Autowired
    private OperateRecordDao        operateRecordDao;
    @Autowired
    private OrganizationDao         organizationDao;
    @Autowired
    private UserAccountDao          userAccountDao;
    @Autowired
    private ReportDealDao           reportDealDao;

    @Autowired
    private SqlSessionFactory ssf;

    public ActivityDao getActivityDao() {
        return activityDao != null ? activityDao
                                   : ac != null ? (activityDao = ac.getBean(ActivityDao.class))
                                                : null;
    }

    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    public AlgorithmDao getAlgorithmDao() {
        return algorithmDao != null ? algorithmDao
                                    : ac != null ? (algorithmDao = ac.getBean(AlgorithmDao.class))
                                                 : null;
    }

    public void setAlgorithmDao(AlgorithmDao algorithmDao) {
        this.algorithmDao = algorithmDao;
    }

    public CommPictureDao getCommPictureDao() {
        return commPictureDao != null ? commPictureDao
                                      : ac != null ? (commPictureDao = ac.getBean(CommPictureDao.class))
                                                   : null;
    }

    public void setCommPictureDao(CommPictureDao commPictureDao) {
        this.commPictureDao = commPictureDao;
    }

    public CommodityDao getCommodityDao() {
        return commodityDao != null ? commodityDao
                                    : ac != null ? (commodityDao = ac.getBean(CommodityDao.class))
                                                 : null;
    }


    public void setCommodityDao(CommodityDao commodityDao) {
        this.commodityDao = commodityDao;
    }

    public DepartmentDao getDepartmentDao() {
        return departmentDao != null ? departmentDao
                                     : ac != null ? (departmentDao = ac.getBean(DepartmentDao.class))
                                                  : null;
    }


    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public EquityDao getEquityDao() {
        return equityDao != null ? equityDao
                                 : ac != null ? (equityDao = ac.getBean(EquityDao.class))
                                              : null;
    }


    public void setEquityDao(EquityDao equityDao) {
        this.equityDao = equityDao;
    }

    public EquityRecordDao getEquityRecordDao() {
        return equityRecordDao != null ? equityRecordDao
                                       : ac != null ? (equityRecordDao = ac.getBean(EquityRecordDao.class))
                                                    : null;
    }


    public void setEquityRecordDao(EquityRecordDao equityRecordDao) {
        this.equityRecordDao = equityRecordDao;
    }

    public EquityExchangeRecordDao getEquityExchangeRecordDao() {
        return equityExchangeRecordDao != null ? equityExchangeRecordDao
                                               : ac != null ? (equityExchangeRecordDao = ac.getBean(EquityExchangeRecordDao.class))
                                                            : null;
    }


    public void setEquityExchangeRecordDao(EquityExchangeRecordDao equityExchangeRecordDao) {
        this.equityExchangeRecordDao = equityExchangeRecordDao;
    }

    public EquityOrderDao getEquityOrderDao() {
        return equityOrderDao != null ? equityOrderDao
                                      : ac != null ? (equityOrderDao = ac.getBean(EquityOrderDao.class))
                                                   : null;
    }


    public void setEquityOrderDao(EquityOrderDao equityOrderDao) {
        this.equityOrderDao = equityOrderDao;
    }

    public ExCodeDao getExCodeDao() {
        return exCodeDao != null ? exCodeDao
                                 : ac != null ? (exCodeDao = ac.getBean(ExCodeDao.class))
                                              : null;
    }


    public void setExCodeDao(ExCodeDao exCodeDao) {
        this.exCodeDao = exCodeDao;
    }

    public ExCodeInstanceDao getExCodeInstanceDao() {
        return exCodeInstanceDao != null ? exCodeInstanceDao
                                         : ac != null ? (exCodeInstanceDao = ac.getBean(ExCodeInstanceDao.class))
                                                      : null;
    }


    public void setExCodeInstanceDao(ExCodeInstanceDao exCodeInstanceDao) {
        this.exCodeInstanceDao = exCodeInstanceDao;
    }

    public FunInterfaceDao getFunInterfaceDao() {
        return funInterfaceDao != null ? funInterfaceDao
                                       : ac != null ? (funInterfaceDao = ac.getBean(FunInterfaceDao.class))
                                                    : null;
    }


    public void setFunInterfaceDao(FunInterfaceDao funInterfaceDao) {
        this.funInterfaceDao = funInterfaceDao;
    }

    public LabelDao getLabelDao() {
        return labelDao != null ? labelDao
                                : ac != null ? (labelDao = ac.getBean(LabelDao.class))
                                             : null;
    }


    public void setLabelDao(LabelDao labelDao) {
        this.labelDao = labelDao;
    }

    public ManageAccountDao getManageAccountDao() {
        return manageAccountDao != null ? manageAccountDao
                                        : ac != null ? (manageAccountDao = ac.getBean(ManageAccountDao.class))
                                                     : null;
    }


    public void setManageAccountDao(ManageAccountDao manageAccountDao) {
        this.manageAccountDao = manageAccountDao;
    }

    public OperateRecordDao getOperateRecordDao() {
        return operateRecordDao != null ? operateRecordDao
                                        : ac != null ? (operateRecordDao = ac.getBean(OperateRecordDao.class))
                                                     : null;
    }


    public void setOperateRecordDao(OperateRecordDao operateRecordDao) {
        this.operateRecordDao = operateRecordDao;
    }

    public OrganizationDao getOrganizationDao() {
        return organizationDao != null ? organizationDao
                                       : ac != null ? (organizationDao = ac.getBean(OrganizationDao.class))
                                                    : null;
    }


    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    @Override public ReportDealDao getReportDealDao() {
        return this.reportDealDao;
    }

    @Override public void setReportDealDao(ReportDealDao reportDealDao) {
        this.reportDealDao=reportDealDao;
    }

    public UserAccountDao getUserAccountDao() {
        return userAccountDao != null ? userAccountDao
                                      : ac != null ? (userAccountDao = ac.getBean(UserAccountDao.class))
                                                   : null;
    }


    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Transactional
    @Override public synchronized <T extends Model> boolean insertBatch(T... ts) {
        Connection conn = null;
        try {
            conn = ssf.openSession().getConnection();
            if (ts == null) {
                throw new NullPointerException("arg : ts can't be null !");
            }
            List<T> lists = Stream.of(ts).filter(e -> e != null).collect(Collectors.toList());
            if (lists.isEmpty()) {
                return true;
            }
            Class       clazz     = lists.get(0).getClass();
            String      tableName = clazz.getSimpleName().replaceAll("[A-Z]", "_$0").toLowerCase();
            List<Field> fields    = ExtractFields.getClassFields(clazz, true).stream().peek(f -> f.setAccessible(true)).collect(Collectors.toList());

            StringBuffer buffer      = new StringBuffer();
            StringBuffer fieldNames  = new StringBuffer();
            StringBuffer placeholder = new StringBuffer();
            buffer.append("insert into ").append(tableName).append(" (");
            fields.stream().forEach(f -> {
                fieldNames.append(f.getName().replaceAll("[A-Z]", "_$0").toLowerCase() + ",");
                fieldNames.deleteCharAt(fieldNames.length() - 1);
                placeholder.append("?,");
                placeholder.deleteCharAt(placeholder.length() - 1);
            });
            buffer.append(fieldNames).append(") values (");
            buffer.append(placeholder).append(")");

            PreparedStatement preparedStatement = conn.prepareStatement(buffer.toString());

            Arrays.asList(ts).stream().forEach(e -> {
                for (int i = 0; i < fields.size(); i++) {
                    Field f = fields.get(0);
                    try {
                        Object value = f.get(e);
                        if (value == null) {
                            continue;
                        }

                        if (value instanceof String) {
                            preparedStatement.setString(i + 1, (String) value);
                        } else if (value instanceof Integer) {
                            preparedStatement.setInt(i + 1, (Integer) value);
                        } else if (value instanceof Double || value instanceof BigDecimal || value instanceof AtomicDouble
                                   || value instanceof Float) {
                            preparedStatement.setBigDecimal(i + 1, (BigDecimal) value);
                        } else if (value instanceof Long || value instanceof AtomicLong) {
                            preparedStatement.setLong(i + 1, (Long) value);
                        } else {
                            preparedStatement.setObject(i + 1, value);
                        }
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    preparedStatement.addBatch();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
            return preparedStatement.executeBatch().length == ts.length;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
