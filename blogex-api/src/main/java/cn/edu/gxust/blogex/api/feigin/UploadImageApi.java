package cn.edu.gxust.blogex.api.feigin;

import cn.edu.gxust.blogex.common.response.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhaoyijie
 * @since 2022/2/26 23:24
 */
@Component
@FeignClient(value = "blogex-upload")
@RequestMapping(value = "/upload")
public interface UploadImageApi {

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/image")
    Result<String> uploadImage(@RequestParam(value = "file") MultipartFile file);

}
