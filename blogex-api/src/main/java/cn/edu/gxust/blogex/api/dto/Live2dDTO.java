package cn.edu.gxust.blogex.api.dto;

import cn.edu.gxust.blogex.common.annotation.EnumValid;
import cn.edu.gxust.blogex.common.enums.ShowMarkEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhaoyijie
 * @since 2022/3/29 09:53
 */
public class Live2dDTO {

    @ApiModelProperty(value = "模型id")
    private Integer id;

    @NotBlank(message = "模型名不能为空")
    @ApiModelProperty(value = "模型名", required = true)
    private String modelName;

    @NotBlank(message = "模型json路径不能为空")
    @ApiModelProperty(value = "模型json路径", required = true)
    private String modelJsonPath;

    @NotBlank(message = "模型预览图片路径不能为空")
    @ApiModelProperty(value = "模型预览图片路径", required = true)
    private String modelImagePath;

    @ApiModelProperty(value = "模型背景图片图片路径")
    private String backgroundPath;

    @NotNull(message = "x不能为空")
    @ApiModelProperty(value = "x", required = true)
    private Double x;

    @NotNull(message = "y不能为空")
    @ApiModelProperty(value = "y", required = true)
    private Double y;

    @NotNull(message = "模型缩放不能为空")
    @ApiModelProperty(value = "模型缩放", required = true)
    private Double scale;

    @NotNull(message = "模型宽不能为空")
    @ApiModelProperty(value = "模型宽", required = true)
    private Integer width;

    @NotNull(message = "模型高不能为空")
    @ApiModelProperty(value = "模型高", required = true)
    private Integer height;

    @NotNull(message = "锚点x不能为空")
    @ApiModelProperty(value = "锚点，以画布中心下方为中心点x（单位：倍）", required = true)
    private Double anchorx;

    @NotNull(message = "锚点y不能为空")
    @ApiModelProperty(value = "锚点，以画布中心下方为中心点y（单位：倍）", required = true)
    private Double anchory;

    @NotBlank(message = "模型在文件系统的路径不能为空")
    @ApiModelProperty(value = "模型在文件系统的路径，用于下载和删除的时候找到这个路径")
    private String baseFilePath;

    @EnumValid(message = "showMark枚举值不正确", target = ShowMarkEnum.class)
    @ApiModelProperty(value = "是否在前台展示的标记，0不展示，1展示，用于下架模型")
    private Integer showMark;

    @ApiModelProperty(value = "描述")
    private String description;

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

    public String getBaseFilePath() {
        return baseFilePath;
    }

    public void setBaseFilePath(String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    public Integer getShowMark() {
        return showMark;
    }

    public void setShowMark(Integer showMark) {
        this.showMark = showMark;
    }

    @Override
    public String toString() {
        return "Live2dDTO{" +
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
                ", baseFilePath='" + baseFilePath + '\'' +
                ", showMark=" + showMark +
                ", description='" + description + '\'' +
                '}';
    }
}
