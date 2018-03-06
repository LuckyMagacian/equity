package com.lanxi.equity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.lanxi.equity.assist.Comment;
import com.lanxi.equity.assist.HibernateValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.*;
import java.nio.file.Files;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 商品图片
 *
 * @author yangyuanjian created in 2018/2/5 19:52
 */
@Comment("商品图片")
public class CommPicture extends Model<CommPicture> {

    @TableId("comm_id")
    @NotNull(message = "商品编号不能为Null",groups = HibernateValidator.Insert.class)
    @Comment("商品编号")
    @Pattern(regexp = "([0-9]{18})|([0-9]{4})", message = "编号必须位18位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String commId;

    @Comment("最小图")
    private byte[] ssPic;

    @Comment("小图")
    private byte[] sPic;

    @Comment("中图")
    private byte[] mPic;

    @Comment("大图")
    private byte[] lPic;

    @Comment("最大图")
    private byte[] llPic;

    @Comment("添加日期")
    @NotNull(message = "添加日期不能为Null",groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-9]{8}", message = "日期必须为8位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })
    private String addDate;

    @Comment("添加时间")
    @NotNull(message = "添加时间不能为Null",groups = HibernateValidator.Insert.class)
    @Pattern(regexp = "[0-6]{8}", message = "时间必须为6位数字", groups = {
            HibernateValidator.Insert.class,
            HibernateValidator.Update.class,
            HibernateValidator.AsArg.class
    })

    private String addTime;

    @Comment("添加者")
    private String addBy;

    @Comment("备用字段1")
    private String backup1;

    @Comment("备用字段2")
    private String backup2;

    @Comment("图片备用3")
    private byte[] backup3;

    @Comment("图片备用4")
    private byte[] backup4;

    @Comment("乐观锁字段")
    @Version
    @NotNull(message = "乐观锁字段不能为Null",groups = HibernateValidator.Insert.class)
    private Long version = 1l;

    public String getCommId() {
        return commId;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }

    public byte[] getSsPic() {
        return ssPic;
    }


    private static final BiFunction<String, byte[], File> byteToFile = (String fileName, byte[] bytes) -> {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        FileOutputStream fout = null;
        try {
            File file = new File(fileName);
            fout = new FileOutputStream(file);
            fout.write(bytes);
            return file;
        } catch (Exception e) {
            throw new RuntimeException("exception occured when convert pic from byte[] to File !", e);
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (Exception e) {
                    throw new RuntimeException("exception occured when close FileOutputStream !", e);
                }
            }
        }
    };

    private static final Function<File, byte[]> fileToByte = (file) -> {
        if (file == null) {
            return null;
        }
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException("exception occured when convert pic from File to byte[] !", e);
        }
    };

    public void setSsPic(byte[] ssPic) {
        this.ssPic = ssPic;
    }

    public File getSsPicFile() {
        return byteToFile.apply(this.commId + "_ss.jpg", this.ssPic);
    }

    public void setSsPicFile(File ssPic) {
        setSsPic(fileToByte.apply(ssPic));
    }


    public byte[] getSPic() {
        return sPic;
    }

    public void setsPic(byte[] sPic) {
        this.sPic = sPic;
    }

    public File getsPicFile() {
        return byteToFile.apply(this.commId + "_s.jpg", this.sPic);
    }

    public void setsPicFile(File sPic) {
        setsPic(fileToByte.apply(sPic));
    }


    public byte[] getmPic() {
        return mPic;
    }

    public void setmPic(byte[] mPic) {
        this.mPic = mPic;
    }

    public File getmPicFile() {
        return byteToFile.apply(this.commId + "_m.jpg", this.mPic);
    }

    public void setmPicFile(File sPic) {
        setmPic(fileToByte.apply(sPic));
    }

    public byte[] getlPic() {
        return lPic;
    }

    public void setlPic(byte[] lPic) {
        this.lPic = lPic;
    }

    public File getlPicFile() {
        return byteToFile.apply(this.commId + "_l.jpg", this.lPic);
    }

    public void setlPicFile(File sPic) {
        setlPic(fileToByte.apply(sPic));
    }


    public byte[] getLlPic() {
        return llPic;
    }

    public void setLlPic(byte[] llPic) {
        this.llPic = llPic;
    }

    public File getLlPicFile() {
        return byteToFile.apply(this.commId + "_ll.jpg", this.llPic);
    }

    public void setLlPicFile(File sPic) {
        setLlPic(fileToByte.apply(sPic));
    }


    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getBackup1() {
        return backup1;
    }

    public void setBackup1(String backup1) {
        this.backup1 = backup1;
    }

    public String getBackup2() {
        return backup2;
    }

    public void setBackup2(String backup2) {
        this.backup2 = backup2;
    }

    public byte[] getBackup3() {
        return backup3;
    }

    public void setBackup3(byte[] backup3) {
        this.backup3 = backup3;
    }

    public byte[] getBackup4() {
        return backup4;
    }

    public void setBackup4(byte[] backup4) {
        this.backup4 = backup4;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override protected Serializable pkVal() {
        return this.commId;
    }

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("CommPicture{");
        sb.append("commId='").append(commId).append('\'');
        sb.append(", ssPic=").append(ssPic);
        sb.append(", sPic=").append(sPic);
        sb.append(", mPic=").append(mPic);
        sb.append(", lPic=").append(lPic);
        sb.append(", llPic=").append(llPic);
        sb.append(", addDate='").append(addDate).append('\'');
        sb.append(", addTime='").append(addTime).append('\'');
        sb.append(", addBy='").append(addBy).append('\'');
        sb.append(", backup1='").append(backup1).append('\'');
        sb.append(", backup2='").append(backup2).append('\'');
        sb.append(", backup3=").append(backup3);
        sb.append(", backup4=").append(backup4);
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
