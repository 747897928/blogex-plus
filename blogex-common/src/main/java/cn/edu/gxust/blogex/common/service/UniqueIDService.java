package cn.edu.gxust.blogex.common.service;

/**
 * 唯一id服务类
 *
 * @author zhaoyijie
 * @since 2022/4/1 09:18
 */
public interface UniqueIDService {

    /**
     * 生成一个唯一的id,String类型，子类可以选择性实现
     */
    default String createStringUniqueId() {
        throw new UnsupportedOperationException();
    }

    /**
     * 生成一个唯一的id,Long类型，子类可以选择性实现
     */
    default long createLongUniqueId() {
        throw new UnsupportedOperationException();
    }

}
