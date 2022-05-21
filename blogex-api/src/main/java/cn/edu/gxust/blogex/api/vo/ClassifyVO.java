package cn.edu.gxust.blogex.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022/3/12 11:49
 */
public class ClassifyVO {

    @ApiModelProperty(value = "id", name = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "分类名", name = "classifyName")
    private String classifyName;

    @ApiModelProperty(value = "该分类被多少文章引用", name = "refCount")
    private Integer refCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期", name = "createTime")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期", name = "updateTime")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Integer getRefCount() {
        return refCount;
    }

    public void setRefCount(Integer refCount) {
        this.refCount = refCount;
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
        return "ClassifyVO{" +
                "id=" + id +
                ", classifyName='" + classifyName + '\'' +
                ", refCount=" + refCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
