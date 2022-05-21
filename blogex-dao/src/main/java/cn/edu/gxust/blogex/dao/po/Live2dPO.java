package cn.edu.gxust.blogex.dao.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022/4/17 11:34
 */
@TableName(value = "t_live2d")
public class Live2dPO {

    /**
     * 模型id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模型名
     */
    private String modelName;

    /**
     * 模型json路径
     */
    private String modelJsonPath;

    /**
     * 模型预览图片路径
     */
    private String modelImagePath;

    /**
     * 模型背景图片图片路径
     */
    private String backgroundPath;

    /**
     * x
     */
    private Double x;

    /**
     * y
     */
    private Double y;

    /**
     * 模型缩放
     */
    private Double scale;

    /**
     * 模型宽
     */
    private Integer width;

    /**
     * 模型高
     */
    private Integer height;

    /**
     * 锚点，以画布中心下方为中心点x（单位：倍）
     */
    private Double anchorx;

    /**
     * 锚点，以画布中心下方为中心点y（单位：倍）
     */
    private Double anchory;

    /**
     * 模型在文件系统的路径，用于下载和删除的时候找到这个路径
     */
    private String baseFilePath;

    /**
     * 是否在前台展示的标记，0不展示，1展示，用于下架模型
     */
    private Integer showMark;
    /**
     * 描述
     */
    private String description;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(update = "now()", fill = FieldFill.UPDATE)
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

    public String getBaseFilePath() {
        return baseFilePath;
    }

    public void setBaseFilePath(String baseFilePath) {
        this.baseFilePath = baseFilePath;
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

    @Override
    public String toString() {
        return "Live2dPO{" +
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
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
