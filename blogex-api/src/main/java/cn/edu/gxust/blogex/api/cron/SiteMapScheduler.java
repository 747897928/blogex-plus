package cn.edu.gxust.blogex.api.cron;

import cn.edu.gxust.blogex.api.service.BlogSiteMapService;
import cn.edu.gxust.blogex.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhaoyijie
 * @since 2022/6/12 10:28
 */
@Service
public class SiteMapScheduler {

    private final static Logger logger = LoggerFactory.getLogger(SiteMapScheduler.class);

    @Resource
    private BlogSiteMapService siteMapService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void createSiteMap() {
        logger.info("createSiteMap start at {}", DateUtils.formatDatetimeFull().format(new Date()));
        siteMapService.generateSitemap();
    }

}
