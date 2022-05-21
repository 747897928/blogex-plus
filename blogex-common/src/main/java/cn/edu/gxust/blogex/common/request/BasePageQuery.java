package cn.edu.gxust.blogex.common.request;

/**
 * @author zhaoyijie
 * @since 2022/3/19 15:00
 */
public class BasePageQuery {

    protected Long pageNo;

    protected Long pageSize;

    public BasePageQuery() {
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "BaseQuery{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
