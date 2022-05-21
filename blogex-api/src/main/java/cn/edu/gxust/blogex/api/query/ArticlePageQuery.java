package cn.edu.gxust.blogex.api.query;


import cn.edu.gxust.blogex.common.request.BasePageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022/3/19 18:18
 */
public class ArticlePageQuery extends BasePageQuery {

    @ApiModelProperty(value = "搜索关键字,为null则不模糊匹配")
    private String searchKey;

    @ApiModelProperty(value = "分类id，这个字段根据分类查询引用的文章时用到,为null则不把这个条件作为检索条件")
    private Integer classifyId;

    @ApiModelProperty(value = "标签id，这个字段根据标签查询引用的文章时用到,为null则不把这个条件作为检索条件")
    private Integer tagId;

    @ApiModelProperty(value = "文章内容的类型，0->html,1->markdown，为null则不把这个条件作为检索条件", name = "contentType")
    private Integer contentType;

    @ApiModelProperty(value = "状态，0->发布,1->草稿,为null则不把这个条件作为检索条件", name = "articleStatus", example = "0")
    private Integer articleStatus;

    @ApiModelProperty(value = "0->开启评论并自动处理,1->不开启评论，为null则不把这个条件作为检索条件", name = "commentStatus", example = "0")
    private Integer commentStatus;

    @ApiModelProperty(value = "0->原创,1->转载,2->翻译，为null则不把这个条件作为检索条件", name = "postType", example = "0")
    private Integer postType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "起止时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ArticleQuery{" +
                "searchKey='" + searchKey + '\'' +
                ", classifyId=" + classifyId +
                ", tagId=" + tagId +
                ", contentType=" + contentType +
                ", articleStatus=" + articleStatus +
                ", commentStatus=" + commentStatus +
                ", postType=" + postType +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
