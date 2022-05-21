package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.BlogMusicDTO;
import cn.edu.gxust.blogex.api.query.BlogMusicPageQuery;
import cn.edu.gxust.blogex.api.vo.BlogMusicVO;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.po.BlogMusicPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * (BlogMusic)表服务接口
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
public interface BlogMusicService extends IService<BlogMusicPO> {

    /**
     * 新增音乐
     */
    int insert(BlogMusicDTO blogMusicDTO);

    /**
     * 获取分页后的音乐列表
     *
     */
    Pagination<BlogMusicVO> listPage(BlogMusicPageQuery query);

    /**
     * 获取所有音乐
     */
    List<BlogMusicVO> selectAll();

    /**
     * 批量删除
     * @param idList 音乐id集合
     */
    int batchDelete(List<Integer> idList);

    /**
     * 更新
     */
    int update(BlogMusicDTO blogMusicDTO);

}

