package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.service.ArticleTagMapService;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.dao.mappers.ArticleTagMapMapper;
import cn.edu.gxust.blogex.dao.po.ArticleTagMapPO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaoyijie
 * @since 2022/3/12 10:11
 */
@Service
public class ArticleTagMapServiceImpl extends ServiceImpl<ArticleTagMapMapper, ArticleTagMapPO> implements ArticleTagMapService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchInsert(Collection<Integer> tagIdCollection, Integer articleId) {
        List<ArticleTagMapPO> articleTagMapPOList = tagIdCollection.stream()
                .map(
                        tagId -> ArticleTagMapPO.newBuilder().articleId(articleId).tagId(tagId)
                        .createTime(new Date()).updateTime(new Date()).build()
                )
                .collect(Collectors.toList());
        return saveOrUpdateBatch(articleTagMapPOList);
    }

    @Override
    public List<Integer> getByArticleId(Integer articleId) {
        Wrapper<ArticleTagMapPO> wrapper = Wrappers.<ArticleTagMapPO>lambdaQuery().select(ArticleTagMapPO::getTagId)
                .eq(ArticleTagMapPO::getArticleId, articleId);
        List<ArticleTagMapPO> articleTagMapPOList = baseMapper.selectList(wrapper);
        return articleTagMapPOList.stream().map(ArticleTagMapPO::getTagId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByArticleId(Integer articleId) {
        return baseMapper.delete(Wrappers.<ArticleTagMapPO>lambdaUpdate().eq(ArticleTagMapPO::getArticleId, articleId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Integer tagId, Integer articleId) {
        return baseMapper.delete(Wrappers.<ArticleTagMapPO>lambdaUpdate().eq(ArticleTagMapPO::getTagId, tagId)
                .eq(ArticleTagMapPO::getArticleId, articleId));
    }

    @Override
    public List<Integer> getArticleIdByTagId(Integer tagId) {
        Wrapper<ArticleTagMapPO> wrapper = Wrappers.<ArticleTagMapPO>lambdaQuery().eq(ArticleTagMapPO::getTagId, tagId);
        List<ArticleTagMapPO> articleTagMapPOList = baseMapper.selectList(wrapper);
        return articleTagMapPOList.stream().map(ArticleTagMapPO::getArticleId).collect(Collectors.toList());
    }

    @Override
    public Pagination<Integer> listPageArticleIdByTagId(Integer tagId, long pageNo, long pageSize) {
        Page<ArticleTagMapPO> page = new Page<>(pageNo, pageSize);
        Wrapper<ArticleTagMapPO> wrapper = Wrappers.<ArticleTagMapPO>lambdaQuery().eq(ArticleTagMapPO::getTagId, tagId);
        Page<ArticleTagMapPO> articleTagMapPOPage = page(page, wrapper);
        List<ArticleTagMapPO> records = articleTagMapPOPage.getRecords();
        List<Integer> articleIdList = records.stream().map(ArticleTagMapPO::getArticleId).collect(Collectors.toList());
        return new Pagination<>(articleTagMapPOPage.getCurrent(), articleTagMapPOPage.getSize(), articleTagMapPOPage.getTotal(), articleIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Collection<Integer> tagIdCollection, Integer articleId) {
        if (CollectionUtils.isEmpty(tagIdCollection)) {
            throw new IllegalArgumentException("至少需要一个标签！");
        }
        if (tagIdCollection.size() > 5) {
            throw new IllegalArgumentException("最多5个标签！");
        }
        List<Integer> tagIdList = new ArrayList<>(tagIdCollection);
        int k = 0;
        boolean isUpdate = false;
        List<Integer> oldTagIdList = getByArticleId(articleId);
        int x = -1;
        for (Integer oldTagId : oldTagIdList) {
            for (int j = 0; j < tagIdList.size(); j++) {
                Integer newTagId = tagIdList.get(j);
                if (oldTagId.equals(newTagId)) {
                    x = j;
                    break;
                }
            }
            if (x == -1) {
                /* 如果找不到，证明这个标签需要删除 */
                k = k + delete(oldTagId, articleId);
                System.out.println("delete TagId = " + oldTagId + "  articleId:" + articleId);
                isUpdate = true;
            } else {
                /* 如果找得到，说明这个元素不需要插入，就把这个元素从插入列表删除 */
                Integer remove = tagIdList.remove(x);
                System.out.println(remove);
                x = -1;
            }
        }
        /* 最后，如果插入列表还有元素，说明有新标签插入，将其插入 */
        if (tagIdList.size() > 0) {
            batchInsert(tagIdList, articleId);
            System.out.println("insert:" + tagIdList);
            isUpdate = true;
        }
        if (!isUpdate) {
            System.out.println("标签并未改变，无需修改");
        }
        return k;
    }

}
