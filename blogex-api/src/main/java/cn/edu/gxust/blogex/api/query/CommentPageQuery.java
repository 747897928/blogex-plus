package cn.edu.gxust.blogex.api.query;

import cn.edu.gxust.blogex.common.annotation.EnumValid;
import cn.edu.gxust.blogex.common.enums.PageTypeEnum;
import cn.edu.gxust.blogex.common.request.BasePageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022/3/20 22:09
 */
public class CommentPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "搜索关键字,为null则不模糊匹配")
    private String searchKey;

    @EnumValid(message = "页面类型枚举值不正确", target = PageTypeEnum.class)
    @ApiModelProperty(value = "页面类型，0->文章评论，1->关于我评论，2->友联评论，默认是0文章评论")
    private Integer pageType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "起止时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

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

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    @Override
    public String toString() {
        return "CommentPageQuery{" +
                "searchKey='" + searchKey + '\'' +
                ", pageType=" + pageType +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
