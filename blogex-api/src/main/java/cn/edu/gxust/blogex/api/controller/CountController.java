package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.service.CountService;
import cn.edu.gxust.blogex.api.vo.CountVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 查看网站浏览次数
 *
 * @author zhaoyijie
 * @since 2022/4/8 16:56
 */
@Api(tags = "网站浏览次数")
@RestController
@RequestMapping(value = "/api/count")
public class CountController {

    @Resource
    private CountService countService;

    @ApiOperation(value = "获取博客浏览的详细数据")
    @GetMapping(value = "/authApi")
    public Result<CountVO> count(@RequestHeader(Constants.AUTHORIZATION) String token) {
        return Result.success(countService.count());
    }

    @ApiOperation(value = "增加博客浏览")
    @PutMapping(value = "/openApi/increase")
    public Result<Long> increase() {
        return Result.success(countService.increase());
    }
}
