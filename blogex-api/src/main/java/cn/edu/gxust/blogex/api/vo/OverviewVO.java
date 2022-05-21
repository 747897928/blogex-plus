package cn.edu.gxust.blogex.api.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhaoyijie
 * @since 2022/3/25 16:07
 */
public class OverviewVO {

    @ApiModelProperty(value = "总文章数")
    private Integer sumArticle;

    @ApiModelProperty(value = "总阅览量")
    private Integer sumView;

    @ApiModelProperty(value = "总评论数")
    private Integer sumComment;

    @ApiModelProperty(value = "总点赞数")
    private Integer sumSupport;

    public OverviewVO() {
    }

    private OverviewVO(Builder builder) {
        setSumArticle(builder.sumArticle);
        setSumView(builder.sumView);
        setSumComment(builder.sumComment);
        setSumSupport(builder.sumSupport);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(OverviewVO copy) {
        Builder builder = new Builder();
        builder.sumArticle = copy.getSumArticle();
        builder.sumView = copy.getSumView();
        builder.sumComment = copy.getSumComment();
        builder.sumSupport = copy.getSumSupport();
        return builder;
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
        return "OverviewVO{" +
                "sumArticle=" + sumArticle +
                ", sumView=" + sumView +
                ", sumComment=" + sumComment +
                ", sumSupport=" + sumSupport +
                '}';
    }


    public static final class Builder {
        private Integer sumArticle;
        private Integer sumView;
        private Integer sumComment;
        private Integer sumSupport;

        private Builder() {
        }

        public Builder sumArticle(Integer val) {
            sumArticle = val;
            return this;
        }

        public Builder sumView(Integer val) {
            sumView = val;
            return this;
        }

        public Builder sumComment(Integer val) {
            sumComment = val;
            return this;
        }

        public Builder sumSupport(Integer val) {
            sumSupport = val;
            return this;
        }

        public OverviewVO build() {
            return new OverviewVO(this);
        }
    }
}
