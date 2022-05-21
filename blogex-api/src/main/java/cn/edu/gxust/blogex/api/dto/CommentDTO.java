package cn.edu.gxust.blogex.api.dto;

import cn.edu.gxust.blogex.common.annotation.EnumValid;
import cn.edu.gxust.blogex.common.enums.PageTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author zhaoyijie
 * @since 2022/3/18 22:39
 */
public class CommentDTO {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    @NotNull(message = "articleId不能为空")
    @ApiModelProperty(value = "文章id", name = "文章id", example = "2")
    private Integer articleId;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", name = "userName", example = "贾克斯")
    private String userName;

    @Email(message = "邮箱格式有误")
    @ApiModelProperty(value = "用户的邮箱", name = "userEmail", example = "747897928@qq.com")
    private String userEmail;

    /*@URL(message = "无效url")*/
    @ApiModelProperty(value = "用户头像url", name = "userAvatarUrl", example = "https://q1.qlogo.cn/g?b=qq&nk=747897928&s=640")
    private String userAvatarUrl;

    @NotBlank(message = "评论内容不能为空")
    @Size(message = "评论内容需要在1-500个字符内", min = 1, max = 500)
    @ApiModelProperty(value = "评论内容", name = "commentContent", example = "人生一世，草木一秋，皆是过程。美与可爱，有心皆懂")
    private String commentContent;

    @ApiModelProperty(value = "父评论id", name = "parentId", example = "1")
    private Integer parentId;

    @EnumValid(message = "页面类型枚举值不正确", target = PageTypeEnum.class)
    @ApiModelProperty(value = "页面类型，0->文章评论，1->关于我评论，2->友联评论，默认是0文章评论")
    private Integer pageType;

    public CommentDTO() {
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

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public Integer getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAvatarUrl='" + userAvatarUrl + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", parentId=" + parentId +
                ", pageType=" + pageType +
                '}';
    }
}
