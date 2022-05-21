package cn.edu.gxust.blogex.api.query;

import cn.edu.gxust.blogex.common.request.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhaoyijie
 * @since 2022/3/19 15:06
 */
public class ClassifyPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "搜索关键字,为null则不模糊匹配")
    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    @Override
    public String toString() {
        return "ClassifyQuery{" +
                "searchKey='" + searchKey + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
