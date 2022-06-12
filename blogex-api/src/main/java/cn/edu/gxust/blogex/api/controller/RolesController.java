package cn.edu.gxust.blogex.api.controller;


import cn.edu.gxust.blogex.api.service.RolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Roles)表控制层
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:19
 */
@Api(tags = "(Roles)相关")
@Validated
@RestController
@RequestMapping(value = "/roles")
public class RolesController {

    @Resource
    private RolesService rolesService;

}

