package cn.edu.gxust.blogex.upload.controller;

import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.upload.dto.Live2dDTO;
import cn.edu.gxust.blogex.upload.query.Live2dPageQuery;
import cn.edu.gxust.blogex.upload.service.Live2dService;
import cn.edu.gxust.blogex.upload.vo.Live2dVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
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
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoyijie
 * @since 2022/4/23 10:44
 */
@Api(tags = "live2d相关")
@Validated
@RestController
@RequestMapping(value = "/live2d")
public class Live2dController {

    @Resource
    private Live2dService live2dService;

    @ApiOperation(value = "上传live2d的压缩包,目前仅支持.zip")
    @PostMapping(value = "/authApi/live2d/zip", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Result<Live2dVO>> uploadLive2d(@RequestPart("file") FilePart filePart, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Mono.just(Result.success(live2dService.uploadLive2d(filePart)));
    }

    @ApiOperation(value = "新建live2d")
    @PostMapping(value = "/authApi/live2d/insert")
    public Mono<Result<String>> insertLive2d(@RequestBody @Validated Live2dDTO live2dDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = live2dService.insertLive2d(live2dDTO);
        return Mono.just(Result.success("操作成功，共有" + count + "条数据受到影响"));
    }

    @ApiOperation(value = "更新live2d")
    @PutMapping(value = "/authApi/live2d/update")
    public Mono<Result<String>> updateLive2d(@RequestBody @Validated Live2dDTO live2dDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = live2dService.updateLive2d(live2dDTO);
        return Mono.just(Result.success("操作成功，共有" + count + "条数据受到影响"));
    }


    @ApiOperation(value = "下载live2d")
    @GetMapping(value = "/authApi/download/{id}")
    @ApiImplicitParams({@ApiImplicitParam(value = "live2dId", name = "id", required = true)})
    public Mono<Void> downloadLive2d(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id, @RequestHeader(value = Constants.AUTHORIZATION, required = false) String token, @ApiIgnore ServerHttpResponse response) {
        return live2dService.downloadLive2d(id, response);
    }


    @ApiOperation(value = "通过id查找对应的live2d")
    @GetMapping(value = "/openApi/detail/{id}")
    public Mono<Result<Live2dVO>> getLive2dById(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id) {
        return Mono.just(Result.success(live2dService.getLive2dById(id)));
    }

    @ApiOperation(value = "删除对应的live2d")
    @DeleteMapping(value = "/authApi/delete/{id}")
    public Mono<Result<String>> deleteLive2d(@NotNull(message = "id不能为空") @PathVariable(value = "id") Integer id, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = live2dService.deleteLive2d(id);
        return Mono.just(Result.success("操作成功，共有" + count + "条数据受到影响"));
    }

    @Deprecated
    @ApiOperation(value = "获取live2d列表")
    @GetMapping(value = "/openApi/live2d")
    public Mono<Result<List<Live2dVO>>> listLive2d() {
        return Mono.just(Result.success(live2dService.listLive2d()));
    }

    @ApiOperation(value = "随机获取10条live2d列表，这里不包括下架的模型")
    @GetMapping(value = "/openApi/getRandomTenList")
    public Mono<Result<List<Live2dVO>>> getRandomTenList() {
        return Mono.just(Result.success(live2dService.getRandomTenList()));
    }

    @ApiOperation(value = "分页获取live2d列表")
    @PostMapping(value = "/authApi/listPage")
    public Mono<Result<Pagination<Live2dVO>>> listPage(@RequestBody Live2dPageQuery query) {
        return Mono.just(Result.success(live2dService.listPage(query)));
    }

    @ApiOperation(value = "获取live2d的词库")
    @GetMapping(value = "/openApi/getModelMessage")
    public Mono<Result<String>> getModelMessage() {
        return Mono.just(Result.success(live2dService.getModelMessage()));
    }

    @ApiOperation(value = "编辑live2d的词库")
    @PostMapping(value = "/openApi/editModelMessage")
    public Mono<Result<Void>> editModelMessage(@RequestBody Object o) {
        live2dService.editModelMessage(o);
        return Mono.just(Result.success());
    }

    @ApiOperation(value = "备份live2d")
    @PostMapping(value = "/authApi/backupLive2d")
    public Mono<Result<String>> backupLive2d(@RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Mono.just(Result.success(live2dService.backupLive2d()));
    }

}
