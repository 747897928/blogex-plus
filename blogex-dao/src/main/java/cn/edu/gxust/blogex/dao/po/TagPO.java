package cn.edu.gxust.blogex.dao.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

/**
 * 标签
 *
 * @author zhaoyijie
 * @since 2022/3/11 23:33
 */
@TableName(value = "t_tag")
public class TagPO extends Model<TagPO> {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 该标签被多少文章引用
     */
    @TableField(exist = false, value = "ref_count")
    private Integer refCount;

    /**
     * 文章id
     *
     * @see ArticlePO#getId()
     */
    @TableField(exist = false)
    private Integer articleId;

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

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    public Integer getArticleId() {
        return articleId;
    }

    public Integer getRefCount() {
        return refCount;
    }

    public void setRefCount(Integer refCount) {
        this.refCount = refCount;
    }

    @Override
    public String toString() {
        return "TagPO{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                ", refCount=" + refCount +
                ", articleId=" + articleId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version=" + version +
                '}';
    }
}
