package com.lanxi.equity.test;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lanxi.equity.Aop.AopInsertUpdateCheck;
import com.lanxi.equity.assist.TimeAssist;
import com.lanxi.equity.business.common.DaoService;
import com.lanxi.equity.config.CodeInstanceStatus;
import com.lanxi.equity.config.CommStatus;
import com.lanxi.equity.config.CommodityType;
import com.lanxi.equity.config.ConstConfig;
import com.lanxi.equity.dao.CommPictureDao;
import com.lanxi.equity.dao.CommodityDao;
import com.lanxi.equity.dao.EquityRecordDao;
import com.lanxi.equity.entity.*;
import com.lanxi.util.utils.HttpUtil;
import com.lanxi.util.utils.LoggerUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/8 15:42
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:xml/applicationContext.xml")
public class TestSpring {
    @Resource
    private ApplicationContext ac;

    @Before
    public void init() {
                LoggerUtil.setLogLevel(LoggerUtil.LogLevel.DEBUG);
        LoggerUtil.init();
        ac = new ClassPathXmlApplicationContext("xml/applicationContext.xml");
    }

    @Test
    public void test1() throws Exception {
        System.out.println(ac);
        DaoService      dao    = ac.getBean(DaoService.class);
        EquityRecordDao picDao = dao.getEquityRecordDao();
        System.out.println(ac.getBean(CommPictureDao.class));
        //        ac.getBeansOfType(null).entrySet().stream().forEach(e -> System.out.println(e.getValue().getClass().getSimpleName()));
        //        System.err.println(ac.getBean(DaoService.class).getAlgorithmDao().selectById("1"));
        //        Algorithm algorithm=new Algorithm();
        CommPicture pic  = ac.getBean(CommPictureDao.class).selectById("2");
        File        file = pic.getSsPicFile();
        if (!file.exists()) {
            file.createNewFile();
        }
        Thread.sleep(3000);
        file.delete();
        Thread.sleep(3000);
        //        algorithm.setAlgoId("1");
        //        System.out.println(algorithm.selectById());
    }

    @Test
    public void test2() throws Exception {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        SqlSessionFactory     ssf  = ssfb.getObject();
        System.out.println(ssf);
        Blob blob = new SerialBlob(new byte[] {});
    }

    @Test
    public void test3() throws IOException {
        File            fiel  = new File("t.jpg");
        CommPicture pic = new CommPicture();
        pic.setCommId("2");
        pic.setSsPicFile(fiel);
        pic.updateById();
    }

    @Test
    public void test4(){
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                   + "<xml>\n"
                   + "  <head>\n"
                   + "    <msgId>968083958697410560</msgId>\n"
                   + "    <actId>968083958525444097</actId>\n"
                   + "    <sign>58d892d908501cbd0d8246e5f624405a</sign>\n"
                   + "    <sendTime>192256</sendTime>\n"
                   + "    <sendDate>20180226</sendDate>\n"
                   + "    <funId>1001</funId>\n"
                   + "    <deptId>968083958525444096</deptId>\n"
                   + "    <orgaId>968083958462529536</orgaId>\n"
                   + "  </head>\n"
                   + "  <body>\n"
                   + "    <userId>968083958705799168</userId>\n"
                   + "    <codes>\n"
                   + "      <code>8705799169</code>\n"
                   + "      <code>8705799170</code>\n"
                   + "      <code>8705799171</code>\n"
                   + "      <code>8705799172</code>\n"
                   + "      <code>8705799173</code>\n"
                   + "    </codes>\n"
                   + "  </body>\n"
                   + "</xml>\n";
    }
    @Test
    public void test5(){
        String[] codes=new String[]{"8705799169","8705799170","8705799171"};

        List<String> cs=new ArrayList<>();

        Stream.generate(IdWorker::getId).map(e->e%10000000000L+"").limit(100).forEach(cs::add);



        cs.stream().forEach(e->{
            ExCodeInstance instance=new ExCodeInstance();
            instance.setOrgaId("968083958462529536");
            instance.setDeptId("968083958525444096");
            instance.setActId("968083958525444097");

            instance.setInstanceId(IdWorker.getId());
            instance.setExCodeId(ConstConfig.TEST_CODE_ID);
            instance.setInstanceStatus(CodeInstanceStatus.NORMAL);

            instance.setOverDate(TimeAssist.nDayLater(30));
            instance.setOverTime(TimeAssist.now());
            instance.setCode(e);
            instance.setValue(10);
            instance.setValidate(30);
            instance.insert();
        });

        cs.stream().forEach(System.out::println);


    }
    @Test
    public void test6(){
        ExCode activity =new ExCode();

        activity.setCodeId(IdWorker.getId()+"");

        activity.setOrgaId("968083958462529536");
        activity.setDeptId("968083958525444096");
        activity.setActId("968083958525444097");

        activity.setP1((ConstConfig.BIGPRIMES.get(activity.hashCode() % ConstConfig.BIGPRIMES.size())));
        activity.setP2((ConstConfig.BIGPRIMES.get(activity.getP1().hashCode()%ConstConfig.BIGPRIMES.size())));
        activity.setPower(ConstConfig.POWERS.get(activity.getP2().hashCode()%ConstConfig.POWERS.size()));
//        activity.setN(activity.getP1().multiply(activity.getP2()));
        activity.setVarProto(new AtomicLong(ConstConfig.CODE_VAR_START));

        activity.insert();
    }
    @Test
    public void test7(){
        ExCode activity =new ExCode();
        activity.setOrgaId("968083958462529536");
        activity.setDeptId("968083958525444096");
        activity.setActId("968083958525444097");

        activity=ac.getBean(DaoService.class).getExCodeDao().selectOne(activity);
        int i=0;
        while(i++<10){
            ExCodeInstance instance=new ExCodeInstance();
            instance.setOrgaId("968083958462529536");
            instance.setDeptId("968083958525444096");
            instance.setActId("968083958525444097");

            instance.setInstanceId(IdWorker.getId());
            instance.setExCodeId(activity.getCodeId());
            instance.setInstanceStatus(CodeInstanceStatus.NORMAL);

            instance.setOverDate(TimeAssist.nDayLater(30));
            instance.setOverTime(TimeAssist.now());
            instance.setCode(activity.generateCode()+"");
            instance.setValue(10);
            instance.setValidate(30);
            instance.insert();

            System.out.println(instance.getCode());
        }
    }
    @Test
    public void test8(){
        Commodity commodity=new Commodity();
        commodity.setCommId(IdWorker.getId()+"");
        commodity.setEleCommId(ConstConfig.TEST_CHARGE);
        commodity.setCommType(CommodityType.TEL_CHARGE);
        commodity.setCommName("测试话费充值");
        commodity.setValidate(30);
        commodity.setUseRule("使用规则");
        commodity.setCommDesc("测试话费充值");
        commodity.setAddDate(TimeAssist.today());
        commodity.setAddTime(TimeAssist.now());
        commodity.setAddBy("whiteyang");
        commodity.setValue(1);
        commodity.setPrice(new BigDecimal(10));
        commodity.setCommStatus(CommStatus.UP);
        commodity.setVersion(1L);

        commodity.insert();
    }

    @Test
    public void test9(){

        AopInsertUpdateCheck aop=ac.getBean(AopInsertUpdateCheck.class);

        DaoService dao=ac.getBean(DaoService.class);

        CommodityDao commDao=dao.getCommodityDao();

        Commodity commodity=new Commodity();
        commodity.setCommId(IdWorker.getId()+"");
        commDao.insert(commodity);

    }

}
