package cn.edu.gxust.blogex.common.exception;

/**
 * @author zhaoyijie
 * @since 2022/3/20 20:18
 */
public class BlogLinkNotFoundException extends ResourcesNotFoundException{

    public BlogLinkNotFoundException(Integer id) {
        this("找不到id为" + id + "的友联");
    }

    public BlogLinkNotFoundException(String message) {
        super(message);
    }

}
