package cn.edu.gxust.blogex.common.exception;

/**
 * @author zhaoyijie
 * @since 2022/3/19 15:56
 */
public class TagNotFoundException extends ResourcesNotFoundException {

    public TagNotFoundException(Integer id) {
        this("找不到id为" + id + "的标签");
    }

    public TagNotFoundException(String message) {
        super(message);
    }

}
