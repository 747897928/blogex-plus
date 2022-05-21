package cn.edu.gxust.blogex.upload.controller;

import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.upload.service.UploadFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author zhaoyijie
 * @since 2022/2/26 23:13
 */
@Api(tags = "上传文件相关")
@RestController
@RequestMapping(value = "/upload")
public class UploadFileController {

    @Resource
    private UploadFileService uploadFileService;

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/authApi/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Result<String>> uploadImage(@RequestPart("file") FilePart filePart, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Mono.just(Result.success(uploadFileService.uploadImage(filePart)));
    }

    @ApiOperation(value = "上传音乐")
    @PostMapping(value = "/authApi/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Result<String>> uploadAudio(@RequestPart("file") FilePart filePart, @RequestHeader(value = Constants.AUTHORIZATION) String token) {
        return Mono.just(Result.success(uploadFileService.uploadAudio(filePart)));
    }

}
