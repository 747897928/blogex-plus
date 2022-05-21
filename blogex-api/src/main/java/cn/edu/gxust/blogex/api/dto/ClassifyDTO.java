package cn.edu.gxust.blogex.api.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 分类
 *
 * @author zhaoyijie
 * @since 2022/3/11 23:34
 */
public class ClassifyDTO {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    @NotBlank(message = "分类不能为空")
    @ApiModelProperty(value = "分类名", name = "classifyName", required = true, example = "云原生")
    private String classifyName;

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

    @Override
    public String toString() {
        return "ClassifyDTO{" +
                "id=" + id +
                ", classifyName='" + classifyName + '\'' +
                '}';
    }
}
