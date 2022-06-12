package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.ArticleConvertor;
import cn.edu.gxust.blogex.api.dto.ArticleDTO;
import cn.edu.gxust.blogex.api.query.ArticlePageQuery;
import cn.edu.gxust.blogex.api.service.ArticleService;
import cn.edu.gxust.blogex.api.service.ArticleTagMapService;
import cn.edu.gxust.blogex.api.service.ClassifyService;
import cn.edu.gxust.blogex.api.service.CommentService;
import cn.edu.gxust.blogex.api.service.TagService;
import cn.edu.gxust.blogex.api.vo.ArticleVO;
import cn.edu.gxust.blogex.api.vo.OverviewVO;
import cn.edu.gxust.blogex.common.enums.ArticleStatus;
import cn.edu.gxust.blogex.common.enums.CommentStatus;
import cn.edu.gxust.blogex.common.exception.ArticleNotFoundException;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.mappers.ArticleMapper;
import cn.edu.gxust.blogex.dao.po.ArticlePO;
import cn.edu.gxust.blogex.dao.po.ClassifyPO;
import cn.edu.gxust.blogex.dao.po.OverviewPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/2/26 11:26
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticlePO> implements ArticleService {

    private final static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Resource
    private ArticleTagMapService articleTagMapService;

    @Resource
    private ClassifyService classifyService;

    @Resource
    private TagService tagService;

    @Resource
    private CommentService commentService;

    /**
     * 摘要的最大长度
     */
    private final int SNIPPET_MAX_LEN = 250;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addArticle(ArticleDTO articleDTO) {
        List<Integer> tagIdList = articleDTO.getTagIdList();
        if (CollectionUtils.isEmpty(tagIdList)) {
            throw new IllegalArgumentException("标签为空！");
        }
        ClassifyPO classifyPO = classifyService.checkExist(articleDTO.getClassifyId());
        ArticlePO articlePo = ArticleConvertor.convert(articleDTO);
        /*if (null == articlePo.getSnippet()) {
            String content = articlePo.getContent();
            int length = content.length();
            if (length > SNIPPET_MAX_LEN) {
                articlePo.setSnippet(content.substring(0, SNIPPET_MAX_LEN));
            } else {
                articlePo.setSnippet(content.substring(0, length));
            }
        }*/
        articlePo.setClassifyName(classifyPO.getClassifyName());
        articlePo.setCreateTime(new Date());
        articlePo.setUpdateTime(new Date());
        int count = baseMapper.insert(articlePo);
        articleTagMapService.batchInsert(tagIdList, articlePo.getId());
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateArticle(ArticleDTO articleDTO) {
        Integer id = articleDTO.getId();
        if (null == id) {
            throw new IllegalArgumentException("非法参数，id不能为空");
        }
        checkExist(id);
        ArticlePO articlePO = ArticleConvertor.convert(articleDTO);
        ClassifyPO classifyPO = classifyService.checkExist(articleDTO.getClassifyId());
        articlePO.setClassifyName(classifyPO.getClassifyName());
        int count = baseMapper.updateById(articlePO);
        count = count + articleTagMapService.update(articleDTO.getTagIdList(), id);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int increaseArticleViewCount(Integer articleId) {
        return baseMapper.update(null, Wrappers.<ArticlePO>lambdaUpdate().eq(ArticlePO::getId, articleId).setSql("view_count = view_count+1"));
    }

    @Override
    public ArticleVO getArticle(Integer id) {
        ArticlePO articlePO = checkExist(id);
        ArticleVO articleVO = ArticleConvertor.convert(articlePO);
        articleVO.setTagList(tagService.getByArticleId(id));
        return articleVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteArticle(Integer id) {
        // 删除文章标签映射记录
        int count = articleTagMapService.deleteByArticleId(id);
        // 删除评论
        count = count + commentService.deleteByArticleId(id);
        // 删除文章
        count = count + baseMapper.deleteById(id);
        return count;
    }

    @Override
    public CommentStatus getcommentStatus(Integer articleId) {
        ArticlePO articlePO = checkExist(articleId);
        return CommentStatus.of(articlePO.getCommentStatus());
    }

    @Override
    public ArticlePO checkExist(Integer articleId) {
        ArticlePO articlePO = baseMapper.selectById(articleId);
        if (null == articlePO) {
            throw new ArticleNotFoundException(articleId);
        }
        return articlePO;
    }

    @Override
    public Pagination<ArticleVO> listPage(ArticlePageQuery query) {
        Long pageNo = query.getPageNo();
        Long pageSize = query.getPageSize();
        if (null != query.getTagId()) {
            Pagination<Integer> pagination = articleTagMapService.listPageArticleIdByTagId(query.getTagId(), query.getPageNo(), query.getPageSize());
            List<Integer> paginationList = pagination.getList();
            if (CollectionUtils.isEmpty(paginationList)) {
                return new Pagination<>(pagination.getPageNo(), pagination.getPageSize(), 0L, new ArrayList<>());
            }
            List<ArticlePO> articlePOList = baseMapper.listQuery(paginationList,
                    query.getSearchKey(), query.getClassifyId(),
                    query.getContentType(), query.getArticleStatus(),
                    query.getCommentStatus(), query.getPostType(),
                    query.getStartTime(), query.getEndTime());
            List<ArticleVO> resultList = ArticleConvertor.convert(articlePOList);
            return new Pagination<>(pagination.getPageNo(), pagination.getPageSize(), pagination.getTotal(), resultList);
        } else {
            PageHelper.startPage(pageNo.intValue(), pageSize.intValue());
            List<ArticlePO> articlePOList = baseMapper.listQuery(null,
                    query.getSearchKey(), query.getClassifyId(),
                    query.getContentType(), query.getArticleStatus(),
                    query.getCommentStatus(), query.getPostType(),
                    query.getStartTime(), query.getEndTime()
            );
            PageInfo<ArticlePO> pageInfo = new PageInfo<>(articlePOList);
            List<ArticlePO> records = pageInfo.getList();
            List<ArticleVO> resultList = ArticleConvertor.convert(records);
            return new Pagination<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), resultList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<Integer> idList) {
        int count = 0;
        for (Integer id : idList) {
            count = count + deleteArticle(id);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int increaseSupport(Integer articleId) {
        return baseMapper.update(null, Wrappers.<ArticlePO>lambdaUpdate().eq(ArticlePO::getId, articleId).setSql("support_count = support_count+1"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateClassifyName(Integer classifyId) {
        return baseMapper.updateClassifyName(classifyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSumComment(Integer articleId) {
        return baseMapper.updateSumComment(articleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSumCommentByList(List<Integer> articleIdList) {
        return baseMapper.updateSumCommentByList(articleIdList);
    }

    @Override
    public List<ArticlePO> findByInId(List<Integer> idList) {
        if (idList.size() > 0) {
            return baseMapper.selectList(Wrappers.<ArticlePO>lambdaQuery().in(ArticlePO::getId, idList)
                    .orderByAsc(ArticlePO::getPriority).orderByDesc(ArticlePO::getCreateTime)
            );
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ArticleVO> getTOPFiveArticle() {
        LambdaQueryWrapper<ArticlePO> wrapper = Wrappers.<ArticlePO>lambdaQuery();
        wrapper.select(ArticlePO::getId, ArticlePO::getTitle, ArticlePO::getViewCount,
                        ArticlePO::getSupportCount, ArticlePO::getSumComment)
                .eq(ArticlePO::getArticleStatus, ArticleStatus.PUBLISH.getCode())
                .orderByDesc(ArticlePO::getViewCount).last("limit 5");
        List<ArticlePO> articlePOList = baseMapper.selectList(wrapper);
        return ArticleConvertor.convert(articlePOList);
    }

    @Override
    public List<ArticleVO> getTopFiveSumCommentArticle() {
        LambdaQueryWrapper<ArticlePO> wrapper = Wrappers.<ArticlePO>lambdaQuery();
        wrapper.select(ArticlePO::getId, ArticlePO::getTitle, ArticlePO::getViewCount,
                        ArticlePO::getSupportCount, ArticlePO::getSumComment)
                .eq(ArticlePO::getArticleStatus, ArticleStatus.PUBLISH.getCode())
                .orderByDesc(ArticlePO::getSumComment).last("limit 5");
        List<ArticlePO> articlePOList = baseMapper.selectList(wrapper);
        return ArticleConvertor.convert(articlePOList);
    }

    @Override
    public OverviewVO getBlogOverview() {
        OverviewPO overviewPO = baseMapper.getBlogOverview();
        return OverviewVO.newBuilder()
                .sumArticle(overviewPO.getSumArticle())
                .sumComment(overviewPO.getSumComment())
                .sumView(overviewPO.getSumView())
                .sumSupport(overviewPO.getSumSupport())
                .build();
    }

    @Override
    public List<ArticleVO> getRandomArticle() {
        List<ArticlePO> articlePOList = baseMapper.getRandomArticle();
        return ArticleConvertor.convert(articlePOList);
    }

}
