package cn.edu.gxust.blogex.api.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhaoyijie
 * @since 2022/3/24 11:54
 */
public class CommentVerifyCodeDTO {

    @ApiModelProperty(value = "评论实体")
    private CommentDTO comment;

    @ApiModelProperty(value = "验证码实体")
    private VerifyCodeDTO verifyCode;

    public CommentDTO getComment() {
        return comment;
    }

    public void setComment(CommentDTO comment) {
        this.comment = comment;
    }

    public VerifyCodeDTO getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(VerifyCodeDTO verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "CommentVerifyCodeDTO{" +
                "comment=" + comment +
                ", verifyCodeDTO=" + verifyCode +
                '}';
    }
}
