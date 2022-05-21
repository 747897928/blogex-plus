package cn.edu.gxust.blogex.dao.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022/3/18 09:41
 */
@TableName(value = "t_comment")
public class CommentPO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户的邮箱
     */
    private String userEmail;

    /**
     * 用户头像url
     */
    private String userAvatarUrl;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 父评论id
     */
    private Integer parentId;

    /**
     * 评论的父层级id(上层级的评论id，引入这个字段的目的是为了让层级最多为2)
     */
    private Integer parentTierId;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 用户os
     */
    private String userOs;

    /**
     * 用户的浏览器名
     */
    private String browserName;

    /**
     * 用户浏览器标识
     */
    private String userAgent;

    /**
     * 页面类型，0->文章评论，1->关于我评论，2->友联评论，默认是0文章评论
     *
     * @see cn.edu.gxust.blogex.common.enums.PageTypeEnum
     */
    private Integer pageType;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    public CommentPO() {
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
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    @Override
    public String toString() {
        return "CommentPO{" +
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
                ", userAgent='" + userAgent + '\'' +
                ", pageType=" + pageType +
                ", createTime=" + createTime +
                '}';
    }
}
