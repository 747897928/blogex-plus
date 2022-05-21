package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.BloggerInfoDTO;
import cn.edu.gxust.blogex.api.vo.BloggerInfoVO;
import cn.edu.gxust.blogex.dao.po.BloggerInfoPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * (BloggerInfo)表服务接口
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
public interface BloggerInfoService extends IService<BloggerInfoPO> {

    /**
     * 获取博主个人信息
     */
    BloggerInfoVO getBlogInfo();

    /**
     * 更新博主个人信息
     */
    int update(BloggerInfoDTO bloggerInfoDTO);

}

