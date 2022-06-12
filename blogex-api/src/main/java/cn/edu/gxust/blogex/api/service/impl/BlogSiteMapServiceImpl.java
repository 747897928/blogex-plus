package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.service.ArticleService;
import cn.edu.gxust.blogex.api.service.BlogSettingService;
import cn.edu.gxust.blogex.api.service.BlogSiteMapService;
import cn.edu.gxust.blogex.api.vo.BlogSettingVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.enums.ArticleStatus;
import cn.edu.gxust.blogex.dao.po.ArticlePO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.redfin.sitemapgenerator.SitemapIndexGenerator;
import com.redfin.sitemapgenerator.W3CDateFormat;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/5/30 19:25
 */
@Service
public class BlogSiteMapServiceImpl implements BlogSiteMapService {

    private final String SITEMAP_FILE_BASE_PATH = "sitemap_index/";

    @Resource
    private BlogSettingService blogSettingService;

    @Resource
    private ArticleService articleService;

    public void generateSitemap() {
        File file = new File(SITEMAP_FILE_BASE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        BlogSettingVO blogSetting = blogSettingService.getBlogSetting();
        //建议通过nginx或者ingress将前后端的域名通过反向代理的方式使其相等，省掉一些不必要的麻烦
        String blogAddress = blogSetting.getBlogAddress();//前端入口地址，前端域名
        String webDomain = blogSetting.getWebDomain();//后端接口地址前缀，后端域名
        List<ArticlePO> articlePOList = articleService.list(Wrappers.<ArticlePO>lambdaQuery()
                .select(ArticlePO::getId, ArticlePO::getTitle, ArticlePO::getPriority, ArticlePO::getCreateTime)
                .eq(ArticlePO::getArticleStatus, ArticleStatus.PUBLISH.getCode())
                .orderByAsc(ArticlePO::getPriority)
                .orderByDesc(ArticlePO::getCreateTime)
        );
        try {
            WebSitemapGenerator indexWebSitemapGenerator = WebSitemapGenerator.builder(blogAddress, file)
                    .fileNamePrefix("index_site_map").build();

            WebSitemapGenerator webSitemapGenerator = WebSitemapGenerator.builder(blogAddress, file)
                    .fileNamePrefix("sitemap_list_index_article").build();
            Date date = new Date();
            for (ArticlePO articlePO : articlePOList) {
                String articleUrl = blogAddress + "/article.html?articleId=" + articlePO.getId();
                Date createTime = articlePO.getCreateTime();
                WebSitemapUrl webSitemapUrl = new WebSitemapUrl.Options(articleUrl).lastMod(createTime).build();
                webSitemapGenerator.addUrl(webSitemapUrl);
            }

            indexWebSitemapGenerator.addUrl(new WebSitemapUrl.Options(blogAddress + "/index.html").lastMod(date).build());
            indexWebSitemapGenerator.addUrl(new WebSitemapUrl.Options(blogAddress + "/aboutMe.html").lastMod(date).build());
            indexWebSitemapGenerator.addUrl(new WebSitemapUrl.Options(blogAddress + "/tagsWall.html").lastMod(date).build());
            indexWebSitemapGenerator.addUrl(new WebSitemapUrl.Options(blogAddress + "/links.html").lastMod(date).build());

            // 生成 sitemap 文件
            List<File> articleFiles = webSitemapGenerator.write();
            List<File> indexFiles = indexWebSitemapGenerator.write();

            // 构造 sitemap_index 生成器
            W3CDateFormat dateFormat = new W3CDateFormat(W3CDateFormat.Pattern.DAY);
            SitemapIndexGenerator sitemapIndexGenerator = new SitemapIndexGenerator
                    .Options(webDomain, new File(SITEMAP_FILE_BASE_PATH + "sitemap_index.xml"))
                    .dateFormat(dateFormat)
                    .autoValidate(true)
                    .build();
            articleFiles.forEach(e -> {
                String fileName = e.getName();
                try {
                    // 组装 sitemap 文件 URL 地址
                    sitemapIndexGenerator.addUrl(webDomain + Constants.DIVISION_STRING + SITEMAP_FILE_BASE_PATH + fileName);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            });
            indexFiles.forEach(e -> {
                String fileName = e.getName();
                try {
                    // 组装 sitemap 文件 URL 地址
                    sitemapIndexGenerator.addUrl(webDomain + Constants.DIVISION_STRING + SITEMAP_FILE_BASE_PATH + fileName);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            });
            // 生成 sitemap_index 文件
            sitemapIndexGenerator.write();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
