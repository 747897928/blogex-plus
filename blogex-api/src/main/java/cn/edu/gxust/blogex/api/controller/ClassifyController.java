package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.dto.ClassifyDTO;
import cn.edu.gxust.blogex.api.query.ClassifyPageQuery;
import cn.edu.gxust.blogex.api.dto.UpdateClassifyDTO;
import cn.edu.gxust.blogex.api.service.ClassifyService;
import cn.edu.gxust.blogex.api.vo.ClassifyVO;
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
 * @since 2022/3/19 14:26
 */
@Api(tags = "分类相关")
@Validated
@RestController
@RequestMapping(value = "/api/classify")
public class ClassifyController {

    @Resource
    private ClassifyService classifyService;

    @ApiOperation(value = "分页查找分类")
    @PostMapping(value = "/openApi/listPage")
    public Result<Pagination<ClassifyVO>> listPage(@RequestBody @Validated ClassifyPageQuery classifyQuery) {
        return Result.success(classifyService.listPage(classifyQuery));
    }

    @ApiOperation(value = "根据id查询对应的分类")
    @GetMapping(value = "/openApi/detail/{id}")
    public Result<ClassifyVO> detail(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id) {
        return Result.success(classifyService.detail(id));
    }

    @ApiOperation(value = "查询全部分类及引用该分类文章的数量")
    @GetMapping(value = "/openApi/queryAll")
    public Result<List<ClassifyVO>> queryAll() {
        return Result.success(classifyService.selectAll());
    }

    @ApiOperation(value = "新增一个分类")
    @PostMapping(value = "/authApi")
    public Result<String> insert(@RequestBody @Validated ClassifyDTO classifyDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = classifyService.insert(classifyDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "修改一个分类")
    @PutMapping(value = "/authApi")
    public Result<String> update(@RequestBody @Validated UpdateClassifyDTO updateClassifyDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = classifyService.update(updateClassifyDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "删除一个分类")
    @DeleteMapping(value = "/authApi/{id}")
    public Result<String> delete(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = classifyService.getBaseMapper().deleteById(id);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "批量删除分类")
    @DeleteMapping(value = "/authApi/batch/delete")
    public Result<String> batchDelete(@RequestBody List<Integer> idList, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = classifyService.batchDelete(idList);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

}
