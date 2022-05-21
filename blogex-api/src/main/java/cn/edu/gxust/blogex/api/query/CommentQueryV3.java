package cn.edu.gxust.blogex.api.query;

import cn.edu.gxust.blogex.common.annotation.EnumValid;
import cn.edu.gxust.blogex.common.enums.PageTypeEnum;
import cn.edu.gxust.blogex.common.request.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhaoyijie
 * @since 2022/5/11 19:30
 */
public class CommentQueryV3 extends BasePageQuery {

    @EnumValid(message = "页面类型枚举值不正确", target = PageTypeEnum.class)
    @ApiModelProperty(value = "页面类型，0->文章评论，1->关于我评论，2->友联评论，默认是0文章评论")
    private Integer pageType;

    public CommentQueryV3() {
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    @Override
    public String toString() {
        return "CommentQueryV3{" +
                "pageType=" + pageType +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
