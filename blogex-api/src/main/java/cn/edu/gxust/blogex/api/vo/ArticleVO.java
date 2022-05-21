package cn.edu.gxust.blogex.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/12 11:42
 */
public class ArticleVO {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "标题", name = "title")
    private String title;

    @ApiModelProperty(value = "文章的摘要", name = "snippet")
    private String snippet;

    @ApiModelProperty(value = "文章分类id")
    private Integer classifyId;

    @ApiModelProperty(value = "分类名，冗余字段，用于加快检索效率")
    private String classifyName;

    @ApiModelProperty(value = "评论数，冗余字段，用于加快检索效率")
    private Integer sumComment;

    @ApiModelProperty(value = "标签集合", name = "tagList")
    private List<TagVO> tagList;

    @ApiModelProperty(value = "内容", name = "content")
    private String content;

    @ApiModelProperty(value = "预览图片url", name = "reviewImgUrl")
    private String reviewImgUrl;

    @ApiModelProperty(value = "阅览量", name = "viewCount")
    private Integer viewCount;

    @ApiModelProperty(value = "点赞量", name = "supportCount")
    private Integer supportCount;

    /**
     * @see cn.edu.gxust.blogex.common.enums.ContentType
     */
    @ApiModelProperty(value = "文章内容的类型，0->html,1->markdown", name = "contentType")
    private Integer contentType;

    @ApiModelProperty(value = "优先级，用于排序", name = "priority")
    private Integer priority;

    /**
     * @see cn.edu.gxust.blogex.common.enums.ArticleStatus
     */
    @ApiModelProperty(value = "文章状态，0->公开,1->草稿", name = "articleStatus")
    private Integer articleStatus;

    /**
     * @see cn.edu.gxust.blogex.common.enums.CommentStatus
     */
    @ApiModelProperty(value = "0->开启评论并自动处理,1->不开启评论，默认是0")
    private Integer commentStatus;

    /**
     * @see cn.edu.gxust.blogex.common.enums.PostType
     */
    @ApiModelProperty(value = "0->原创,1->转载,2->翻译", name = "postType")
    private Integer postType;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER) //写成long时间戳，避免ios里的js日期格式不兼容的情况发生
    @ApiModelProperty(value = "创建日期", name = "createTime")
    private Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER) //写成long时间戳，避免ios里的js日期格式不兼容的情况发生
    @ApiModelProperty(value = "更新日期", name = "updateTime")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Integer getSumComment() {
        return sumComment;
    }

    public void setSumComment(Integer sumComment) {
        this.sumComment = sumComment;
    }

    public List<TagVO> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagVO> tagList) {
        this.tagList = tagList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReviewImgUrl() {
        return reviewImgUrl;
    }

    public void setReviewImgUrl(String reviewImgUrl) {
        this.reviewImgUrl = reviewImgUrl;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(Integer supportCount) {
        this.supportCount = supportCount;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
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
        return "ArticleVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", snippet='" + snippet + '\'' +
                ", classifyId=" + classifyId +
                ", classifyName='" + classifyName + '\'' +
                ", sumComment=" + sumComment +
                ", tagList=" + tagList +
                ", content='" + content + '\'' +
                ", reviewImgUrl='" + reviewImgUrl + '\'' +
                ", viewCount=" + viewCount +
                ", supportCount=" + supportCount +
                ", contentType=" + contentType +
                ", priority=" + priority +
                ", articleStatus=" + articleStatus +
                ", commentStatus=" + commentStatus +
                ", postType=" + postType +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
