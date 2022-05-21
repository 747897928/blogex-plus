package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.BlogLinkDTO;
import cn.edu.gxust.blogex.api.query.BlogLinkPageQuery;
import cn.edu.gxust.blogex.api.vo.BlogLinkVO;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.po.BlogLinkPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * (BlogLink)表服务接口
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
public interface BlogLinkService extends IService<BlogLinkPO> {

    /**
     * 新增友链
     */
    int insert(BlogLinkDTO blogLinkDTO);

    /**
     * 获取分页后的友联列表
     *
     */
    Pagination<BlogLinkVO> listPage(BlogLinkPageQuery query);

    /**
     * 获取所有友联
     */
    List<BlogLinkVO> selectAll();

    /**
     * 批量删除
     * @param idList 友联id集合
     */
    int batchDelete(List<Integer> idList);

    /**
     * 更新
     */
    int update(BlogLinkDTO blogLinkDTO);

    /**
     * 通过友联名查询友联
     * @param linkName 友联名
     * @return 友联
     */
    BlogLinkPO queryByLinkName(String linkName);
}

