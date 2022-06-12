package cn.edu.gxust.blogex.dao.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.util.List;

/**
 * 文章
 *
 * @author zhaoyijie
 * @since 2021/7/23 12:07
 */
@TableName(value = "t_article")
public class ArticlePO extends Model<ArticlePO> {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     *
     */
    private String snippet;

    /**
     * 分类id
     * 
     * @see ClassifyPO#getId()
     */
    private Integer classifyId;

    /**
     * 分类名，冗余字段，用于加快检索效率
     */
    private String classifyName;

    /**
     * 评论数，冗余字段，用于加快检索效率
     */
    private Integer sumComment;

    /**
     * 标签集合
     */
    @TableField(exist = false)
    private List<TagPO> tagList;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 预览图片链接
     */
    private String reviewImgUrl;

    /**
     * 阅览量
     */
    private Integer viewCount;

    /**
     * 点赞个数
     */
    private Integer supportCount;

    /**
     * @see cn.edu.gxust.blogex.common.enums.CommentStatus
     */
    private Integer contentType;

    /**
     * 优先级，用于排序
     */
    private Integer priority;

    /**
     * @see cn.edu.gxust.blogex.common.enums.ArticleStatus
     */
    private Integer articleStatus;

    /**
     * @see cn.edu.gxust.blogex.common.enums.CommentStatus
     */
    private Integer commentStatus;

    /**
     * @see cn.edu.gxust.blogex.common.enums.PostType
     */
    private Integer postType;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新日期
     */
    @TableField(update = "now()", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 版本，乐观锁
     */
    @Version
    private Long version;

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

    public List<TagPO> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagPO> tagList) {
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ArticlePO{" +
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
                ", version=" + version +
                '}';
    }
}
