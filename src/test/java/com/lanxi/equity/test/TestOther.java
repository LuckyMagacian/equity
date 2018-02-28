package com.lanxi.equity.test;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.google.common.collect.HashBiMap;
import com.lanxi.equity.assist.*;
import com.lanxi.equity.config.*;
import com.lanxi.equity.entity.*;
import com.lanxi.equity.report.api.AddEquityReq;
import com.lanxi.equity.report.api.ReportSign;
import com.lanxi.equity.report.api.Req;
import com.lanxi.equity.report.api.Res;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author yangyuanjian created in 2018/2/6 9:38
 */
public class TestOther {

    @Test
    public void test1() {

        AddEquityReq body = new AddEquityReq();
        System.out.println(HibernateValidator.validate(body));
    }

    @Test
    public void test2() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hhmmss")));
    }

    @Test
    public void test3() {
        String[] values = null;
        System.out.println(Arrays.asList(values));
    }

    @Test
    public void test4() {
        Activity act = new Activity();
        System.out.println(HibernateValidator.validate(act));
    }

    @Test
    public void test5() {
        new MyClassLoader().loadFromPath(true).stream().forEach(System.out::println);
    }

    @Test
    public void test6() {
        Map<String, Class> map = new LinkedHashMap<>();
        map.put("活动", Activity.class);
        map.put("算法", Algorithm.class);
        map.put("商品图片", CommPicture.class);
        map.put("商品", Commodity.class);
        map.put("部门", Department.class);
        map.put("权益", Equity.class);
        map.put("权益记录", EquityRecord.class);
        map.put("权益兑换记录", EquityExchangeRecord.class);
        map.put("权益订单", EquityOrder.class);
        map.put("兑换码", ExCode.class);
        map.put("兑换码实体", ExCodeInstance.class);
        map.put("功能接口", FunInterface.class);
        map.put("商品分类标签", Label.class);
        map.put("管理员账户", ManageAccount.class);
        map.put("操作记录", OperateRecord.class);
        //                 map.put("机构部门活动通用字段",OrgaDeptAct.class);
        map.put("机构", Organization.class);
        map.put("用户账户", UserAccount.class);


        map.put("活动状态", ActStatus.class);
        map.put("商品类型", CommodityType.class);
        map.put("商品状态", CommStatus.class);
        map.put("通用状态", Status.class);
        map.put("兑换码实例状态", CodeInstanceStatus.class);
        map.put("兑换码状态", CodeStatus.class);
        map.put("权益记录详细类型", EquityReDetailType.class);
        map.put("权益记录类型", EquityReType.class);
        map.put("权益值来源", EquitySource.class);
        map.put("权益值状态", EquityStatus.class);
        map.put("兑换记录状态", ExStatus.class);
        map.put("功能接口状态", FunStatus.class);
        map.put("商品分类标签状态", LabelStatus.class);
        map.put("电子礼品平台订单状态", OrderStatus.class);


        CNMap.ENUM_CN.entrySet().stream()
                     .filter(e -> e.getValue().isInterface())
                     .forEach(e -> {
                         String desc = e.getKey();
                         Class  c    = e.getValue();
                         List<Field> fields = Stream.of(c.getDeclaredFields())
                                                    .filter(f -> Modifier.isPublic(f.getModifiers()))
                                                    .filter(f -> f.getType().equals(String.class))
                                                    .collect(Collectors.toList());
                         System.out.println("#### " + desc + ":" + c.getName() + "\n```");
                         fields.stream().forEach(f -> {
                             f.setAccessible(true);
                             try {
                                 String   fieldName  = f.getName();
                                 String   fieldValue = f.get(c) + "";
                                 String[] comments   = getComment(f);
                                 String comment = comments == null ? ""
                                                                   : comments.length == 1 ? comments[0]
                                                                                          : null;
                                 System.out.format("%-20s%-20s\n", fieldValue, comment != null ? comment : Arrays.asList(comments));
                             } catch (IllegalAccessException e1) {
                                 e1.printStackTrace();
                             }
                         });
                         System.out.println("```");
                     });
        //        map.entrySet().stream()
        //           .filter(e -> !e.getValue().isInterface())
        //           .forEach(e -> {
        //               String      desc   = e.getKey();
        //               Class       c      = e.getValue();
        //               List<Field> fields = getClassFields(c, true);
        //               System.out.println("#### " + desc + ":" + c.getName() + "\n```");
        //               fields.stream().forEach(f -> {
        //                   f.setAccessible(true);
        //                   String   fieldName = f.getName();
        //                   String[] comments  = getComment(f);
        //                   String comment = comments == null ? ""
        //                                                     : comments.length == 1 ? comments[0]
        //                                                                            : null;
        //                   System.out.format("%-20s%-20s\n", fieldName, comment != null ? comment : Arrays.asList(comments));
        //               });
        //               System.out.println("```");
        //           });

    }

    static String[] getComment(Field field) {
        Comment annotation = field.getAnnotation(Comment.class);
        return annotation == null ? null
                                  : annotation.value();
    }

    static List<Class> getSuperInterfaces(Class clazz, boolean includeSuper) {
        List<Class> interfaces = new ArrayList<>();
        interfaces.addAll(new ArrayList<>(Arrays.asList(clazz.getInterfaces())));
        if (!includeSuper) {
            return interfaces;
        }
        Stream.of(clazz.getInterfaces()).forEach(c -> interfaces.addAll(getSuperInterfaces(c, includeSuper)));
        return interfaces;
    }

    private static List<Field> getSuperFields(Class superClass, boolean includeSuper) {
        List<Field> fields = new ArrayList<>();
        if (superClass.equals(Object.class)) {
            return fields;
        }

        fields.addAll(Stream.of(superClass.getDeclaredFields())
                            .filter(f -> !Modifier.isStatic(f.getModifiers()))
                            .filter(f -> !Modifier.isPrivate(f.getModifiers()))
                            .collect(Collectors.toList()));
        if (!includeSuper) {
            return fields;
        }
        fields.addAll(getSuperFields(superClass.getSuperclass(), includeSuper));
        return fields;
    }

    static List<Field> getClassFields(Class clazz, boolean includeSuper) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Stream.of(clazz.getDeclaredFields()).filter(f -> !Modifier.isStatic(f.getModifiers())).collect(Collectors.toList()));
        if (!includeSuper) {
            return fields;
        }
        fields.addAll(getSuperFields(clazz.getSuperclass(), includeSuper));
        return fields;
    }

    @Test
    public void test8() throws NoSuchMethodException {
        Method method = Algorithm.class.getMethod("insert");
        System.out.println(method);
    }

    @Test
    public void test9() {
        OrgaDeptAct oda = new Equity();
        oda.setActId("10086");
        EquityRecord record = new EquityRecord();
        record.fromOda(oda);
        System.out.println(record);
    }

    @Test
    public void test10() {
        Class clazz = Equity.class;
        System.out.println(ConvertAssist.extractCommentInfo(clazz));
        System.out.println(ConvertAssist.extractCommentInfo(clazz, "orgaId"));
        System.out.println(ConvertAssist.extractCommentInfo(RetCode.class, "CODE_USED"));
    }

    @Test
    public void test11() {
        StringBuffer buffer = new StringBuffer();
        System.out.println(buffer.indexOf(","));
        System.out.println(buffer.lastIndexOf(","));
    }

    @Test
    public void test12() {
        Map<String, Model> map               = new HashMap<String, Model>();
        Type               type              = map.getClass().getGenericSuperclass();
        ParameterizedType  parameterizedType = ParameterizedType.class.cast(type);
        System.out.println(parameterizedType.getRawType());
        //        for (Type each : parameterizedType.getActualTypeArguments()) {
        //            System.out.println(each.getTypeName());
        //            System.out.println(each.getClass());
        //        }
    }

    @Test
    public void test13() {
        Map<String, Integer> map = new HashMap();

        Type              type              = (ParameterizedType) map;
        ParameterizedType parameterizedType = ParameterizedType.class.cast(type);
        for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
            System.out.println(typeArgument.getTypeName());
        }
    }

    @Test
    public void test14() {
        ArrayList<String> a = new ArrayList<String>();
        //applyMethod.invoke(null, a);
        Stream.of(a.getClass().getTypeParameters())
              //              .map(e -> e.getTypeName())
              //              .map(e->e instanceof ParameterizedType)
              .forEach(e -> {
                  TypeVariable variable = e;
                  Stream.of(variable.getBounds()).forEach(System.out::println);
                  Stream.of(variable.getAnnotatedBounds()).forEach(System.out::println);
              });
    }

    @Test
    public void test15() throws NoSuchMethodException {
        Class  clazz  = Class.class;
        Method method = clazz.getDeclaredMethod("getGenericSignature0");
        System.out.println(method);
    }

    @Test
    public void test16() {
        String string = test11116();
        System.out.println(string);
    }

    @Test
    public void test17() {
        Equity equity = new Equity();
        equity.setEquityId("10086");
        EntityWrapper<Equity> wrapper  = ConvertAssist.objToWrapper(equity);
        EntityWrapper<Equity> wrapper1 = new EntityWrapper<>(equity);
        System.out.println(wrapper);
        System.out.println(wrapper1.getSqlSegment());
    }


    public <T> T test11116() {
        return null;
    }

    @Test
    public void test18() throws IOException {
        Document document = DocumentHelper.createDocument();
        Element  root     =document.addElement("xml");

        Req      req      = new Req();

        req.setOrgaId(IdWorker.getId() + "");

        req.setDeptId(IdWorker.getId() + "");

        req.setActId(IdWorker.getId() + "");

        req.setSendDate(TimeAssist.today());

        req.setSendTime(TimeAssist.now());

        req.setMsgId(IdWorker.getId() + "");

        req.setFunId(ConstConfig.FUN_ID_EXCODE_INSTANCE_EXCHANGE);




        AddEquityReq bodyReq = new AddEquityReq();
        bodyReq.setUserId(IdWorker.getId() + "");
        List<String> codes = new ArrayList<>();
        while (codes.size() < 5)
            codes.add(IdWorker.getId() % 10000000000L + "");
        bodyReq.setCodes(codes);

        Element codesEle=DocumentHelper.createElement("codes");
        codes.stream().forEach(e->{
            codesEle.addElement("code").setText(e);
        });

        Element bodyEle=bodyReq.easyToElement("body");
        bodyEle.add(codesEle);



        req.setSign(ReportSign.sign(req.easyToElement("head"),bodyEle));

        root.add(req.easyToElement("head"));
        root.add(bodyEle);

        StringWriter writer=new StringWriter();
        new XMLWriter(writer,OutputFormat.createPrettyPrint()).write(document);

        System.out.println(writer);

    }
    @Test
    public void test19(){
        System.out.println(ConvertAssist.extractCommentInfo(RetCode.class, "CODE_INSTACNCE_NOT_EXISTED"));
    }
    @Test
    public void test20(){
        List<Field> fields=ExtractFields.getClassFields(ReportDeal.class,true);
        fields.stream().map(Field::getName).sorted().forEach(System.out::println);
    }
    @Test
    public void test21(){
        Class  clazz     = ExCode.class;
        String className = clazz.getSimpleName();
        String tableName = (char) (className.charAt(0) + 32) + className.substring(1);

        StringBuilder sql = new StringBuilder("#创建"+ConvertAssist.extractCommentInfo(clazz)+"表\n"+"create table ");
        //获取表名
        tableName = tableName.replaceAll("[A-Z]{1}", "_$0").toLowerCase();
        sql.append(tableName + "(\n");
        getClassFields(clazz, true)
                .forEach(f -> {
                    String s       = null;
                    String type    = null;
                    String comment = null;
                    try {
                        s = f.getName();
                        s = s.replaceAll("[A-Z]{1}", "_$0").toLowerCase()+" ";
                        type = null;
                        comment = " comment '" + getComment(f)[0] + "',";

                        if (f.getType().equals(Long.class)
                            || f.getType().equals(AtomicLong.class)) {
                            type = "bigint(20)";
                        } else if (f.getType().equals(Integer.class)
                                   || f.getType().equals(AtomicInteger.class)) {
                            type = "int";
                        } else if (f.getType().equals(BigDecimal.class)
                                   || f.getType().equals(Double.class)
                                   || f.getType().equals(Float.class)) {
                            type = "decimal(20,2)";
                        } else if (f.getType().equals(Boolean.class)
                                   || f.getType().equals(AtomicBoolean.class)) {
                            type = "char(5)";
                        } else if (List.class.isAssignableFrom(f.getType())
                                   || Map.class.isAssignableFrom(f.getType())) {
                            type = "text";
                        } else if (f.getType().equals(new byte[] {}.getClass())) {
                            type = "blob";
                        } else if (f.getType().equals(String.class)) {
                            type = null;
                        } else {
                            throw new RuntimeException("no match class :" + f.getType());
                        }


                        if (type == null) {
                            if (s.trim().endsWith("id")
                                || s.trim().endsWith("by")) {
                                type = "char(20)";
                            } else if (s.trim().endsWith("name")) {
                                type = "varchar(200)";
                            } else if (s.trim().endsWith("date")) {
                                type = "char(8)";
                            } else if (s.trim().endsWith("time")
                                       ||s.trim().endsWith("value")
                                       ||s.trim().endsWith("num")
                                       ||s.trim().endsWith("count")) {
                                type = "char(20)";
                            } else if (s.trim().endsWith("status")
                                       || s.trim().endsWith("type")
                                       || s.trim().endsWith("source")
                                       ||s.trim().endsWith("result")||s.trim().endsWith("code")||s.trim().endsWith("level")) {
                                type = "char(6)";
                            } else if (s.trim().endsWith("rule")
                                       || s.trim().endsWith("desc")
                                       || s.trim().endsWith("reason")
                                       ||s.trim().endsWith("msg")
                                       ||s.trim().endsWith("info")||s.trim().endsWith("route")) {
                                type = "text";
                            } else if (s.startsWith("backup")) {
                                type = "varchar(200)";
                            } else if(s.trim().endsWith("phone")){
                                type="char(16)";
                            }else {
                                //                                    System.out.println(s);
                                type = "varchar(200)";
                            }
                        }

                        sql.append(String.format("%-20s %-20s %-20s\n",s,type ,comment));
                    } catch (Exception e1) {
                        System.err.println(clazz.getName() + ":" + s + ":" + type + ":" + comment);
                    }
                });
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(");\n");
        System.out.println(sql);
    }

    private static boolean isPrime(int num){
        double factor = Math.sqrt(num);
        for (int i = 2; i < factor; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test22(){
        ExCode code=new ExCode();
        code.setP1((ConstConfig.BIGPRIMES.get(code.hashCode()%ConstConfig.BIGPRIMES.size())));
        code.setP2((ConstConfig.BIGPRIMES.get(code.getP1().hashCode()%ConstConfig.BIGPRIMES.size())));
        code.setPower(ConstConfig.POWERS.get(code.getP2().hashCode()%ConstConfig.POWERS.size()));
//        code.setN(new BigInteger((long)code.getP1()* code.getP2()+""));
        code.setVarProto(new AtomicLong(ConstConfig.CODE_VAR_START));

        System.out.println(code);


        Stream.generate(code::generateCode).limit(10).forEach(System.out::println);
    }

}
