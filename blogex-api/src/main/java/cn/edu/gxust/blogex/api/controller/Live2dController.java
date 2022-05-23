package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.dto.Live2dDTO;
import cn.edu.gxust.blogex.api.query.Live2dPageQuery;
import cn.edu.gxust.blogex.api.service.Live2dService;
import cn.edu.gxust.blogex.api.vo.Live2dVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/4/23 10:44
 */
@Api(tags = "live2d相关")
@Validated
@RestController
@RequestMapping(value = "/uploads/live2d")
public class Live2dController {

    @Resource
    private Live2dService live2dService;

    @ApiOperation(value = "上传live2d的压缩包,目前仅支持.zip")
    @PostMapping(value = "/authApi/live2d/zip", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Live2dVO> uploadLive2d(@RequestParam(value = "file") @RequestPart MultipartFile multipartFile, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Result.success(live2dService.uploadLive2d(multipartFile));
    }

    @ApiOperation(value = "新建live2d")
    @PostMapping(value = "/authApi/live2d/insert")
    public Result<String> insertLive2d(@RequestBody @Validated Live2dDTO live2dDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = live2dService.insertLive2d(live2dDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "更新live2d")
    @PutMapping(value = "/authApi/live2d/update")
    public Result<String> updateLive2d(@RequestBody @Validated Live2dDTO live2dDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = live2dService.updateLive2d(live2dDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }


    @ApiOperation(value = "下载live2d")
    @GetMapping(value = "/authApi/download/{id}")
    @ApiImplicitParams({@ApiImplicitParam(value = "live2dId", name = "id", required = true)})
    public void downloadLive2d(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id, @RequestHeader(value = Constants.AUTHORIZATION, required = false) String token, @ApiIgnore HttpServletResponse response) {
        live2dService.downloadLive2d(id, response);
    }


    @ApiOperation(value = "通过id查找对应的live2d")
    @GetMapping(value = "/openApi/detail/{id}")
    public Result<Live2dVO> getLive2dById(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id) {
        return Result.success(live2dService.getLive2dById(id));
    }

    @ApiOperation(value = "删除对应的live2d")
    @DeleteMapping(value = "/authApi/delete/{id}")
    public Result<String> deleteLive2d(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = live2dService.deleteLive2d(id);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @Deprecated
    @ApiOperation(value = "获取live2d列表")
    @GetMapping(value = "/openApi/live2d")
    public Result<List<Live2dVO>> listLive2d() {
        return Result.success(live2dService.listLive2d());
    }

    @ApiOperation(value = "随机获取10条live2d列表，这里不包括下架的模型")
    @GetMapping(value = "/openApi/getRandomTenList")
    public Result<List<Live2dVO>> getRandomTenList() {
        return Result.success(live2dService.getRandomTenList());
    }

    @ApiOperation(value = "分页获取live2d列表")
    @PostMapping(value = "/authApi/listPage")
    public Result<Pagination<Live2dVO>> listPage(@RequestBody Live2dPageQuery query) {
        return Result.success(live2dService.listPage(query));
    }

    @ApiOperation(value = "获取live2d的词库")
    @GetMapping(value = "/openApi/getModelMessage")
    public Result<String> getModelMessage() {
        return Result.success(live2dService.getModelMessage());
    }

    @ApiOperation(value = "编辑live2d的词库")
    @PostMapping(value = "/openApi/editModelMessage")
    public void editModelMessage(@RequestBody Object o) {
        live2dService.editModelMessage(o);
    }

    @ApiOperation(value = "备份live2d")
    @PostMapping(value = "/authApi/backupLive2d")
    public Result<String> backupLive2d(@RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Result.success(live2dService.backupLive2d());
    }

}
