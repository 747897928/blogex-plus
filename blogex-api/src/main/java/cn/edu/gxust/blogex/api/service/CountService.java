package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.vo.CountVO;

/**
 * @author zhaoyijie
 * @since 2022/4/8 16:58
 */
public interface CountService {

    CountVO count();

    long increase();

}
