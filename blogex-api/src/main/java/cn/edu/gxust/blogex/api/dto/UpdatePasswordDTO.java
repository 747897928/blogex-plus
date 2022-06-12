package cn.edu.gxust.blogex.api.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author zhaoyijie
 * @since 2022/6/11 21:51
 */
public class UpdatePasswordDTO {

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String oldPassword;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String newPassword;

    @NotBlank(message = "验证码的uuid不能为空")
    @ApiModelProperty(value = "验证码的uuid", required = true)
    private String codeUuid;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String verifyCode;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCodeUuid() {
        return codeUuid;
    }

    public void setCodeUuid(String codeUuid) {
        this.codeUuid = codeUuid;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "UpdatePasswordDTO{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", codeUuid='" + codeUuid + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
