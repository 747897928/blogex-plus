package cn.edu.gxust.blogex.common.response;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private Long pageNo;
    private Long pageSize;
    private Long total;
    private Long page;
    private List<T> list;

    public Pagination() {
    }

    public Pagination(Long pageNo, Long pageSize, List<T> list) {
        this(pageNo, pageSize, 0L, list);
    }

    public Pagination(Integer pageNo, Integer pageSize, Long total, List<T> list) {
        this(pageNo != null ? Long.valueOf(pageNo) : 0, pageSize != null ? Long.valueOf(pageSize) : 0, total, list);
    }

    public Pagination(Long pageNo, Long pageSize, Long total, List<T> list) {
        this.pageNo = pageNo != null ? pageNo : 0;
        this.pageSize = pageSize != null ? pageSize : 0;
        this.total = total != null ? total : 0L;
        this.page = this.pageSize != 0 ? ((long) Math.ceil((double) this.total / this.pageSize)) : 0L;
        this.list = list != null ? list : new ArrayList<>();
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", page=" + page +
                ", list=" + list +
                '}';
    }
}
