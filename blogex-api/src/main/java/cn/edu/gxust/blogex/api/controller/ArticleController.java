package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.dto.ArticleDTO;
import cn.edu.gxust.blogex.api.query.ArticlePageQuery;
import cn.edu.gxust.blogex.api.service.ArticleService;
import cn.edu.gxust.blogex.api.vo.ArticleVO;
import cn.edu.gxust.blogex.api.vo.OverviewVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/2/26 11:07
 */
@Api(tags = "文章相关")
@Validated
@RestController
@RequestMapping(value = "/api/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @ApiOperation(value = "查找一篇文章的详细信息")
    @GetMapping(value = "/openApi/{id}")
    public Result<ArticleVO> getArticle(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id) {
        return Result.success(articleService.getArticle(id));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/openApi/page/query")
    public Result<Pagination<ArticleVO>> listPage(@RequestBody ArticlePageQuery articleQuery) {
        return Result.success(articleService.listPage(articleQuery));
    }

    @ApiOperation(value = "文章增加阅览量")
    @GetMapping(value = "/openApi/increase/viewCount/{articleId}")
    public Result<Integer> increaseArticleViewCount(@NotNull(message = "articleId不能为空") @PathVariable(value = "articleId") Integer articleId) {
        return Result.success(articleService.increaseArticleViewCount(articleId));
    }

    @ApiOperation(value = "文章增加点赞量")
    @GetMapping(value = "/openApi/increase/support/{articleId}")
    public Result<Integer> increaseSupport(@NotNull(message = "articleId不能为空") @PathVariable(value = "articleId") Integer articleId) {
        return Result.success(articleService.increaseSupport(articleId));
    }

    @ApiOperation(value = "获取阅览量前五的文章")
    @GetMapping(value = "/openApi/top/five")
    public Result<List<ArticleVO>> getTOPFiveArticle() {
        return Result.success(articleService.getTOPFiveArticle());
    }


    @ApiOperation(value = "获取评论最多的五篇文章")
    @GetMapping(value = "/openApi/top/sumComment/five")
    public Result<List<ArticleVO>> getTopFiveSumCommentArticle() {
        return Result.success(articleService.getTopFiveSumCommentArticle());
    }

    @ApiOperation(value = "获取博客总览信息，包括：总文章数，总阅览量，总评论，总点赞数")
    @GetMapping(value = "/openApi/blog/overview")
    public Result<OverviewVO> getBlogOverview() {
        return Result.success(articleService.getBlogOverview());
    }

    @ApiOperation(value = "随机获取10条文章记录")
    @GetMapping(value = "/openApi/random/ten")
    public Result<List<ArticleVO>> getRandomArticle() {
        return Result.success(articleService.getRandomArticle());
    }


    @ApiOperation(value = "新增一篇文章", notes = "新增不需要携带id")
    @PostMapping(value = "/authApi/insert")
    public Result<String> addArticle(@RequestBody @Validated ArticleDTO articleDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = articleService.addArticle(articleDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "修改一篇文章", notes = "修改需要携带id")
    @PutMapping(value = "/authApi/update")
    public Result<String> updateArticle(@RequestBody @Validated ArticleDTO articleDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = articleService.updateArticle(articleDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "删除一篇文章")
    @DeleteMapping(value = "/authApi/{id}")
    public Result<String> deleteArticle(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = articleService.deleteArticle(id);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "批量删除文章")
    @DeleteMapping(value = "/authApi/batch")
    public Result<String> batchDelete(@RequestBody List<Integer> idList, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = articleService.batchDelete(idList);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

}
