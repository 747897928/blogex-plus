package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.dto.CommentDTO;
import cn.edu.gxust.blogex.api.dto.CommentVerifyCodeDTO;
import cn.edu.gxust.blogex.api.query.CommentPageQuery;
import cn.edu.gxust.blogex.api.query.CommentQueryV2;
import cn.edu.gxust.blogex.api.query.CommentQueryV3;
import cn.edu.gxust.blogex.api.service.CommentService;
import cn.edu.gxust.blogex.api.vo.CommentVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/18 23:13
 */
@Api(tags = "评论相关")
@Validated
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @ApiOperation(value = "新增一条评论")
    @PostMapping(value = "/openApi/insert")
    public Result<String> insert(@Validated @RequestBody CommentVerifyCodeDTO commentVerifyCodeDTO,
                                 @RequestHeader(value = Constants.UA) String userAgentStr,
                                 @ApiIgnore @RequestHeader(value = Constants.X_USER_IP) String userIp) {
        int count = commentService.insert(commentVerifyCodeDTO, userAgentStr, userIp);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "添加评论，该接口是给博主回复用户评论用的")
    @PostMapping(value = "/authApi/blogger/reply")
    public Result<String> bloggerReplyComment(@RequestBody CommentDTO commentDTO,
                                              @RequestHeader(value = Constants.UA) String userAgentStr,
                                              @RequestHeader(value = Constants.AUTHORIZATION) String token,
                                              @ApiIgnore @RequestHeader(value = Constants.X_USER_IP) String userIp) {
        int count = commentService.bloggerReplyComment(commentDTO, userAgentStr, userIp);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "根据文章的id，分页查询该文章的评论")
    @PostMapping(value = "/openApi/listPage/articleId")
    public Result<Pagination<CommentVO>> listPageByArticleId(@RequestBody CommentQueryV2 queryV2) {
        return Result.success(commentService.listPageByArticleId(queryV2));
    }

    @ApiOperation(value = "根据文章的id，分页查询该文章的评论")
    @PostMapping(value = "/openApi/listPage/pageType")
    public Result<Pagination<CommentVO>> listPageByPageType(@RequestBody CommentQueryV3 queryV3) {
        return Result.success(commentService.listPageByPageType(queryV3));
    }

    @ApiOperation(value = "条件查询")
    @PostMapping(value = "/openApi/listPage")
    public Result<Pagination<CommentVO>> listPage(@RequestBody CommentPageQuery query) {
        return Result.success(commentService.listPage(query));
    }

    @ApiOperation(value = "删除一条评论")
    @DeleteMapping(value = "/authApi/{id}")
    public Result<String> delete(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = commentService.getBaseMapper().deleteById(id);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "批量删除评论")
    @DeleteMapping(value = "/authApi/batch")
    public Result<String> batchDelete(@RequestBody List<Integer> idList) {
        int count = commentService.batchDelete(idList);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }
}
