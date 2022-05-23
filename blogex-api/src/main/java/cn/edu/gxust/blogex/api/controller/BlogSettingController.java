package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.dto.BlogSettingDTO;
import cn.edu.gxust.blogex.api.service.BlogSettingService;
import cn.edu.gxust.blogex.api.vo.BlogSettingVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 博客配置相关控制层
 *
 * @author zhaoyijie
 * @since 2022-05-15 12:40:55
 */
@Api(tags = "博客配置相关")
@Validated
@RestController
@RequestMapping(value = "/api/blogSetting")
public class BlogSettingController {

    @Resource
    private BlogSettingService blogSettingService;

    @ApiOperation(value = "修改博客配置")
    @PostMapping(value = "/authApi/update")
    public Result<String> update(@RequestBody @Validated BlogSettingDTO blogSettingDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = blogSettingService.update(blogSettingDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "获取博客配置")
    @GetMapping(value = "/openApi/getBlogSetting")
    public Result<BlogSettingVO> getBlogInfo() {
        return Result.success(blogSettingService.getBlogSetting());
    }


}

