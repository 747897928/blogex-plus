package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.CommentConvertor;
import cn.edu.gxust.blogex.api.dto.CommentDTO;
import cn.edu.gxust.blogex.api.dto.CommentVerifyCodeDTO;
import cn.edu.gxust.blogex.api.dto.VerifyCodeDTO;
import cn.edu.gxust.blogex.api.query.CommentPageQuery;
import cn.edu.gxust.blogex.api.query.CommentQueryV2;
import cn.edu.gxust.blogex.api.query.CommentQueryV3;
import cn.edu.gxust.blogex.api.service.ArticleService;
import cn.edu.gxust.blogex.api.service.AsyncTaskService;
import cn.edu.gxust.blogex.api.service.BlogSettingService;
import cn.edu.gxust.blogex.api.service.BloggerInfoService;
import cn.edu.gxust.blogex.api.service.CommentService;
import cn.edu.gxust.blogex.api.service.VerifyCodeService;
import cn.edu.gxust.blogex.api.vo.BlogSettingVO;
import cn.edu.gxust.blogex.api.vo.BloggerInfoVO;
import cn.edu.gxust.blogex.api.vo.CommentVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.enums.CommentStatus;
import cn.edu.gxust.blogex.common.enums.PageTypeEnum;
import cn.edu.gxust.blogex.common.exception.CommentNotFoundException;
import cn.edu.gxust.blogex.common.exception.OperationNotAllowedException;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import cn.edu.gxust.blogex.dao.mappers.CommentMapper;
import cn.edu.gxust.blogex.dao.po.CommentPO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/3/18 23:16
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentPO> implements CommentService {

    private final static Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Resource
    private ArticleService articleService;

    @Resource
    private VerifyCodeService verifyCodeService;

    @Value("${blogex.mail.visitor.reply:false}")
    private boolean enableVisitorReplyMail;

    @Value("${blogex.mail.send.admin:false}")
    private boolean mailSendAdmin;

    @Resource
    private AsyncTaskService asyncTaskService;

    @Resource
    private BloggerInfoService bloggerInfoService;

    @Resource
    private BlogSettingService blogSettingService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(CommentVerifyCodeDTO commentVerifyCodeDTO, String userAgentStr, String userIp) {
        /*校验验证码的有效性*/
        VerifyCodeDTO verifyCodeDTO = commentVerifyCodeDTO.getVerifyCode();
        verifyCodeService.verifyCode(verifyCodeDTO);
        CommentDTO commentDTO = commentVerifyCodeDTO.getComment();
        return insert(commentDTO, userAgentStr, userIp, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(CommentDTO commentDTO, String userAgentStr, String userIp, BloggerInfoVO blogInfo) {
        if (null == blogInfo) {
            blogInfo = bloggerInfoService.getBlogInfo();
            if (blogInfo.getUserName().equals(commentDTO.getUserName())) {
                throw new OperationNotAllowedException("不能与博主名同名");
            }
        }
        boolean articleFlag = true;
        if (PageTypeEnum.ARTICLE_COMMENT.getCode().equals(commentDTO.getPageType())) {
            CommentStatus commentStatus = articleService.getcommentStatus(commentDTO.getArticleId());
            if (CommentStatus.CLOSE == commentStatus) {
                throw new OperationNotAllowedException("文章并未开启评论，不允许评论");
            }
        } else {
            articleFlag = false;
        }
        CommentPO commentPO = CommentConvertor.convert(commentDTO);
        commentPO.setCreateTime(new Date());
        commentPO.setUserIp(userIp);
        if (null == userAgentStr) {
            throw new OperationNotAllowedException("no header " + Constants.UA);
        } else {
            try {
                commentPO.setUserAgent(userAgentStr);
                UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
                String osName = userAgent.getOperatingSystem().getName();
                String browserName = userAgent.getBrowser().getName();
                commentPO.setUserOs(osName);
                commentPO.setBrowserName(browserName);
            } catch (Exception e) {
                logger.error("error when parsing User-Agent", e);
            }
        }
        boolean b = commentDTO.getParentId() != null;
        if (b) {
            /*查看父评论的父层级id*/
            Integer parentTierId = findParentTierId(commentDTO.getParentId());
            /*父评论的父层级id为空，证明这个父评论是第一层评论*/
            if (parentTierId == null) {
                commentPO.setParentTierId(commentDTO.getParentId());
            } else {
                /*如果父评论的父层级评论id不为空，说明父评论是一个评论的子评论，
                让该评论和父评论保持在同一个层级上，保证层级最多为2*/
                commentPO.setParentTierId(parentTierId);
            }

        } else {
            /*无父评论id肯定是第一层评论，将其父层级id设置为空*/
            commentPO.setParentTierId(null);
        }
        int count = baseMapper.insert(commentPO);
        if (articleFlag) {
            /*更新文章表里评论数量这个冗余的字段*/
            articleService.updateSumComment(commentPO.getArticleId());
        }
        if (b) {
            if (enableVisitorReplyMail) {
                asyncTaskService.notifyParentReviewer(commentPO);
            } else {
                logger.debug("关闭游客回复发送邮件中，游客之间回复不会发送邮件");
            }
        }
        if (mailSendAdmin) {
            asyncTaskService.notifyAdminIfAddComment(commentPO);
        }
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int bloggerReplyComment(CommentDTO commentDTO, String userAgentStr, String userIp) {
        BloggerInfoVO blogInfo = bloggerInfoService.getBlogInfo();
        String userName = blogInfo.getUserName();
        commentDTO.setUserName(userName);
        BlogSettingVO blogSetting = blogSettingService.getBlogSetting();
        commentDTO.setUserEmail(blogSetting.getBlogMailAddress());
        return insert(commentDTO, userAgentStr, userIp, blogInfo);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByArticleId(Integer articleId) {
        Wrapper<CommentPO> wrapper = Wrappers.<CommentPO>lambdaUpdate().eq(CommentPO::getArticleId, articleId);
        return baseMapper.delete(wrapper);
    }

    @Override
    public Pagination<CommentPO> listPageParent(Integer articleId, Long pageNo, Long pageSize) {
        Page<CommentPO> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<CommentPO> wrapper = Wrappers.<CommentPO>lambdaQuery();
        //查询文章评论类型的评论
        wrapper.eq(CommentPO::getPageType, PageTypeEnum.ARTICLE_COMMENT.getCode());
        if (null != articleId) {
            wrapper.eq(CommentPO::getArticleId, articleId);
        }

        //查询没有父id的评论，父评论
        wrapper.isNull(CommentPO::getParentId);
        wrapper.orderByDesc(CommentPO::getCreateTime);
        Page<CommentPO> selectPage = baseMapper.selectPage(page, wrapper);
        List<CommentPO> records = selectPage.getRecords();
        return new Pagination<>(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal(), records);
    }

    @Override
    public Pagination<CommentPO> listPageParentByPageType(Integer pageType, Long pageNo, Long pageSize) {
        Page<CommentPO> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<CommentPO> wrapper = Wrappers.<CommentPO>lambdaQuery();
        //查询文章评论类型的评论
        wrapper.eq(CommentPO::getPageType, pageType);
        //查询没有父id的评论，父评论
        wrapper.isNull(CommentPO::getParentId);
        wrapper.orderByDesc(CommentPO::getCreateTime);
        Page<CommentPO> selectPage = baseMapper.selectPage(page, wrapper);
        List<CommentPO> records = selectPage.getRecords();
        return new Pagination<>(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal(), records);
    }

    @Override
    public Pagination<CommentVO> listPage(CommentPageQuery query) {
        Long pageNo = query.getPageNo();
        Long pageSize = query.getPageSize();
        Page<CommentPO> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<CommentPO> wrapper = Wrappers.<CommentPO>lambdaQuery();
        if (null != query.getPageType()) {
            wrapper.eq(CommentPO::getPageType, query.getPageType());
        }
        String searchKey = query.getSearchKey();
        if (null != query.getStartTime()) {
            wrapper.ge(CommentPO::getCreateTime, query.getStartTime());
        }
        if (null != query.getEndTime()) {
            wrapper.le(CommentPO::getCreateTime, query.getEndTime());
        }
        if (StringUtils.isNotBlank(searchKey)) {
            wrapper.like(CommentPO::getUserName, searchKey)
                    .or().like(CommentPO::getId, searchKey);
        }
        wrapper.orderByDesc(CommentPO::getCreateTime);
        Page<CommentPO> selectPage = baseMapper.selectPage(page, wrapper);
        List<CommentPO> records = selectPage.getRecords();
        List<CommentVO> resultList = CommentConvertor.convert(records);
        return new Pagination<>(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal(), resultList);
    }

    @Override
    public Pagination<CommentVO> listPageByArticleId(CommentQueryV2 queryV2) {
        Pagination<CommentPO> pagination = listPageParent(queryV2.getArticleId(), queryV2.getPageNo(), queryV2.getPageSize());
        List<CommentPO> commentPOList = pagination.getList();
        List<CommentVO> resultList = CommentConvertor.convert(commentPOList);
        List<Integer> idList = commentPOList.stream().map(CommentPO::getId).collect(Collectors.toList());
        //查询子评论,注意！这里的查询条件是父层级id，而非父评论id，父层级id是为了保证层级最多为2层
        if (!CollectionUtils.isEmpty(idList)) {
            List<CommentPO> childCommentPOList = list(Wrappers.<CommentPO>lambdaQuery().in(CommentPO::getParentTierId, idList));
            //父评论做映射，方便遍历子评论的时候能快速找到父评论，并把子评论接到父评论的子评论列表
            Map<Integer, CommentVO> map = resultList.stream().collect(Collectors.toMap(CommentVO::getId, Function.identity()));
            for (CommentPO commentPO : childCommentPOList) {
                if (null != commentPO.getParentTierId()) {
                    CommentVO parent = map.get(commentPO.getParentTierId());
                    /*如果存着有父层级评论id，但是却找不到父层级评论，说明数据错误，可能是手动删除数据库的数据*/
                    if (parent == null) {
                        continue;
                    }
                    /*如果存着父层级评论id等于子评论id，说明这是错误的数据，可能是人为修改的，为了避免前端递归出现栈溢出，这个问题需要规避掉*/
                    if (parent.getId().intValue() == commentPO.getId().intValue()) {
                        /*出现这种情况把这个评论当作父评论加到列表里*/
                        resultList.add(CommentConvertor.convert(commentPO));
                        continue;
                    }
                    if (parent.getChildList() == null) {
                        parent.setChildList(new ArrayList<>());
                    }
                    parent.getChildList().add(CommentConvertor.convert(commentPO));
                }
            }
        }
        return new Pagination<>(pagination.getPageNo(), pagination.getPageSize(), pagination.getTotal(), resultList);
    }

    @Override
    public Pagination<CommentVO> listPageByPageType(CommentQueryV3 queryV3) {
        Pagination<CommentPO> pagination = listPageParentByPageType(queryV3.getPageType(), queryV3.getPageNo(), queryV3.getPageSize());
        List<CommentPO> commentPOList = pagination.getList();
        List<CommentVO> resultList = CommentConvertor.convert(commentPOList);
        List<Integer> idList = commentPOList.stream().map(CommentPO::getId).collect(Collectors.toList());
        //查询子评论,注意！这里的查询条件是父层级id，而非父评论id，父层级id是为了保证层级最多为2层
        if (!CollectionUtils.isEmpty(idList)) {
            List<CommentPO> childCommentPOList = list(Wrappers.<CommentPO>lambdaQuery().in(CommentPO::getParentTierId, idList));
            //父评论做映射，方便遍历子评论的时候能快速找到父评论，并把子评论接到父评论的子评论列表
            Map<Integer, CommentVO> map = resultList.stream().collect(Collectors.toMap(CommentVO::getId, Function.identity()));
            for (CommentPO commentPO : childCommentPOList) {
                if (null != commentPO.getParentTierId()) {
                    CommentVO parent = map.get(commentPO.getParentTierId());
                    /*如果存着有父层级评论id，但是却找不到父层级评论，说明数据错误，可能是手动删除数据库的数据*/
                    if (parent == null) {
                        continue;
                    }
                    /*如果存着父层级评论id等于子评论id，说明这是错误的数据，可能是人为修改的，为了避免前端递归出现栈溢出，这个问题需要规避掉*/
                    if (parent.getId().intValue() == commentPO.getId().intValue()) {
                        /*出现这种情况把这个评论当作父评论加到列表里*/
                        resultList.add(CommentConvertor.convert(commentPO));
                        continue;
                    }
                    if (parent.getChildList() == null) {
                        parent.setChildList(new ArrayList<>());
                    }
                    parent.getChildList().add(CommentConvertor.convert(commentPO));
                }
            }
        }
        return new Pagination<>(pagination.getPageNo(), pagination.getPageSize(), pagination.getTotal(), resultList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<Integer> idList) {
        if (idList.size() == 0) {
            return 0;
        }
        List<CommentPO> commentPOList = baseMapper.selectList(Wrappers.<CommentPO>lambdaQuery()
                .select(CommentPO::getArticleId, CommentPO::getPageType).in(CommentPO::getId, idList));
        //先将评论的文章id集合查出
        List<Integer> articleIdList = new ArrayList<>();
        for (CommentPO commentPO : commentPOList) {
            Integer articleId = commentPO.getArticleId();
            if (null != articleId) {
                articleIdList.add(articleId);
            }
        }
        //删除
        int count = baseMapper.deleteBatchIds(idList);
        //删除子评论
        count = count + baseMapper.delete(Wrappers.<CommentPO>lambdaUpdate().in(CommentPO::getParentId, idList));
        //更新文章表里评论数量这个冗余的字段
        if (articleIdList.size() > 0) {
            count = count + articleService.updateSumCommentByList(articleIdList);
        }
        return count;
    }

    public Integer findParentTierId(Integer parentId) {
        CommentPO parentComment = baseMapper.selectOne(Wrappers.<CommentPO>lambdaQuery().eq(CommentPO::getId, parentId));
        if (null == parentComment) {
            throw new CommentNotFoundException(parentId);
        }
        return parentComment.getParentTierId();
    }
}
