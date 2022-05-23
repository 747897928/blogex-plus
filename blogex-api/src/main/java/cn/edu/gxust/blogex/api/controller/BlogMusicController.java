package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.dto.BlogMusicDTO;
import cn.edu.gxust.blogex.api.query.BlogMusicPageQuery;
import cn.edu.gxust.blogex.api.service.BlogMusicService;
import cn.edu.gxust.blogex.api.vo.BlogMusicVO;
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
 * (BlogMusic)表控制层
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
@Api(tags = "博客音乐相关")
@Validated
@RestController
@RequestMapping(value = "/api/blogMusic")
public class BlogMusicController {

    @Resource
    private BlogMusicService blogMusicService;

    @ApiOperation(value = "获取所有音乐")
    @GetMapping(value = "/openApi/selectAll")
    public Result<List<BlogMusicVO>> selectAll() {
        return Result.success(blogMusicService.selectAll());
    }

    @ApiOperation(value = "新增音乐")
    @PostMapping(value = "/authApi/insert")
    public Result<String> insert(@RequestBody @Validated BlogMusicDTO blogMusicDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = blogMusicService.insert(blogMusicDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "获取分页后的音乐列表")
    @PostMapping(value = "/authApi/listPage")
    public Result<Pagination<BlogMusicVO>> listPage(@RequestBody BlogMusicPageQuery query, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Result.success(blogMusicService.listPage(query));
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping(value = "/authApi/batch/delete")
    public Result<String> batchDelete(@RequestBody List<Integer> idList, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = blogMusicService.batchDelete(idList);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "更新音乐")
    @PutMapping(value = "/authApi/update")
    public Result<String> update(@RequestBody BlogMusicDTO blogMusicDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = blogMusicService.update(blogMusicDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

}

