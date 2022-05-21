package cn.edu.gxust.blogex.api.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
public class BlogLinkDTO {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    @NotNull(message = "友联名不能为空")
    @ApiModelProperty(value = "友联名")
    private String linkName;

    @URL(message = "友联链接格式不正确")
    @ApiModelProperty(value = "友联url")
    private String linkHref;

    @ApiModelProperty(value = "友联头像url")
    private String linkAvatarUrl;

    @ApiModelProperty(value = "描述")
    private String description;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BlogLinkDTO{" +
                "id=" + id +
                ", linkName='" + linkName + '\'' +
                ", linkHref='" + linkHref + '\'' +
                ", linkAvatarUrl='" + linkAvatarUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

