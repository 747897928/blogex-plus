package cn.edu.gxust.blogex.api.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhaoyijie
 * @since 2022/3/19 15:48
 */
public class UpdateTagDTO {

    @NotNull(message = "标签id不能为空")
    @ApiModelProperty(value = "标签id", name = "id", required = true, example = "1")
    private Integer id;

    @NotBlank(message = "新标签名不能为空")
    @ApiModelProperty(value = "新标签名", name = "newTagName", required = true, example = "kubernetes")
    private String newTagName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewTagName() {
        return newTagName;
    }

    public void setNewTagName(String newTagName) {
        this.newTagName = newTagName;
    }

    @Override
    public String toString() {
        return "UpdateTagDTO{" +
                "id=" + id +
                ", newTagName='" + newTagName + '\'' +
                '}';
    }
}
