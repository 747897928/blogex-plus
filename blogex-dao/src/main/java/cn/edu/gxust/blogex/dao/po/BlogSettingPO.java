package cn.edu.gxust.blogex.dao.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022-05-15 12:40:54
 */
@TableName(value = "t_blog_setting")
public class BlogSettingPO extends Model<BlogSettingPO> {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * web网站的域名
     */
    private String webDomain;

    /**
     * 是否开启邮件任务，0开启，1不开启
     */
    private Integer mailEnable;

    /**
     * 博客域名（入口前缀）用于大部分领域，比如拼接邮件回复链接
     */
    private String blogAddress;

    /**
     * 博主邮箱，用于接受备份文件的邮箱
     */
    private String blogMailAddress;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(update = "now()", fill = FieldFill.UPDATE)
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWebDomain() {
        return webDomain;
    }

    public void setWebDomain(String webDomain) {
        this.webDomain = webDomain;
    }

    public Integer getMailEnable() {
        return mailEnable;
    }

    public void setMailEnable(Integer mailEnable) {
        this.mailEnable = mailEnable;
    }

    public String getBlogAddress() {
        return blogAddress;
    }

    public void setBlogAddress(String blogAddress) {
        this.blogAddress = blogAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBlogMailAddress() {
        return blogMailAddress;
    }

    public void setBlogMailAddress(String blogMailAddress) {
        this.blogMailAddress = blogMailAddress;
    }

    @Override
    public String toString() {
        return "BlogSettingPO{" +
                "id=" + id +
                ", webDomain='" + webDomain + '\'' +
                ", mailEnable=" + mailEnable +
                ", blogAddress='" + blogAddress + '\'' +
                ", blogMailAddress='" + blogMailAddress + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}

