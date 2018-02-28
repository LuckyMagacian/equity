package com.lanxi.equity.config;

import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.entity.ExCode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author yangyuanjian created in 2018/2/6 19:06
 */
public abstract class ConstConfig {


    //--------------------------------------------------变量赋值开始

    @Comment("配置文件路径")
    public static final String STATIC_CONFIG_PATH = "classpath:properties";

    @Comment("调试开关")
    public static final boolean DEBUG = true;

    @Comment("开发开关")
    public static final boolean DEVELOP = true;

    @Comment("字符集")
    public static final String CHARSET = "utf-8";

    @Comment("rsa私钥")
    public static final String PRI_KEY = "";

    @Comment("rsa公钥")
    public static final String PUB_KEY = "";

    @Comment("有效期-永久")
    public static final int VALIDATE_FOREVER = -1;

    @Comment("有效期-永久")
    public static final long VALIDATE_FOREVER_L = -1L;

    @Comment("测试用签名密钥")
    public static final String TEST_SIGN_KEY = "301ed4c27bdfb7785b1c80de33b323e6";

    @Comment("测试用兑换码原型编号")
    public static final String TEST_CODE_ID = 100000000000000000L + "";

    @Comment("兑换码长度限制条件")
    public static final Function<ExCode, Integer> CODE_LENGTH_LIMIT = e -> (e.getP1() + "").length() + (e.getP2() + "").length();

    @Comment("权益过期日期值默认")
    public static final int EQUITY_BE_EXPIRE_DAYS = 3;

    //--------------------------------------------------变量赋值结束


    //--------------------------------------------------功能码区开始

    @Comment("功能代码-兑换码实例兑换权益")
    public static final String FUN_ID_EXCODE_INSTANCE_EXCHANGE = "1001";

    @Comment("功能代码-生成兑换码")
    public static final String FUN_ID_GENERATE_EXCODE_INSTANCE = "1002";

    @Comment("功能代码列表")
    public static final List<String> FUNIDS = new ArrayList<>();

    //功能列表初始化
    static {
        FUNIDS.add(FUN_ID_EXCODE_INSTANCE_EXCHANGE);
        FUNIDS.add(FUN_ID_GENERATE_EXCODE_INSTANCE);
    }


    //--------------------------------------------------功能码区结束


    //--------------------------------------------------兑换码算法相关开始

    @Comment("质数检测方法")
    private static boolean isPrime(int num) {
        double factor = Math.sqrt(num);
        for (int i = 2; i < factor; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Comment("乘方值列表")
    public static final List<Integer> POWERS = new ArrayList<>();

    @Comment("大质数列表")
    public static final List<Integer> BIGPRIMES = new ArrayList<>();

    @Comment("兑换码算法变量起始")
    public static final long CODE_VAR_START = 2L;

    //兑换码生成所需相关参数初始化
    static {
        List<Integer> primes = IntStream.range(1, 100000).filter(ConstConfig::isPrime).mapToObj(e -> Integer.valueOf(e)).collect(Collectors.toList());
        POWERS.addAll(primes.stream().filter(e -> e > 200 && e < 400).collect(Collectors.toList()));
        BIGPRIMES.addAll(primes.stream().filter(e -> e > 33334 && e < 99999).collect(Collectors.toList()));
    }

    //--------------------------------------------------兑换码算法相关结束

}
