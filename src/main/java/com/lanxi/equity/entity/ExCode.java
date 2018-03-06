package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;
import com.lanxi.equity.assist.InRange;
import com.lanxi.equity.config.CodeStatus;
import com.lanxi.equity.config.ConstConfig;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 兑换码
 *
 * @author yangyuanjian created in 2018/2/6 13:50
 */
@Comment("兑换码")
public class ExCode extends OrgaDeptAct {

    @TableId("code_id")
    @Comment("兑换码编号")
    @NotNull(message = "兑换码编号不能为null", groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{18}", message = "兑换码编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String codeId;

    @Comment("兑换码状态")
    @InRange(clazz = CodeStatus.class, message = "兑换码状态必须是在CodeStatus中声明的值", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    } )
    private String codeStatus;

    @Comment("兑换码值")
    @Pattern(regexp = "[0-9]{1,3}", message = "兑换码的权益值必须是1到999之间的整数", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private Integer value;

    @Comment("有效期")
    private String validate;

    //    @TableId("algo_id")
    //    @Comment("算法数据编号")
    //    @Pattern(regexp = "[0-9]{18}", message = "算法编号必须位18位数字")
    //    private String algoId;

    @Comment("大质数p1")
    @NotNull(message = "大质数p1不能为null", groups = HibernateValidator.Insert.class)
    private Integer p1;

    @Comment("大质数p2")
    @NotNull(message = "大质数p2不能为null", groups = HibernateValidator.Insert.class)
    private Integer p2;

    @Comment("权值")
    @NotNull(message = "权值不能为null", groups = HibernateValidator.Insert.class)
    private Integer power;

    @Comment("大质数乘积")
    @NotNull(message = "大质数乘积不能为null", groups = HibernateValidator.Insert.class)
    private BigInteger n;

    @Comment("串码算法变量")
    @NotNull(message = "算法变量不能为null", groups = HibernateValidator.Insert.class)
    private volatile AtomicLong var;

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override protected Serializable pkVal() {
        return this.codeId;
    }


    public Integer getP1() {
        return p1;
    }

    public void setP1(Integer p1) {
        this.p1 = p1;
        if (p2 != null) {
            this.n = new BigInteger(((long) p1) * p2 + "");
        }
    }

    public Integer getP2() {
        return p2;
    }

    public void setP2(Integer p2) {
        this.p2 = p2;
        if (p1 != null) {
            this.n = new BigInteger(((long) p1) * p2 + "");
        }
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public BigInteger getN() {
        return n;
    }

    //    public void setN(BigInteger n) {
    //        this.n = n;
    //    }

    public AtomicLong getVarProto() {
        return var;
    }

    public void setVarProto(AtomicLong var) {
        this.var = var;
    }

    public Long getVar() {
        return Optional.ofNullable(this.var).map(e -> e != null ? e.longValue() : null).orElse(null);
    }

    public void setVar(Long var) {
        if (this.var == null) {
            this.var = new AtomicLong(var);
        } else {
            this.var.set(var);
        }
    }

    //将数据库更新提出减少数据库连接次数
    public synchronized long generateCode() {
        long code = generateCodeInvoke();
        this.updateById();
        return code;
    }

    private long generateCodeInvoke() {
        BigInteger bVar = new BigInteger(var.addAndGet(1) + "");
        BigInteger code = bVar.pow(power).remainder(this.n);
        //串码长度需要与两个大质数以字符串形式的相加的的长度一致
        long lcode = (code.longValue() + "").length() != ConstConfig.CODE_LENGTH_LIMIT.apply(this) ? generateCode() : code.longValue();
        return lcode;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("ExCode{");
        sb.append("codeId='").append(codeId).append('\'');
        sb.append(", codeStatus='").append(codeStatus).append('\'');
        sb.append(", value=").append(value);
        sb.append(", validate='").append(validate).append('\'');
        sb.append(", p1=").append(p1);
        sb.append(", p2=").append(p2);
        sb.append(", power=").append(power);
        sb.append(", n=").append(n);
        sb.append(", var=").append(var);
        sb.append(", orgaId='").append(orgaId).append('\'');
        sb.append(", orgaName='").append(orgaName).append('\'');
        sb.append(", deptId='").append(deptId).append('\'');
        sb.append(", deptName='").append(deptName).append('\'');
        sb.append(", actId='").append(actId).append('\'');
        sb.append(", actName='").append(actName).append('\'');
        sb.append(", addDate='").append(addDate).append('\'');
        sb.append(", addTime='").append(addTime).append('\'');
        sb.append(", addByName='").append(addByName).append('\'');
        sb.append(", addById='").append(addById).append('\'');
        sb.append(", backup1='").append(backup1).append('\'');
        sb.append(", backup2='").append(backup2).append('\'');
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
