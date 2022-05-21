package cn.edu.gxust.blogex.gateway.controller;

import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.gateway.service.VerifyCodeService;
import cn.edu.gxust.blogex.gateway.vo.VerificationVO;
import cn.edu.gxust.blogex.gateway.vo.VerifyCodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/3/23 20:20
 */
@Api(tags = "验证码相关")
@RequestMapping(value = "/verify")
@RestController
public class VerifyCodeController {

    @Resource
    private VerifyCodeService verifyCodeService;

    @ApiOperation(value = "获取滑动验证码")
    @GetMapping(value = "/sliderCode")
    public Mono<Result<VerificationVO>> getSliderVCode() {
        return Mono.just(Result.success(verifyCodeService.sliderVerificationCode()));
    }

    @ApiOperation(value = "获取图片验证码")
    @GetMapping("/imageCode")
    public Result<VerifyCodeVO> verifyCode() {
        return Result.success(verifyCodeService.getImageVerifyCode());
    }
}
