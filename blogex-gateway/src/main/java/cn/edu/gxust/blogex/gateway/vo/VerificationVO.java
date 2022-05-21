package cn.edu.gxust.blogex.gateway.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 滑块验证码vo
 */
public class VerificationVO implements Serializable {

    @ApiModelProperty(value = "验证码的uuid")
    private String codeUuid;

    @ApiModelProperty(value = "滑块图")
    private String slidingImage;

    @ApiModelProperty(value = "原图")
    private String originalImage;

    @ApiModelProperty(value = "宽")
    private Integer xWidth;

    @ApiModelProperty(value = "高")
    private Integer yHeight;

    @ApiModelProperty(value = "有效期")
    private long expireTime;

    public VerificationVO() {
    }

    public String getSlidingImage() {
        return slidingImage;
    }

    public void setSlidingImage(String slidingImage) {
        this.slidingImage = slidingImage;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public Integer getxWidth() {
        return xWidth;
    }

    public void setxWidth(Integer xWidth) {
        this.xWidth = xWidth;
    }

    public Integer getyHeight() {
        return yHeight;
    }

    public void setyHeight(Integer yHeight) {
        this.yHeight = yHeight;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getCodeUuid() {
        return codeUuid;
    }

    public void setCodeUuid(String codeUuid) {
        this.codeUuid = codeUuid;
    }

    @Override
    public String toString() {
        return "VerificationVO{" +
                "codeUuid='" + codeUuid + '\'' +
                ", slidingImage='" + slidingImage + '\'' +
                ", originalImage='" + originalImage + '\'' +
                ", xWidth=" + xWidth +
                ", yHeight=" + yHeight +
                ", expireTime=" + expireTime +
                '}';
    }
}
