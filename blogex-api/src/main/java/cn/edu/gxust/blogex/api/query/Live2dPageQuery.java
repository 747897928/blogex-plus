package cn.edu.gxust.blogex.api.query;

import cn.edu.gxust.blogex.common.annotation.EnumValid;
import cn.edu.gxust.blogex.common.enums.ShowMarkEnum;
import cn.edu.gxust.blogex.common.request.BasePageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022/5/2 08:45
 */
public class Live2dPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "搜索关键字,为null则不模糊匹配")
    private String searchKey;

    @EnumValid(message = "showMark枚举值不正确", target = ShowMarkEnum.class)
    @ApiModelProperty(value = "是否在前台展示的标记，0不展示，1展示，用于下架模型")
    private Integer showMark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "起止时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    public Live2dPageQuery() {
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getShowMark() {
        return showMark;
    }

    public void setShowMark(Integer showMark) {
        this.showMark = showMark;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Live2dPageQuery{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", searchKey='" + searchKey + '\'' +
                ", showMark=" + showMark +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
