package cn.edu.gxust.blogex.common.exception;

/**
 * @author zhaoyijie
 * @since 2022/3/12 12:59
 */
public class ClassifyNotFoundException extends ResourcesNotFoundException {

    public ClassifyNotFoundException(Integer id) {
        this("找不到id为" + id + "的分类");
    }

    public ClassifyNotFoundException(String message) {
        super(message);
    }

}
