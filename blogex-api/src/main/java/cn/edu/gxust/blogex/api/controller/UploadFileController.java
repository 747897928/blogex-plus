package cn.edu.gxust.blogex.api.controller;

import cn.edu.gxust.blogex.api.service.UploadFileService;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/2/26 23:13
 */
@Api(tags = "上传文件相关")
@RestController
@RequestMapping(value = "/uploads/upload")
public class UploadFileController {

    @Resource
    private UploadFileService uploadFileService;

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/authApi/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> uploadImage(@RequestParam(value = "file") @RequestPart MultipartFile multipartFile, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Result.success(uploadFileService.uploadImage(multipartFile));
    }

    @ApiOperation(value = "上传音乐")
    @PostMapping(value = "/authApi/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> uploadAudio(@RequestParam(value = "file") @RequestPart MultipartFile multipartFile, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Result.success(uploadFileService.uploadAudio(multipartFile));
    }

}
