package cn.edu.gxust.blogex.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/18 22:54
 */
public class CommentVO {

    @ApiModelProperty(value = "id", name = "id", example = "2")
    private Integer id;

    @ApiModelProperty(value = "文章id", name = "文章id", example = "2")
    private Integer articleId;

    @ApiModelProperty(value = "用户名", name = "userName", example = "贾克斯")
    private String userName;

    @ApiModelProperty(value = "用户的邮箱", name = "userEmail", example = "747897928@qq.com")
    private String userEmail;

    @ApiModelProperty(value = "用户头像url", name = "userAvatarUrl", example = "https://q1.qlogo.cn/g?b=qq&nk=747897928&s=640")
    private String userAvatarUrl;

    @ApiModelProperty(value = "评论内容", name = "commentContent", example = "人生一世，草木一秋，皆是过程。美与可爱，有心皆懂")
    private String commentContent;

    @ApiModelProperty(value = "父评论id", name = "parentId", example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "评论的父层级id(上层级的评论id，引入这个字段的目的是为了让层级最多为2)", name = "parentId", example = "1")
    private Integer parentTierId;

    @ApiModelProperty(value = "用户ip", name = "userIp", example = "127.0.0.1")
    private String userIp;

    @ApiModelProperty(value = "用户os(mac,win,iphone,ipad,linux,android)", name = "userOs", example = "mac")
    private String userOs;

    @ApiModelProperty(value = "用户的浏览器名", name = "browserName", example = "Chrome")
    private String browserName;

    @ApiModelProperty(value = "用户浏览器标识", name = "UserAgent", example = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
    private String UserAgent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期", name = "createTime", example = "2022-03-18 23:03:33")
    private Date createTime;

    @ApiModelProperty(value = "子评论列表，回复评论", name = "childList")
    private List<CommentVO> childList;

    @ApiModelProperty(value = "页面类型，0->文章评论，1->关于我评论，2->友联评论，默认是0文章评论")
    private Integer pageType;

    public CommentVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentTierId() {
        return parentTierId;
    }

    public void setParentTierId(Integer parentTierId) {
        this.parentTierId = parentTierId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserOs() {
        return userOs;
    }

    public void setUserOs(String userOs) {
        this.userOs = userOs;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<CommentVO> getChildList() {
        return childList;
    }

    public void setChildList(List<CommentVO> childList) {
        this.childList = childList;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAvatarUrl='" + userAvatarUrl + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", parentId=" + parentId +
                ", parentTierId=" + parentTierId +
                ", userIp='" + userIp + '\'' +
                ", userOs='" + userOs + '\'' +
                ", browserName='" + browserName + '\'' +
                ", UserAgent='" + UserAgent + '\'' +
                ", createTime=" + createTime +
                ", childList=" + childList +
                ", pageType=" + pageType +
                '}';
    }
}
