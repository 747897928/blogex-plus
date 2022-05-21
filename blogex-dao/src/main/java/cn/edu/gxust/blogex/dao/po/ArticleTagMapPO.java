package cn.edu.gxust.blogex.dao.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022/3/12 08:47
 */
@TableName(value = "t_article_tag_map")
public class ArticleTagMapPO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     * 
     * @see ArticlePO#getId()
     */
    private Integer articleId;

    /**
     * 标签id
     *
     * @see TagPO#getId()
     */
    private Integer tagId;

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

    public ArticleTagMapPO() {
    }

    private ArticleTagMapPO(Builder builder) {
        setId(builder.id);
        setArticleId(builder.articleId);
        setTagId(builder.tagId);
        setCreateTime(builder.createTime);
        setUpdateTime(builder.updateTime);
        setVersion(builder.version);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(ArticleTagMapPO copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.articleId = copy.getArticleId();
        builder.tagId = copy.getTagId();
        builder.createTime = copy.getCreateTime();
        builder.updateTime = copy.getUpdateTime();
        builder.version = copy.getVersion();
        return builder;
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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
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
        return "ArticleTagMapPO{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", tagId=" + tagId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                '}';
    }


    public static final class Builder {
        private Integer id;
        private Integer articleId;
        private Integer tagId;
        private Date createTime;
        private Date updateTime;
        private Long version;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder articleId(Integer val) {
            articleId = val;
            return this;
        }

        public Builder tagId(Integer val) {
            tagId = val;
            return this;
        }

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        public Builder updateTime(Date val) {
            updateTime = val;
            return this;
        }

        public Builder version(Long val) {
            version = val;
            return this;
        }

        public ArticleTagMapPO build() {
            return new ArticleTagMapPO(this);
        }
    }
}
