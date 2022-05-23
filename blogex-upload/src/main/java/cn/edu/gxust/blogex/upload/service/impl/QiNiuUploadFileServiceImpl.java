package cn.edu.gxust.blogex.upload.service.impl;

import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.exception.OperationNotAllowedException;
import cn.edu.gxust.blogex.common.exception.UploadException;
import cn.edu.gxust.blogex.common.utils.FileUtils;
import cn.edu.gxust.blogex.upload.entity.QiNiuProperties;
import cn.edu.gxust.blogex.upload.service.UploadFileService;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.multipart.FilePart;

import java.io.File;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * @author zhaoyijie
 * @since 2022/5/22 10:20
 */
public class QiNiuUploadFileServiceImpl implements UploadFileService {

    private static final Logger logger = LoggerFactory.getLogger(QiNiuUploadFileServiceImpl.class);

    private final String accessKey;

    private final String secretKey;

    private final String bucket;

    private final String domainOfBucket;

    /**
     * 指定扩展名为下例的图片文件可以上传
     */
    private final String[] IMAGE_FILE_EXTENSION = new String[]{".jpg", ".jpeg", ".gif", ".png", ".ico", ".webp"};

    /**
     * 指定扩展名为下例的音频文件可以上传
     */
    private final String[] AUDIO_FILE_EXTENSION = new String[]{".mp3", ".m4a", ".cda", ".wav", ".aif", ".aiff", "mid", "wma", "ra", "vqf", "ape"};

    public QiNiuUploadFileServiceImpl(QiNiuProperties qiNiuProperties) {
        this.accessKey = qiNiuProperties.getAccessKey();
        this.secretKey = qiNiuProperties.getSecretKey();
        this.bucket = qiNiuProperties.getBucket();
        this.domainOfBucket = qiNiuProperties.getDomainOfBucket();

    }

    @Override
    public String uploadImage(FilePart filePart) {
        //https://www.cnblogs.com/LUA123/p/10518272.html
        String originalFilename = filePart.filename();
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
            String destFolder = "uploadsFile/images/";
            File destFile = new File(destFolder + originalFilename);
            try {
                File parentFile = destFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                filePart.transferTo(destFile);
                String filePath = destFile.getAbsolutePath();
                Configuration cfg = new Configuration(Region.autoRegion());
                UploadManager uploadManager = new UploadManager(cfg);
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                String encodedFileName = URLEncoder.encode(originalFilename, "utf-8").replace("+", "%20");
                Response response = uploadManager.put(filePath, destFolder + encodedFileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                String finalUrl = domainOfBucket + Constants.DIVISION_STRING + putRet.key;
                System.out.println(finalUrl);
                FileUtils.deleteFiles(destFile);
                return finalUrl;
            } catch (Exception e) {
                logger.error("上传图片失败，错误原因:", e);
                throw new UploadException(e);
            }
        } else {
            throw new OperationNotAllowedException("只允许上传" + Arrays.toString(IMAGE_FILE_EXTENSION) + "类型的文件");
        }
    }

    @Override
    public String uploadAudio(FilePart filePart) {
        String originalFilename = filePart.filename();
        System.out.println(originalFilename);
        //上传的文件是否符合要求
        boolean isSatisfactory = false;
        for (String fileTypeItem : AUDIO_FILE_EXTENSION) {
            //大写转小写
            if (originalFilename.toLowerCase().endsWith(fileTypeItem)) {
                isSatisfactory = true;
                break;
            }
        }
        if (isSatisfactory) {
            String destFolder = "uploadsFile/audio/";
            File destFile = new File(destFolder + originalFilename);
            try {
                File parentFile = destFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                filePart.transferTo(destFile);
                String filePath = destFile.getAbsolutePath();
                Configuration cfg = new Configuration(Region.autoRegion());
                UploadManager uploadManager = new UploadManager(cfg);
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                String encodedFileName = URLEncoder.encode(originalFilename, "utf-8").replace("+", "%20");
                Response response = uploadManager.put(filePath, destFolder + encodedFileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                String finalUrl = domainOfBucket + Constants.DIVISION_STRING + putRet.key;
                System.out.println(finalUrl);
                FileUtils.deleteFiles(destFile);
                return finalUrl;
            } catch (Exception e) {
                logger.error("上传音频失败，错误原因:", e);
                throw new UploadException(e);
            }
        } else {
            throw new OperationNotAllowedException("只允许上传" + Arrays.toString(AUDIO_FILE_EXTENSION) + "类型的文件");
        }
    }

}
