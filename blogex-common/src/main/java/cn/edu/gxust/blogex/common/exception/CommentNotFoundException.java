package cn.edu.gxust.blogex.common.exception;

/**
 * @author zhaoyijie
 * @since 2022/3/24 13:59
 */
public class CommentNotFoundException extends ResourcesNotFoundException{

    public CommentNotFoundException(Integer id) {
        this("找不到id为" + id + "的评论");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }

}
