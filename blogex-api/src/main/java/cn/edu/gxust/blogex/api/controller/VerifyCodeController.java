package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.service.VerifyCodeService;
import cn.edu.gxust.blogex.api.vo.VerifyCodeVO;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/3/24 09:50
 */
@Api(tags = "图片验证码相关")
@Validated
@RestController
@RequestMapping(value = "/imageCode/")
public class VerifyCodeController {

    @Resource
    private VerifyCodeService verifyCodeService;

    @ApiOperation(value = "获取验证码")
    @GetMapping("/openApi/verifyCode")
    public Result<VerifyCodeVO> verifyCode() {
        return Result.success(verifyCodeService.getVerifyCode());
    }

}
