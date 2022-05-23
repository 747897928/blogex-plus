package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.service.RssService;
import cn.edu.gxust.blogex.api.vo.ArticleVO;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/5/11 22:43
 */

@Api(tags = "Rss订阅相关")
@RestController
@RequestMapping(value = "/api/rss")
public class RssController {

    @Resource
    private RssService rssService;

    @GetMapping(value = "/openApi", produces = MediaType.APPLICATION_XML_VALUE)
    public String getRss() {
        List<ArticleVO> articleVOList = rssService.getRssList(10);
        return rssService.createRssXml(articleVOList);
    }

}
