package cn.edu.gxust.blogex.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * @author zhaoyijie
 * @since 2022/4/8 17:05
 */
public class EchartsVO <T,S>{

    /**
     * 横坐标数据
     */
    @JsonProperty("xAxisData")
    private Collection<T> xAxisData;

    /**
     * 纵坐标数据
     */
    @JsonProperty("seriesData")
    private Collection<S> seriesData;

    public EchartsVO() {
    }

    public EchartsVO(Collection<T> xAxisData, Collection<S> seriesData) {
        this.xAxisData = xAxisData;
        this.seriesData = seriesData;
    }

    public Collection<T> getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(Collection<T> xAxisData) {
        this.xAxisData = xAxisData;
    }

    public Collection<S> getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(Collection<S> seriesData) {
        this.seriesData = seriesData;
    }

    @Override
    public String toString() {
        return "EchartsVO{" +
                "xAxisData=" + xAxisData +
                ", seriesData=" + seriesData +
                '}';
    }
}
