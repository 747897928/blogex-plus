package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.vo.ArticleVO;

import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/5/11 22:45
 */
public interface RssService {

    /**
     * 根据文章列表创建rss文档
     *
     * @param articleVOList 文章列表
     * @return 字符串形式的rss文档
     */
    String createRssXml(List<ArticleVO> articleVOList);

    /**
     * 获取构建rss的文章列表
     *
     * @param pageSize limit多少篇
     * @return 最新的pageSize篇文章
     */
    List<ArticleVO> getRssList(Integer pageSize);

}
