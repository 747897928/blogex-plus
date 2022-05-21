package cn.edu.gxust.blogex.api.vo;

/**
 * @author zhaoyijie
 * @since 2022/4/8 17:01
 */
public class CountVO {

    private Integer todayCount;

    private Integer weekCount;

    private Integer mouthCount;

    private Long totalCount;

    private EchartsVO<String, Integer> echartsData;

    public CountVO() {
    }

    private CountVO(Builder builder) {
        setTodayCount(builder.todayCount);
        setWeekCount(builder.weekCount);
        setMouthCount(builder.mouthCount);
        setTotalCount(builder.totalCount);
        setEchartsData(builder.echartsData);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(CountVO copy) {
        Builder builder = new Builder();
        builder.todayCount = copy.getTodayCount();
        builder.weekCount = copy.getWeekCount();
        builder.mouthCount = copy.getMouthCount();
        builder.totalCount = copy.getTotalCount();
        builder.echartsData = copy.getEchartsData();
        return builder;
    }

    public Integer getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(Integer todayCount) {
        this.todayCount = todayCount;
    }

    public Integer getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(Integer weekCount) {
        this.weekCount = weekCount;
    }

    public Integer getMouthCount() {
        return mouthCount;
    }

    public void setMouthCount(Integer mouthCount) {
        this.mouthCount = mouthCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public EchartsVO<String, Integer> getEchartsData() {
        return echartsData;
    }

    public void setEchartsData(EchartsVO<String, Integer> echartsData) {
        this.echartsData = echartsData;
    }

    @Override
    public String toString() {
        return "CountVO{" +
                "todayCount=" + todayCount +
                ", weekCount=" + weekCount +
                ", mouthCount=" + mouthCount +
                ", totalCount=" + totalCount +
                ", echartsData=" + echartsData +
                '}';
    }


    public static final class Builder {
        private Integer todayCount;
        private Integer weekCount;
        private Integer mouthCount;
        private Long totalCount;
        private EchartsVO<String, Integer> echartsData;

        private Builder() {
        }

        public Builder todayCount(Integer val) {
            todayCount = val;
            return this;
        }

        public Builder weekCount(Integer val) {
            weekCount = val;
            return this;
        }

        public Builder mouthCount(Integer val) {
            mouthCount = val;
            return this;
        }

        public Builder totalCount(Long val) {
            totalCount = val;
            return this;
        }

        public Builder echartsData(EchartsVO<String, Integer> val) {
            echartsData = val;
            return this;
        }

        public CountVO build() {
            return new CountVO(this);
        }
    }
}
