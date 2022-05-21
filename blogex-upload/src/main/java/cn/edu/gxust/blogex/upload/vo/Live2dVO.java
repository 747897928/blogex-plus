package cn.edu.gxust.blogex.upload.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022/3/29 09:53
 */
public class Live2dVO {

    @ApiModelProperty(value = "模型id")
    private Integer id;

    @ApiModelProperty(value = "模型名", required = true)
    private String modelName;

    @ApiModelProperty(value = "模型json路径", required = true)
    private String modelJsonPath;

    @ApiModelProperty(value = "模型预览图片路径", required = true)
    private String modelImagePath;

    @ApiModelProperty(value = "模型背景图片图片路径")
    private String backgroundPath;

    @ApiModelProperty(value = "x", required = true)
    private Double x;

    @ApiModelProperty(value = "y", required = true)
    private Double y;

    @ApiModelProperty(value = "模型缩放", required = true)
    private Double scale;

    @ApiModelProperty(value = "模型宽", required = true)
    private Integer width;

    @ApiModelProperty(value = "模型高", required = true)
    private Integer height;

    @ApiModelProperty(value = "锚点，以画布中心下方为中心点x（单位：倍）", required = true)
    private Double anchorx;

    @ApiModelProperty(value = "锚点，以画布中心下方为中心点y（单位：倍）", required = true)
    private Double anchory;

    @ApiModelProperty(value = "是否在前台展示的标记，0不展示，1展示，用于下架模型")
    private Integer showMark;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "模型在文件系统的路径，用于下载和删除的时候找到这个路径")
    private String baseFilePath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelJsonPath() {
        return modelJsonPath;
    }

    public void setModelJsonPath(String modelJsonPath) {
        this.modelJsonPath = modelJsonPath;
    }

    public String getModelImagePath() {
        return modelImagePath;
    }

    public void setModelImagePath(String modelImagePath) {
        this.modelImagePath = modelImagePath;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getAnchorx() {
        return anchorx;
    }

    public void setAnchorx(Double anchorx) {
        this.anchorx = anchorx;
    }

    public Double getAnchory() {
        return anchory;
    }

    public void setAnchory(Double anchory) {
        this.anchory = anchory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getShowMark() {
        return showMark;
    }

    public void setShowMark(Integer showMark) {
        this.showMark = showMark;
    }

    public String getBaseFilePath() {
        return baseFilePath;
    }

    public void setBaseFilePath(String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    @Override
    public String toString() {
        return "Live2dVO{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", modelJsonPath='" + modelJsonPath + '\'' +
                ", modelImagePath='" + modelImagePath + '\'' +
                ", backgroundPath='" + backgroundPath + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", scale=" + scale +
                ", width=" + width +
                ", height=" + height +
                ", anchorx=" + anchorx +
                ", anchory=" + anchory +
                ", showMark=" + showMark +
                ", description='" + description + '\'' +
                ", baseFilePath='" + baseFilePath + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
