package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.convertor.Live2dConvertor;
import cn.edu.gxust.blogex.api.dto.Live2dDTO;
import cn.edu.gxust.blogex.api.query.Live2dPageQuery;
import cn.edu.gxust.blogex.api.service.BlogDomainService;
import cn.edu.gxust.blogex.api.service.Live2dService;
import cn.edu.gxust.blogex.api.vo.Live2dVO;
import cn.edu.gxust.blogex.common.enums.ShowMarkEnum;
import cn.edu.gxust.blogex.common.exception.BlogException;
import cn.edu.gxust.blogex.common.exception.Live2dNotFoundException;
import cn.edu.gxust.blogex.common.exception.OperationNotAllowedException;
import cn.edu.gxust.blogex.common.exception.ResourcesNotFoundException;
import cn.edu.gxust.blogex.common.exception.UploadException;
import cn.edu.gxust.blogex.common.response.Pagination;
import cn.edu.gxust.blogex.common.utils.FileUtils;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import cn.edu.gxust.blogex.common.utils.RandomUtils;
import cn.edu.gxust.blogex.common.utils.StringUtils;
import cn.edu.gxust.blogex.common.utils.ZipUtils;
import cn.edu.gxust.blogex.dao.mappers.Live2dMapper;
import cn.edu.gxust.blogex.dao.po.Live2dPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2022/3/29 10:06
 */
@Service
public class Live2dServiceImpl extends ServiceImpl<Live2dMapper, Live2dPO> implements Live2dService {

    private static final Logger logger = LoggerFactory.getLogger(Live2dServiceImpl.class);

    @Resource
    private BlogDomainService blogDomainService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final String LIVE2D_MODEL_BASE_PATH = "uploads/uploadsFile/live2d/model/";

    private final String LIVE2D_MODEL_MESSAGE_KEY = "live2d:message:json";

    @Override
    public Live2dVO uploadLive2d(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println(originalFilename);
        if (originalFilename.endsWith(".zip")) {
            File destCompressFile = new File(LIVE2D_MODEL_BASE_PATH + originalFilename);
            try {
                File parentFile = destCompressFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                byte[] fileBytes = multipartFile.getBytes();
                FileUtils.writeBytesToLocal(fileBytes, destCompressFile);
                File uncompressFile = uncompressLive2d(destCompressFile);
                String path = uncompressFile.getPath();
                System.out.println(path);
                String uploadDomainPrefix = blogDomainService.getUploadDomainPrefix();
                File modelJsonFile = findFile(uncompressFile, "(.*?)model(.*?)json");
                String modelJsonUrl = uploadDomainPrefix + modelJsonFile.getPath();
                System.out.println(modelJsonUrl);
                File previewFile = findFile(uncompressFile, "(.*?)preview(.*?)|(.*?)截图(.*?)");
                String previewImageUrl = null;
                if (null != previewFile) {
                    previewImageUrl = uploadDomainPrefix + previewFile.getPath();
                }
                System.out.println(previewImageUrl);
                Live2dVO live2dVO = new Live2dVO();
                live2dVO.setModelName(uncompressFile.getName());
                live2dVO.setBaseFilePath(uncompressFile.getAbsolutePath());
                live2dVO.setModelJsonPath(modelJsonUrl);
                live2dVO.setModelImagePath(previewImageUrl);
                live2dVO.setShowMark(1);
                live2dVO.setWidth(250);
                live2dVO.setHeight(250);
                live2dVO.setX(0.0);
                live2dVO.setY(0.0);
                live2dVO.setScale(0.08);
                live2dVO.setAnchorx(0.0);
                live2dVO.setAnchory(0.0);
                return live2dVO;
            } catch (Exception e) {
                logger.error("上传live2d文件失败，错误原因:", e);
                throw new UploadException(e);
            }
        } else {
            throw new OperationNotAllowedException("只允许上传zip的压缩包");
        }
    }

    @Override
    public File uncompressLive2d(File destCompressFile) throws IOException {
        String compressFileName = destCompressFile.getName();
        if (compressFileName.endsWith(".zip")) {
            ZipUtils.unZipFiles(destCompressFile.getAbsolutePath(), LIVE2D_MODEL_BASE_PATH);
        }
        destCompressFile.delete();
        File destFile = new File(LIVE2D_MODEL_BASE_PATH + compressFileName.replace(".zip", ""));
        if (!destFile.exists()) {
            throw new BlogException("压缩包内的文件夹名与压缩包名字不一致");
        }
        return destFile;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertLive2d(Live2dDTO live2dDTO) {
        String basePath = new File(LIVE2D_MODEL_BASE_PATH).getAbsolutePath();
        String baseFilePath = live2dDTO.getBaseFilePath();
        //这里校验一下这个路径是否正确，避免删除错误路径的文件
        if (basePath.equals(baseFilePath) || !baseFilePath.contains(basePath)) {
            throw new BlogException("非法路径：" + baseFilePath);
        }
        System.out.println(live2dDTO);
        Live2dPO live2dPO = getOne(Wrappers.<Live2dPO>lambdaQuery().eq(Live2dPO::getModelName, live2dDTO.getModelName()));
        if (null != live2dPO) {
            throw new BlogException("存在相同的模型名：" + live2dDTO.getModelName());
        }
        Live2dPO entity = Live2dConvertor.convert(live2dDTO);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        return baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateLive2d(Live2dDTO live2dDTO) {
        if (null == live2dDTO.getId()) {
            throw new BlogException("id不能为空！");
        }
        Live2dPO live2dPO = Live2dConvertor.convert(live2dDTO);
        return baseMapper.updateById(live2dPO);
    }

    @Override
    public Live2dVO getLive2dById(Integer id) {
        return Live2dConvertor.convert(getById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteLive2d(Integer id) {
        Live2dPO live2dPO = checkExist(id);
        boolean b = removeById(id);
        if (b) {
            String baseFilePath = live2dPO.getBaseFilePath();
            if (null != baseFilePath) {
                String basePath = new File(LIVE2D_MODEL_BASE_PATH).getAbsolutePath();
                //这里校验一下这个路径是否正确，避免删除错误路径的文件
                if (baseFilePath.contains(basePath)) {
                    FileUtils.deleteFiles(new File(baseFilePath));
                }
            }
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Live2dPO checkExist(Integer live2dId) {
        Live2dPO live2dPO = baseMapper.selectById(live2dId);
        if (null == live2dPO) {
            throw new Live2dNotFoundException(live2dId);
        }
        return live2dPO;
    }

    @Override
    public Pagination<Live2dVO> listPage(Live2dPageQuery query) {
        Long pageNo = query.getPageNo();
        Long pageSize = query.getPageSize();
        Page<Live2dPO> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Live2dPO> wrapper = Wrappers.<Live2dPO>lambdaQuery();
        if (null != query.getShowMark()) {
            wrapper.eq(Live2dPO::getShowMark, query.getShowMark());
        }
        String searchKey = query.getSearchKey();
        if (null != query.getStartTime()) {
            wrapper.ge(Live2dPO::getCreateTime, query.getStartTime());
        }
        if (null != query.getEndTime()) {
            wrapper.le(Live2dPO::getCreateTime, query.getEndTime());
        }
        if (StringUtils.isNotBlank(searchKey)) {
            wrapper.like(Live2dPO::getModelName, searchKey);
        }
        wrapper.orderByDesc(Live2dPO::getCreateTime);
        Page<Live2dPO> selectPage = baseMapper.selectPage(page, wrapper);
        List<Live2dPO> records = selectPage.getRecords();
        List<Live2dVO> resultList = Live2dConvertor.convert(records);
        return new Pagination<>(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal(), resultList);
    }

    @Override
    public List<Live2dVO> getRandomTenList() {
        List<Live2dPO> live2dPOList = list(Wrappers.<Live2dPO>lambdaQuery().eq(Live2dPO::getShowMark, ShowMarkEnum.SHOW.getCode()).last("order by rand() desc limit 10"));
        return Live2dConvertor.convert(live2dPOList);
    }

    @Override
    public void downloadLive2d(Integer id, HttpServletResponse response) {
        Live2dPO live2dPO = checkExist(id);
        String baseFilePath = live2dPO.getBaseFilePath();
        if (null == baseFilePath) {
            throw new BlogException("File path is empty, cannot download!");
        }
        File file = new File(baseFilePath);
        if (!file.exists()) {
            throw new ResourcesNotFoundException("The model file could not be found in the file system");
        }
        try {
            String fileName = file.getName() + ".zip";
            String encode = URLEncoder.encode(fileName, "UTF-8");
            /*解决axios无法获取响应头headers的Content-Disposition*/
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + encode);
            response.addHeader("file-name", encode);
            response.setContentType("application/octet-stream;charset=UTF-8");
            ZipUtils.toZip(file.getAbsolutePath(), response.getOutputStream());
        } catch (Exception e) {
            logger.error("", e);
            throw new BlogException(e);
        }
    }

    @Override
    public String getModelMessage() {
        return stringRedisTemplate.opsForValue().get(LIVE2D_MODEL_MESSAGE_KEY);
    }

    @Override
    public void editModelMessage(Object o) {
        String json = JSONUtils.toJsonString(o);
        stringRedisTemplate.opsForValue().set(LIVE2D_MODEL_MESSAGE_KEY, json);
    }

    @Override
    public String backupLive2d() {
        File srcFile = new File(LIVE2D_MODEL_BASE_PATH);
        if (!srcFile.exists()) {
            throw new ResourcesNotFoundException("文件夹不存在：" + srcFile.getAbsolutePath());
        }
        File[] fileList = srcFile.listFiles();
        if (fileList == null || fileList.length == 0) {
            throw new BlogException(srcFile.getAbsolutePath() + "下没有模型文件");
        } else {
            //检查一下这个路径是否有模型文件夹，加上这个校验是为了防止个别电脑如mac上的.DS_Store文件跳过了上面的校验
            boolean haveDirectoryFlag = false;
            for (File file : fileList) {
                if (file.isDirectory()) {
                    haveDirectoryFlag = true;
                }
            }
            if (!haveDirectoryFlag) {
                throw new BlogException(srcFile.getAbsolutePath() + "下没有模型文件");
            }
        }
        String randomString = RandomUtils.randomString(4);
        long currentTimeMillis = System.currentTimeMillis();
        File outputFile = new File("uploads/uploadsFile/" + currentTimeMillis + "_" + randomString + "_backUp/live2dAll.zip");
        outputFile.deleteOnExit();
        try {
            ZipUtils.toZipWithPassWord(srcFile, outputFile, null);
        } catch (ZipException e) {
            throw new RuntimeException(e);
        }
        String uploadDomainPrefix = blogDomainService.getUploadDomainPrefix();
        return uploadDomainPrefix + outputFile.getPath();
    }

    @Override
    public List<Live2dVO> listLive2d() {
        return Live2dConvertor.convert(list());
    }

    public File findFile(File parentFile, String fileNameRegex) {
        if (parentFile.isDirectory()) {
            File[] listFiles = parentFile.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    File resultFile = findFile(file, fileNameRegex);
                    if (resultFile != null) {
                        return resultFile;
                    }
                }
            } else {
                return null;
            }
        } else {
            if (parentFile.getName().matches(fileNameRegex)) {
                return parentFile;
            } else {
                return null;
            }
        }
        return null;
    }
}
