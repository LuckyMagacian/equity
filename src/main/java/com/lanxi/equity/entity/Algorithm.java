package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lanxi.equity.assist.Comment;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 串码算法
 *
 * @author yangyuanjian created in 2018/2/6 11:16
 */
@Comment("串码算法")
@Deprecated
public class Algorithm extends OrgaDeptAct {

    @TableId("algo_id")
    @Comment("算法数据编号")
    @Pattern(regexp = "[0-9]{18}", message = "算法编号必须位18位数字")
    private String algoId;

    @Comment("大质数票p1")
    private Integer p1;

    @Comment("大质数p2")
    private Integer p2;

    @Comment("权值")
    private Integer power;

    @Comment("大质数乘积")
    private BigDecimal n;

    @Comment("串码算法变量")
    private volatile AtomicLong var;


    public String getAlgoId() {
        return algoId;
    }

    public void setAlgoId(String algoId) {
        this.algoId = algoId;
    }

    public Integer getP1() {
        return p1;
    }

    public void setP1(Integer p1) {
        this.p1 = p1;
    }

    public Integer getP2() {
        return p2;
    }

    public void setP2(Integer p2) {
        this.p2 = p2;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public BigDecimal getN() {
        return n;
    }

    public void setN(BigDecimal n) {
        this.n = n;
    }

    public AtomicLong getVar() {
        return var;
    }

    public void setVar(AtomicLong var) {
        this.var = var;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("Algorithm{");
        sb.append("algoId='").append(algoId).append('\'');
        sb.append(", p1=").append(p1);
        sb.append(", p2=").append(p2);
        sb.append(", power=").append(power);
        sb.append(", n=").append(n);
        sb.append(", var=").append(var);
        sb.append(", orgaId='").append(orgaId).append('\'');
        sb.append(", orgaName='").append(orgaName).append('\'');
        sb.append(", deptId='").append(deptId).append('\'');
        //        sb.append(", orgaDeptId='").append(orgaDeptId).append('\'');
        sb.append(", deptName='").append(deptName).append('\'');
        sb.append(", actId='").append(actId).append('\'');
        //        sb.append(", orgaActId='").append(orgaActId).append('\'');
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

    @Override protected Serializable pkVal() {
        return this.algoId;
    }
}
