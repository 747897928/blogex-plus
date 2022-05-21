package cn.edu.gxust.blogex.dao.po;

/**
 * @author zhaoyijie
 * @since 2022/3/25 16:07
 */
public class OverviewPO {

    /**
     * 总文章数
     */
    private Integer sumArticle;

    /**
     * 总阅览量
     */
    private Integer sumView;

    /**
     * 总评论
     */
    private Integer sumComment;

    /**
     * 总点赞数
     */
    private Integer sumSupport;


    public OverviewPO() {
    }

    public Integer getSumArticle() {
        return sumArticle;
    }

    public void setSumArticle(Integer sumArticle) {
        this.sumArticle = sumArticle;
    }

    public Integer getSumView() {
        return sumView;
    }

    public void setSumView(Integer sumView) {
        this.sumView = sumView;
    }

    public Integer getSumComment() {
        return sumComment;
    }

    public void setSumComment(Integer sumComment) {
        this.sumComment = sumComment;
    }

    public Integer getSumSupport() {
        return sumSupport;
    }

    public void setSumSupport(Integer sumSupport) {
        this.sumSupport = sumSupport;
    }

    @Override
    public String toString() {
        return "OverviewPO{" +
                "sumArticle=" + sumArticle +
                ", sumView=" + sumView +
                ", sumComment=" + sumComment +
                ", sumSupport=" + sumSupport +
                '}';
    }
}
