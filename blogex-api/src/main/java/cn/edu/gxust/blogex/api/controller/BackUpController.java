package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.service.SQLBackUpService;
import cn.edu.gxust.blogex.api.vo.VerifyCodeVO;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/5/15 20:05
 */
@Api(tags = "备份相关")
@Validated
@RestController
@RequestMapping(value = "/api/backup")
public class BackUpController {

    @Resource
    private SQLBackUpService sqlBackUpService;

    @ApiOperation(value = "备份sql", notes = "sql文件将会压缩发送到邮件，解压密码将会在这个vo图片的base64内")
    @PostMapping(value = "/authApi/sql")
    public Result<VerifyCodeVO> backUpSql(@RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Result.success(sqlBackUpService.backUpSql());
    }

}
