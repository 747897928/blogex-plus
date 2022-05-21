package cn.edu.gxust.blogex.api.dto;

import cn.edu.gxust.blogex.common.annotation.EnumValid;
import cn.edu.gxust.blogex.common.enums.ArticleStatus;
import cn.edu.gxust.blogex.common.enums.CommentStatus;
import cn.edu.gxust.blogex.common.enums.ContentType;
import cn.edu.gxust.blogex.common.enums.PostType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 文章
 *
 * @author zhaoyijie
 * @since 2022/2/26 11:46
 */
public class ArticleDTO {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "标题", name = "title", required = true, example = "提升自己比多社交更重要")
    private String title;

    @ApiModelProperty(value = "文章的摘要", name = "snippet", example = "愿每一个骑士，都能找到值得自己守护的公主。")
    private String snippet;

    /**
     * @see cn.edu.gxust.blogex.api.vo.CommentVO#getId()
     */
    @ApiModelProperty(value = "分类的id", name = "classifyId", example = "1", required = true)
    private Integer classifyId;

    /**
     * @see cn.edu.gxust.blogex.api.dto.TagDTO#getId()
     */
    @ApiModelProperty(value = "标签id的集合", name = "tagIdList", required = true)
    private List<Integer> tagIdList;

    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty(value = "内容", name = "content", required = true, example = "成长的标志就是懂得克制自己。克制自己的情绪，克制自己的表演欲，甚至克制自己的喜欢....")
    private String content;

    @ApiModelProperty(value = "预览图片url", name = "reviewImgUrl", example = "https://gitee.com/guangxikejidaxue/aquarius-wizards-chart-bed/raw/master/image/20210718-1.png")
    private String reviewImgUrl;

    @ApiModelProperty(value = "阅览量", name = "viewCount", example = "0")
    private Integer viewCount;

    @ApiModelProperty(value = "点赞量", name = "supportCount", example = "0")
    private Integer supportCount;

    @EnumValid(message = "contentType枚举值不正确", target = ContentType.class)
    @ApiModelProperty(value = "文章内容的类型，0->html,1->markdown", name = "contentType", required = true, example = "0")
    private Integer contentType;

    @Max(message = "priority不能大于100", value = 100L)
    @Min(message = "priority不能小于于1", value = 1L)
    @ApiModelProperty(value = "优先级，用于排序", name = "priority", example = "1", required = true)
    private Integer priority;

    @EnumValid(message = "articleStatus枚举值不正确", target = ArticleStatus.class)
    @ApiModelProperty(value = "状态，0->公开,1->草稿", name = "articleStatus", example = "0", required = true)
    private Integer articleStatus;

    @EnumValid(message = "commentStatus枚举值不正确", target = CommentStatus.class)
    @ApiModelProperty(value = "0->开启评论并自动处理,1->不开启评论，默认是0", name = "commentStatus", example = "0")
    private Integer commentStatus;

    @EnumValid(message = "postType枚举值不正确", target = PostType.class)
    @ApiModelProperty(value = "0->原创,1->转载,2->翻译", name = "postType", required = true, example = "0")
    private Integer postType;

    public ArticleDTO() {
    }

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

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
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

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", snippet='" + snippet + '\'' +
                ", classifyId=" + classifyId +
                ", tagIdList=" + tagIdList +
                ", content='" + content + '\'' +
                ", reviewImgUrl='" + reviewImgUrl + '\'' +
                ", viewCount=" + viewCount +
                ", supportCount=" + supportCount +
                ", contentType=" + contentType +
                ", priority=" + priority +
                ", articleStatus=" + articleStatus +
                ", commentStatus=" + commentStatus +
                ", postType=" + postType +
                '}';
    }
}
