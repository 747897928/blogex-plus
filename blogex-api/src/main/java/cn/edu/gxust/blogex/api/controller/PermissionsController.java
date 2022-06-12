package cn.edu.gxust.blogex.api.controller;


import cn.edu.gxust.blogex.api.service.PermissionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Permissions)表控制层
 *
 * @author zhaoyijie
 * @since 2022-06-11 19:12:18
 */
@Api(tags = "(Permissions)相关")
@Validated
@RestController
@RequestMapping(value = "/permissions")
public class PermissionsController {

    @Resource
    private PermissionsService permissionsService;


}

