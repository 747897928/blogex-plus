package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.dto.UpdatePasswordDTO;
import cn.edu.gxust.blogex.api.dto.UsersDTO;
import cn.edu.gxust.blogex.api.service.UsersService;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Users)表控制层
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:19
 */
@Api(tags = "(Users)相关")
@Validated
@RestController
@RequestMapping(value = "/users")
public class UsersController {

    @Resource
    private UsersService usersService;

    @ApiOperation(value = "修改密码")
    @PutMapping(value = "/authApi/updatePassword")
    public Result<String> updatePassword(@RequestBody @Validated UpdatePasswordDTO updatePasswordDTO, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        int count = usersService.updatePassword(updatePasswordDTO,token);
        return Result.success("操作成功，共有" + count + "条数据受到影响");
    }

}

