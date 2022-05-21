package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.dto.TagDTO;
import cn.edu.gxust.blogex.api.query.TagPageQuery;
import cn.edu.gxust.blogex.api.dto.UpdateTagDTO;
import cn.edu.gxust.blogex.api.service.TagService;
import cn.edu.gxust.blogex.api.vo.TagVO;
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
 * @since 2022/3/19 15:43
 */
@Api(tags = "标签相关")
@Validated
@RestController
@RequestMapping(value = "/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @ApiOperation(value = "新增一个标签")
    @PostMapping(value = "/authApi")
    public Result<String> insert(@RequestBody @Validated TagDTO tagDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = tagService.insert(tagDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "修改一个标签")
    @PutMapping(value = "/authApi")
    public Result<String> update(@RequestBody @Validated UpdateTagDTO updateTagDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = tagService.update(updateTagDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "查询全部标签")
    @GetMapping(value = "/openApi/queryAll")
    public Result<List<TagVO>> queryAll() {
        return Result.success(tagService.queryAll());
    }

    @ApiOperation(value = "根据id查询对应的标签")
    @GetMapping(value = "/openApi/detail/{id}")
    public Result<TagVO> detail(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id) {
        return Result.success(tagService.detail(id));
    }

    @ApiOperation(value = "查找标签")
    @PostMapping(value = "/authApi/listPage")
    public Result<Pagination<TagVO>> listPage(@RequestBody @Validated TagPageQuery tagQuery, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Result.success(tagService.listPage(tagQuery));
    }

    @ApiOperation(value = "删除一个标签")
    @DeleteMapping(value = "/authApi/{id}")
    public Result<String> delete(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = tagService.getBaseMapper().deleteById(id);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "批量删除标签")
    @DeleteMapping(value = "/authApi/batch")
    public Result<String> batchDelete(@RequestBody List<Integer> idList, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = tagService.batchDelete(idList);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

}
