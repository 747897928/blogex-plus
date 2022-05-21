package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.service.AsyncTaskService;
import cn.edu.gxust.blogex.api.service.BlogSettingService;
import cn.edu.gxust.blogex.api.utils.MailUtils;
import cn.edu.gxust.blogex.api.vo.BlogSettingVO;
import cn.edu.gxust.blogex.common.enums.MailEnableEnum;
import cn.edu.gxust.blogex.common.enums.PageTypeEnum;
import cn.edu.gxust.blogex.dao.mappers.ArticleMapper;
import cn.edu.gxust.blogex.dao.mappers.CommentMapper;
import cn.edu.gxust.blogex.dao.po.CommentPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/3/24 19:39
 */
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    private final static Logger logger = LoggerFactory.getLogger(AsyncTaskServiceImpl.class);

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private BlogSettingService blogSettingService;

    @Value("${blogex.mail.username}")
    private String mailUsername;

    @Value("${blogex.mail.password}")
    private String mailPassword;

    @Value("${blogex.mail.host}")
    private String mailHost;

    @Value("${blogex.mail.port}")
    private Integer mailPort;

    @Async
    public boolean sendMail(String subject, String text, String to) {
        MailUtils.sendMail(mailHost, mailPort, mailUsername, mailPassword, new String[]{to}, null, subject, text);
        return true;
    }

    @Override
    public void notifyParentReviewer(CommentPO commentPO) {
        BlogSettingVO blogSetting = blogSettingService.getBlogSetting();
        if (blogSetting.getMailEnable().equals(MailEnableEnum.ON.getCode())) {
            Integer parentId = commentPO.getParentId();
            if (parentId == null) {
                return;
            }
            CommentPO comment = commentMapper.selectById(parentId);
            String userEmail = comment.getUserEmail();
            if (userEmail != null && isEmail(userEmail)) {
                Integer pageType = commentPO.getPageType();
                PageTypeEnum pageTypeEnum = PageTypeEnum.of(pageType);
                if (null == pageTypeEnum) {
                    logger.warn("错误的pageType：" + pageType + "commentPO：" + commentPO);
                    return;
                }
                String subject;
                String text;
                String blogAddress = blogSetting.getBlogAddress();
                switch (pageTypeEnum) {
                    case ARTICLE_COMMENT:
                        Integer articleId = commentPO.getArticleId();
                        String articleTitle = articleMapper.findTitle(articleId);
                        String articleUrl = blogAddress + "/article.html?articleId=" + articleId;
                        subject = "[博客评论回复通知]Re:" + articleTitle;
                        text = "Re:" + articleTitle + "\n评论内容：" + commentPO.getCommentContent() +
                                "\n评论者：" + commentPO.getUserName() + "\n时间:： " + commentPO.getCreateTime()
                                + "\nURL： " + articleUrl;
                        break;
                    case ABOUT_ME_COMMENT:
                        String url = blogAddress + "/aboutMe.html";
                        subject = "[博客评论回复通知]Re: 关于我";
                        text = "Re: 关于我\n评论内容：" + commentPO.getCommentContent() +
                                "\n评论者：" + commentPO.getUserName() + "\n时间:： " + commentPO.getCreateTime()
                        + "\nURL： " + url;
                        break;
                    case LINK_COMMENT:
                        String linkUrl = blogAddress + "/links.html";
                        subject = "[博客评论回复通知]Re: 友联";
                        text = "Re: 友联\n评论内容：" + commentPO.getCommentContent() +
                                "\n评论者：" + commentPO.getUserName() + "\n时间:： " + commentPO.getCreateTime()
                                + "\nURL： " + linkUrl;
                        break;
                    default:
                        return;
                }
                sendMail(subject, text, blogSetting.getBlogMailAddress());
            } else {
                logger.warn("该邮箱为空或者该邮箱格式不合法，评论id：" + comment.getId());
            }
        } else {
            logger.debug("邮箱任务并未开启，跳过操作");
        }
    }

    @Override
    public void notifyAdminIfAddComment(CommentPO commentPO) {
        BlogSettingVO blogSetting = blogSettingService.getBlogSetting();
        String blogMailAddress = blogSetting.getBlogMailAddress();
        if (blogSetting.getMailEnable().equals(MailEnableEnum.ON.getCode())) {
            if (blogMailAddress != null && isEmail(blogMailAddress)) {
                Integer pageType = commentPO.getPageType();
                PageTypeEnum pageTypeEnum = PageTypeEnum.of(pageType);
                if (null == pageTypeEnum) {
                    logger.warn("错误的pageType：" + pageType + "commentPO：" + commentPO);
                    return;
                }
                String subject;
                String text;
                String blogAddress = blogSetting.getBlogAddress();
                switch (pageTypeEnum) {
                    case ARTICLE_COMMENT:
                        Integer articleId = commentPO.getArticleId();
                        String articleTitle = articleMapper.findTitle(articleId);
                        subject = "[博客评论回复通知]Re:" + articleTitle;
                        String articleUrl = blogAddress + "/article.html?articleId=" + articleId;
                        text = "Re:" + articleTitle + "\n评论内容：" + commentPO.getCommentContent() +
                                "\n评论者：" + commentPO.getUserName() + "\n时间:： " + commentPO.getCreateTime()
                                + "\nURL： " + articleUrl;
                        break;
                    case ABOUT_ME_COMMENT:
                        String url = blogAddress + "/aboutMe.html";
                        subject = "[博客评论回复通知]Re: 关于我";
                        text = "Re: 关于我\n评论内容：" + commentPO.getCommentContent() +
                                "\n评论者：" + commentPO.getUserName() + "\n时间:： " + commentPO.getCreateTime()
                                + "\nURL： " + url;
                        break;
                    case LINK_COMMENT:
                        String linkUrl = blogAddress + "/links.html";
                        subject = "[博客评论回复通知]Re: 友联";
                        text = "Re: 友联\n评论内容：" + commentPO.getCommentContent() +
                                "\n评论者：" + commentPO.getUserName() + "\n时间:： " + commentPO.getCreateTime()
                                + "\nURL： " + linkUrl;
                        break;
                    default:
                        return;
                }
                sendMail(subject, text, blogMailAddress);
            } else {
                logger.error("博主的邮箱为空或者该邮箱格式不合法！！！");
            }
        } else {
            logger.debug("邮箱任务并未开启，跳过操作");
        }
    }
}
