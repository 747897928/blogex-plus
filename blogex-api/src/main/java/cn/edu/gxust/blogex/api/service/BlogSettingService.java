package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.BlogSettingDTO;
import cn.edu.gxust.blogex.api.vo.BlogSettingVO;
import cn.edu.gxust.blogex.dao.po.BlogSettingPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * (BlogSetting)表服务接口
 *
 * @author zhaoyijie
 * @since 2022-05-15 12:40:55
 */
public interface BlogSettingService extends IService<BlogSettingPO> {

    /**
     * 修改博客配置
     */
    int update(BlogSettingDTO blogSettingDTO);

    /**
     * 获取博客配置
     */
    BlogSettingVO getBlogSetting();
}

