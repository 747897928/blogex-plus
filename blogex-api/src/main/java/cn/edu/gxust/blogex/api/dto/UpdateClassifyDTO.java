package cn.edu.gxust.blogex.api.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhaoyijie
 * @since 2022/3/19 14:45
 */
public class UpdateClassifyDTO {

    @NotNull(message = "分类id不能为空")
    @ApiModelProperty(value = "分类id", name = "id", required = true, example = "1")
    private Integer id;

    @NotBlank(message = "新分类名不能为空")
    @ApiModelProperty(value = "新分类名", name = "newClassifyName", required = true, example = "云原生")
    private String newClassifyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewClassifyName() {
        return newClassifyName;
    }

    public void setNewClassifyName(String newClassifyName) {
        this.newClassifyName = newClassifyName;
    }

    @Override
    public String toString() {
        return "UpdateClassifyDTO{" +
                "id=" + id +
                ", newClassifyName='" + newClassifyName + '\'' +
                '}';
    }
}
