package cn.edu.gxust.blogex.api.dto;

import cn.edu.gxust.blogex.common.annotation.EnumValid;
import cn.edu.gxust.blogex.common.enums.MailEnableEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022-05-15 12:40:55
 */
public class BlogSettingDTO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @NotBlank(message = "webDomain不能为空")
    @ApiModelProperty(value = "web网站的域名")
    private String webDomain;

    @EnumValid(message = "mailEnable枚举值不正确", target = MailEnableEnum.class)
    @ApiModelProperty(value = "是否开启邮件任务，0开启，1不开启")
    private Integer mailEnable;

    @NotBlank(message = "博客域名不能为空")
    @ApiModelProperty(value = "博客域名（入口前缀）用于大部分领域，比如拼接邮件回复链接")
    private String blogAddress;

    @NotBlank(message = "博主邮箱不能为空")
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
        return "BlogSettingDTO{" +
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

