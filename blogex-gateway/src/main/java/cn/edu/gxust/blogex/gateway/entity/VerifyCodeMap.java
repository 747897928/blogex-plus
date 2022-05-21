package cn.edu.gxust.blogex.gateway.entity;

/**
 * @author zhaoyijie
 * @since 2022/4/27 21:08
 */
public class VerifyCodeMap {

    private Integer verifyCodeType;

    private String verifyCodeData;

    public VerifyCodeMap() {
    }

    private VerifyCodeMap(Builder builder) {
        setVerifyCodeType(builder.verifyCodeType);
        setVerifyCodeData(builder.verifyCodeData);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(VerifyCodeMap copy) {
        Builder builder = new Builder();
        builder.verifyCodeType = copy.getVerifyCodeType();
        builder.verifyCodeData = copy.getVerifyCodeData();
        return builder;
    }

    public Integer getVerifyCodeType() {
        return verifyCodeType;
    }

    public void setVerifyCodeType(Integer verifyCodeType) {
        this.verifyCodeType = verifyCodeType;
    }

    public String getVerifyCodeData() {
        return verifyCodeData;
    }

    public void setVerifyCodeData(String verifyCodeData) {
        this.verifyCodeData = verifyCodeData;
    }

    @Override
    public String toString() {
        return "VerifyCodeMap{" +
                "verifyCodeType=" + verifyCodeType +
                ", verifyCodeData=" + verifyCodeData +
                '}';
    }


    public static final class Builder {
        private Integer verifyCodeType;
        private String verifyCodeData;

        private Builder() {
        }

        public Builder verifyCodeType(Integer val) {
            verifyCodeType = val;
            return this;
        }

        public Builder verifyCodeData(String val) {
            verifyCodeData = val;
            return this;
        }

        public VerifyCodeMap build() {
            return new VerifyCodeMap(this);
        }
    }
}
