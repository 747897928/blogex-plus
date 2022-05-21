package cn.edu.gxust.blogex.api.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
public class BlogLinkVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "友联名")
    private String linkName;

    @ApiModelProperty(value = "友联url")
    private String linkHref;

    @ApiModelProperty(value = "友联头像url")
    private String linkAvatarUrl;

    @ApiModelProperty(value = "描述")
    private String description;

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

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkHref() {
        return linkHref;
    }

    public void setLinkHref(String linkHref) {
        this.linkHref = linkHref;
    }

    public String getLinkAvatarUrl() {
        return linkAvatarUrl;
    }

    public void setLinkAvatarUrl(String linkAvatarUrl) {
        this.linkAvatarUrl = linkAvatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "BlogLinkVO{" +
                "id=" + id +
                ", linkName='" + linkName + '\'' +
                ", linkHref='" + linkHref + '\'' +
                ", linkAvatarUrl='" + linkAvatarUrl + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

