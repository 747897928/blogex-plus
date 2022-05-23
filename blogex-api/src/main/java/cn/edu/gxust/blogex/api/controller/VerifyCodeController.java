package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.service.VerifyCodeService;
import cn.edu.gxust.blogex.api.vo.VerificationVO;
import cn.edu.gxust.blogex.api.vo.VerifyCodeVO;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/3/24 09:50
 */
@Api(tags = "图片验证码相关")
@Validated
@RestController
public class VerifyCodeController {

    @Resource
    private VerifyCodeService verifyCodeService;

    @ApiOperation(value = "获取验证码(图片验证码，评论专用)")
    @GetMapping("/api/imageCode/openApi/verifyCode")
    public Result<VerifyCodeVO> verifyCode() {
        return Result.success(verifyCodeService.getVerifyCode());
    }

    @ApiOperation(value = "获取滑动验证码")
    @GetMapping(value = "/verify/sliderCode")
    public Result<VerificationVO> getSliderVCode() {
        return Result.success(verifyCodeService.sliderVerificationCode());
    }

    @ApiOperation(value = "获取图片验证码(登录专用)")
    @GetMapping("/verify/imageCode")
    public Result<VerifyCodeVO> getLoginImageVerifyCode() {
        return Result.success(verifyCodeService.getLoginImageVerifyCode());
    }

}
