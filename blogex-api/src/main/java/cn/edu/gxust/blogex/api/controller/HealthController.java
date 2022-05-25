package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.common.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoyijie
 * @since 2022/5/25 09:44
 */
@Api(tags = "健康检查相关")
@RestController
@RequestMapping(value = "/api/health")
public class HealthController {

    @ApiOperation(value = "健康检查")
    @GetMapping(value = "/openApi")
    public Result<String> increase() {
        return Result.success(DateUtils.formatDatetimeFull().format(System.currentTimeMillis()));
    }

}
