package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.dto.BlogLinkDTO;
import cn.edu.gxust.blogex.api.query.BlogLinkPageQuery;
import cn.edu.gxust.blogex.api.service.BlogLinkService;
import cn.edu.gxust.blogex.api.vo.BlogLinkVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友联(BlogLink)表控制层
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
@Api(tags = "友联相关")
@Validated
@RestController
@RequestMapping(value = "/blogLink")
public class BlogLinkController {

    @Resource
    private BlogLinkService blogLinkService;

    @ApiOperation(value = "获取分页后的友联列表")
    @PostMapping(value = "/openApi/listPage")
    public Result<Pagination<BlogLinkVO>> listPage(@RequestBody BlogLinkPageQuery query) {
        return Result.success(blogLinkService.listPage(query));
    }

    @ApiOperation(value = "获取所有友联")
    @GetMapping(value = "/openApi/listPage")
    public Result<List<BlogLinkVO>> selectAll() {
        return Result.success(blogLinkService.selectAll());
    }

    @ApiOperation(value = "新增友链")
    @PostMapping(value = "/authApi/insert")
    public Result<String> insert(@RequestBody BlogLinkDTO blogLinkDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = blogLinkService.insert(blogLinkDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "/authApi/batch/delete")
    public Result<String> batchDelete(@RequestBody List<Integer> idList, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = blogLinkService.batchDelete(idList);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "更新友联")
    @PutMapping(value = "/authApi/update")
    public Result<String> update(@RequestBody BlogLinkDTO blogLinkDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = blogLinkService.update(blogLinkDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

}

