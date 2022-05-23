package cn.edu.gxust.blogex.api.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhaoyijie
 * @since 2022/2/26 21:45
 */
public interface UploadFileService {

    String uploadImage(MultipartFile multipartFile);

    String uploadAudio(MultipartFile multipartFile);

}
