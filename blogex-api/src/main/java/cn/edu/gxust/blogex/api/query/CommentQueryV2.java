package cn.edu.gxust.blogex.api.query;

import cn.edu.gxust.blogex.common.request.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author zhaoyijie
 * @since 2022/3/20 22:09
 */
public class CommentQueryV2 extends BasePageQuery {

    @NotNull(message = "文章id不能为空")
    @ApiModelProperty(value = "文章id")
    private Integer articleId;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "CommentQueryV2{" +
                "articleId=" + articleId +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }

}
