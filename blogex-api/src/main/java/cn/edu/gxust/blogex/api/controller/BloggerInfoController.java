package cn.edu.gxust.blogex.api.controller;


import cn.edu.gxust.blogex.api.dto.BloggerInfoDTO;
import cn.edu.gxust.blogex.api.service.BloggerInfoService;
import cn.edu.gxust.blogex.api.vo.BloggerInfoVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

/**
 * 文章控制层
 *
 * @author zhaoyijie
 * @since 2022-03-20 17:11:52
 */
@Api(tags = "博客信息相关")
@Validated
@RestController
@RequestMapping(value = "/bloggerInfo")
public class BloggerInfoController {

    @Resource
    private BloggerInfoService bloggerInfoService;

    @ApiOperation(value = "修改博客信息")
    @PostMapping(value = "/authApi/update")
    public Result<String> update(@RequestBody @Validated BloggerInfoDTO bloggerInfoDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = bloggerInfoService.update(bloggerInfoDTO);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

    @ApiOperation(value = "获取博客信息")
    @GetMapping(value = "/openApi/getBlogInfo")
    public Result<BloggerInfoVO> getBlogInfo() {
        return Result.success(bloggerInfoService.getBlogInfo());
    }


}

