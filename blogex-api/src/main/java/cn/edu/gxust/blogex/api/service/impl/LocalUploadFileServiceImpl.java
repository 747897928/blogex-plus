package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.service.BlogDomainService;
import cn.edu.gxust.blogex.api.service.UploadFileService;
import cn.edu.gxust.blogex.common.exception.OperationNotAllowedException;
import cn.edu.gxust.blogex.common.exception.UploadException;
import cn.edu.gxust.blogex.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author zhaoyijie
 * @since 2022/2/26 21:54
 */
public class LocalUploadFileServiceImpl implements UploadFileService {

    private static final Logger logger = LoggerFactory.getLogger(LocalUploadFileServiceImpl.class);

    /**
     * 指定扩展名为下例的图片文件可以上传
     */
    private final String[] IMAGE_FILE_EXTENSION = new String[]{".jpg", ".jpeg", ".gif", ".png", ".ico", ".webp"};

    /**
     * 指定扩展名为下例的音频文件可以上传
     */
    private final String[] AUDIO_FILE_EXTENSION = new String[]{".mp3", ".m4a", ".cda", ".wav", ".aif", ".aiff", "mid", "wma", "ra", "vqf", "ape"};

    private final BlogDomainService blogDomainService;

    public LocalUploadFileServiceImpl(BlogDomainService blogDomainService) {
        this.blogDomainService = blogDomainService;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) {
        //https://www.cnblogs.com/LUA123/p/10518272.html
        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println(originalFilename);
        //上传的文件是否符合要求
        boolean isSatisfactory = false;
        String suffix = "";
        for (String fileTypeItem : IMAGE_FILE_EXTENSION) {
            //大写转小写
            if (originalFilename.toLowerCase().endsWith(fileTypeItem)) {
                isSatisfactory = true;
                suffix = fileTypeItem;
                break;
            }
        }
        if (isSatisfactory) {
            String destFolder = "uploads/uploadsFile/images/";
            File destFile = new File(destFolder + UUID.randomUUID().toString() + suffix);
            try {
                File parentFile = destFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                byte[] fileBytes = multipartFile.getBytes();
                FileUtils.writeBytesToLocal(fileBytes, destFile);
                String filePath = destFile.getPath();
                return blogDomainService.getUploadDomainPrefix() + filePath;
            } catch (Exception e) {
                logger.error("上传图片失败，错误原因:", e);
                throw new UploadException(e);
            }
        } else {
            throw new OperationNotAllowedException("只允许上传" + Arrays.toString(IMAGE_FILE_EXTENSION) + "类型的文件");
        }
    }

    @Override
    public String uploadAudio(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println(originalFilename);
        //上传的文件是否符合要求
        boolean isSatisfactory = false;
        String suffix = "";
        for (String fileTypeItem : AUDIO_FILE_EXTENSION) {
            //大写转小写
            if (originalFilename.toLowerCase().endsWith(fileTypeItem)) {
                isSatisfactory = true;
                suffix = fileTypeItem;
                break;
            }
        }
        if (isSatisfactory) {
            String destFolder = "uploads/uploadsFile/audio/";
            File destFile = new File(destFolder + UUID.randomUUID().toString() + suffix);
            File parentFile = destFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                byte[] fileBytes = multipartFile.getBytes();
                FileUtils.writeBytesToLocal(fileBytes, destFile);
                String filePath = destFile.getPath();
                return blogDomainService.getUploadDomainPrefix() + filePath;
            } catch (Exception e) {
                logger.error("上传音频失败，错误原因:", e);
                throw new UploadException(e);
            }
        } else {
            throw new OperationNotAllowedException("只允许上传" + Arrays.toString(AUDIO_FILE_EXTENSION) + "类型的文件");
        }
    }
}
