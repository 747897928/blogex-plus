package cn.edu.gxust.blogex.upload.service;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/2/26 21:45
 */
public interface UploadFileService {

    String uploadImage(FilePart filePart);

    String uploadAudio(FilePart filePart);

}
