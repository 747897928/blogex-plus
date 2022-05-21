package cn.edu.gxust.blogex.api.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
public class BloggerInfoDTO {

    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id")
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @NotBlank(message = "头像url不能为空")
    @ApiModelProperty(value = "头像url")
    private String avatarUrl;

    @NotBlank(message = "个性签名不能为空")
    @ApiModelProperty(value = "个性签名")
    private String signature;

    @NotBlank(message = "githubUrl不能为空")
    @ApiModelProperty(value = "博主github或者gitee的主页")
    private String githubUrl;

    @ApiModelProperty(value = "(备案号)")
    private String recordNumber;

    @NotBlank(message = "博客的简介不能为空")
    @ApiModelProperty(value = "博客的简介")
    private String bloggerDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getBloggerDetail() {
        return bloggerDetail;
    }

    public void setBloggerDetail(String bloggerDetail) {
        this.bloggerDetail = bloggerDetail;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "BloggerInfoDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", signature='" + signature + '\'' +
                ", githubUrl='" + githubUrl + '\'' +
                ", recordNumber='" + recordNumber + '\'' +
                ", bloggerDetail='" + bloggerDetail + '\'' +
                '}';
    }
}

