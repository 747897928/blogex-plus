package cn.edu.gxust.blogex.gateway.controller;

import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.gateway.dto.AuthDTO;
import cn.edu.gxust.blogex.gateway.entity.BlogUser;
import cn.edu.gxust.blogex.gateway.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/3/22 09:20
 */
@Api(tags = "登录相关")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public Mono<Result<BlogUser>> login(@RequestBody AuthDTO authDTO) {
        return authService.login(authDTO);
    }

    @ApiOperation(value = "登出")
    @GetMapping(value = "/logout")
    public Mono<Result<String>> logout(@RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return authService.logout(token);
    }

}
