package cn.edu.gxust.blogex.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022-05-15 12:40:55
 */
public class BlogSettingVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "后端接口的域名")
    private String webDomain;

    @ApiModelProperty(value = "是否开启邮件任务，0开启，1不开启")
    private Integer mailEnable;

    @ApiModelProperty(value = "博客域名(前端域名,博客入口地址)用于大部分领域，比如拼接邮件回复链接")
    private String blogAddress;

    @ApiModelProperty(value = "博主邮箱，用于接受备份文件的邮箱")
    private String blogMailAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
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
        return "BlogSettingVO{" +
                "id=" + id +
                ", webDomain='" + webDomain + '\'' +
                ", mailEnable=" + mailEnable +
                ", blogAddress='" + blogAddress + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", blogMailAddress='" + blogMailAddress + '\'' +
                '}';
    }
}

