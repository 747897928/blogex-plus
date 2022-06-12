package cn.edu.gxust.blogex.common.entity;

/**
 * 滑块验证码实体
 */
public class Verification {

    /**
     * 验证码的uuid
     */
    private String codeUuid;

    /**
     * 滑块图
     */
    private String slidingImage;

    /**
     * 原图
     */
    private String originalImage;

    /**
     * 宽
     */
    private Integer xWidth;

    /**
     * 高
     */
    private Integer yHeight;

    /**
     * 有效期
     */
    private long expireTime;

    public Verification() {
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
        return "Verification{" +
                "codeUuid='" + codeUuid + '\'' +
                ", slidingImage='" + slidingImage + '\'' +
                ", originalImage='" + originalImage + '\'' +
                ", xWidth=" + xWidth +
                ", yHeight=" + yHeight +
                ", expireTime=" + expireTime +
                '}';
    }
}
