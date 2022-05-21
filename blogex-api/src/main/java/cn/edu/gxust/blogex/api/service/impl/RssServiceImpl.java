package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.query.ArticlePageQuery;
import cn.edu.gxust.blogex.api.service.ArticleService;
import cn.edu.gxust.blogex.api.service.BlogSettingService;
import cn.edu.gxust.blogex.api.service.BloggerInfoService;
import cn.edu.gxust.blogex.api.service.RssService;
import cn.edu.gxust.blogex.api.vo.ArticleVO;
import cn.edu.gxust.blogex.api.vo.BlogSettingVO;
import cn.edu.gxust.blogex.api.vo.BloggerInfoVO;
import cn.edu.gxust.blogex.common.enums.ArticleStatus;
import cn.edu.gxust.blogex.common.response.Pagination;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/5/11 22:47
 */
@Service
public class RssServiceImpl implements RssService {

    private final static Logger logger = LoggerFactory.getLogger(RssServiceImpl.class);

    @Resource
    private ArticleService articleService;

    @Resource
    private BloggerInfoService bloggerInfoService;

    @Resource
    private BlogSettingService blogSettingService;

    @Override
    public String createRssXml(List<ArticleVO> articleVOList) {
        BlogSettingVO blogSetting = blogSettingService.getBlogSetting();
        String blogAddress = blogSetting.getBlogAddress();
        List<ArticleVO> rssList = getRssList(10);
        Channel channel = new Channel("rss_2.0");
        BloggerInfoVO blogInfo = bloggerInfoService.getBlogInfo();
        String userName = blogInfo.getUserName();
        String title = userName + "的个人博客";
        channel.setTitle(title);/*网站标题*/
        channel.setDescription("最新文章");
        channel.setLink(blogAddress + "/index.html");
        channel.setEncoding("utf-8");/*RSS文件编码*/
        channel.setLanguage("zh-cn");/*RSS使用的语言*/
        channel.setTtl(5);/*time to live的简写，在刷新前当前RSS在缓存中可以保存多长时间（分钟）*/
        List<Item> items = new ArrayList<Item>();/*这个list对应rss中的item列表*/
        for (ArticleVO articleVO : rssList) {
            Item item = new Item();/*新建Item对象，对应rss中的<item></item>*/
            item.setTitle(articleVO.getTitle());
            item.setPubDate(articleVO.getCreateTime());
            String articleUrl = blogAddress + "/article.html?articleId=" + articleVO.getId();
            item.setLink(articleUrl);
            Description description = new Description();
            description.setValue(articleVO.getContent());
            item.setDescription(description);
            items.add(item);
            channel.setItems(items);
        }
        /*用WireFeedOutput对象输出rss文本*/
        WireFeedOutput out = new WireFeedOutput();
        try {
            return out.outputString(channel);
        } catch (IllegalArgumentException | FeedException e) {
            logger.error("", e);
            return null;
        }
    }

    @Override
    public List<ArticleVO> getRssList(Integer pageSize) {
        ArticlePageQuery articlePageQuery = new ArticlePageQuery();
        articlePageQuery.setPageNo(1L);
        articlePageQuery.setPageSize(Long.valueOf(pageSize));
        articlePageQuery.setArticleStatus(ArticleStatus.PUBLISH.getCode());
        Pagination<ArticleVO> pagination = articleService.listPage(articlePageQuery);
        return pagination.getList();
    }

}
