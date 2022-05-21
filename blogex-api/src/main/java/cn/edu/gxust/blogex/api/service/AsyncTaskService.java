package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.dao.po.CommentPO;

/**
 * @author zhaoyijie
 * @since 2022/3/24 19:37
 */
public interface AsyncTaskService extends BlogMailService {

    /**
     * 通知父评论者，有人回复了你的评论
     *
     * @param commentPO 评论对象
     */
    void notifyParentReviewer(CommentPO commentPO);

    /**
     * 有人评论，通知博主
     *
     * @param commentPO 评论对象
     */
    void notifyAdminIfAddComment(CommentPO commentPO);

}
