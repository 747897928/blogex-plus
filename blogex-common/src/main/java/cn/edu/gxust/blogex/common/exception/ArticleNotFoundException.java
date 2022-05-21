package cn.edu.gxust.blogex.common.exception;

/**
 * @author zhaoyijie
 * @since 2022/3/12 11:56
 */
public class ArticleNotFoundException extends ResourcesNotFoundException {

    public ArticleNotFoundException(Integer id) {
        this("找不到id为" + id + "的文章");
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }

}
