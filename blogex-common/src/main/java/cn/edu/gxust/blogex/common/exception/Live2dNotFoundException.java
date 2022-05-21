package cn.edu.gxust.blogex.common.exception;

/**
 * @author zhaoyijie
 * @since 2022/5/1 22:19
 */
public class Live2dNotFoundException extends ResourcesNotFoundException {

    public Live2dNotFoundException(Integer id) {
        this("找不到id为" + id + "的live2d");
    }

    public Live2dNotFoundException(String message) {
        super(message);
    }

}
