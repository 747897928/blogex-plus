package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.service.RsaService;
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
 * @since 2022/6/1 20:10
 */
@Api(tags = "rsa相关")
@Validated
@RestController
@RequestMapping(value = "/api/rsa")
public class RsaController {

    @Resource
    private RsaService rsaService;

    @ApiOperation(value = "获取rsa公钥")
    @GetMapping(value = "/openApi/getRsaPublicKey")
    public Result<String> getRsaPublicKey() {
        return Result.success(rsaService.getRsaPublicKey());
    }

}
