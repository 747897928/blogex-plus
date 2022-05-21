package cn.edu.gxust.blogex.gateway.vo;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 图片文字验证码实体
 *
 * @author zhaoyijie
 * @since 2020/1/13 14:44
 */
public class VerifyCodeVO {

    @NotNull(message = "唯一标识不能为空")
    @ApiModelProperty(value = "唯一标识,用于定位到具体的校验码", required = true)
    private String uuid;

    @ApiModelProperty(value = "实际值,后端不会传给前端")
    private String code;

    @ApiModelProperty(value = "图片base64编码 data:image/png;base64")
    private String base64ImgStr;

    @ApiModelProperty(value = "过期时间，只是给前端看,无实际意义")
    private long expireTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBase64ImgStr() {
        return base64ImgStr;
    }

    public void setBase64ImgStr(String base64ImgStr) {
        this.base64ImgStr = base64ImgStr;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "VerifyCodeVO{" +
                "uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", base64ImgStr='" + base64ImgStr + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
