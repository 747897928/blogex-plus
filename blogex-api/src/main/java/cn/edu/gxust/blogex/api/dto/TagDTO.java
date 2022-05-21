package cn.edu.gxust.blogex.api.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 标签
 *
 * @author zhaoyijie
 * @since 2022/3/11 23:33
 */
public class TagDTO {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "标签名", name = "tagName")
    private String tagName;

    @ApiModelProperty(value = "创建日期", name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "更新日期", name = "updateTime")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "TagDTO{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
