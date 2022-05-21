package cn.edu.gxust.blogex.api.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 图片文字验证码实体
 *
 * @author zhaoyijie
 * @since 2020/1/13 14:44
 */
public class VerifyCodeDTO {

    @NotBlank(message = "唯一标识不能为空")
    @ApiModelProperty(value = "唯一标识,用于定位到具体的校验码", required = true)
    private String codeUuid;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码值")
    private String code;

    public String getCodeUuid() {
        return codeUuid;
    }

    public void setCodeUuid(String codeUuid) {
        this.codeUuid = codeUuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "VerifyCodeDTO{" +
                "uuid='" + codeUuid + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
