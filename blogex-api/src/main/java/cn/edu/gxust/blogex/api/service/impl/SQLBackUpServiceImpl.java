package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.service.BlogSettingService;
import cn.edu.gxust.blogex.api.service.SQLBackUpService;
import cn.edu.gxust.blogex.api.service.VerifyCodeService;
import cn.edu.gxust.blogex.api.utils.MailUtils;
import cn.edu.gxust.blogex.api.vo.BlogSettingVO;
import cn.edu.gxust.blogex.api.vo.VerifyCodeVO;
import cn.edu.gxust.blogex.common.enums.MailEnableEnum;
import cn.edu.gxust.blogex.common.exception.BlogException;
import cn.edu.gxust.blogex.common.exception.OperationNotAllowedException;
import cn.edu.gxust.blogex.common.utils.FileUtils;
import cn.edu.gxust.blogex.common.utils.RandomUtils;
import cn.edu.gxust.blogex.common.utils.ZipUtils;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhaoyijie
 * @since 2022/5/15 13:31
 */
@Service
public class SQLBackUpServiceImpl implements SQLBackUpService {

    private final static Logger logger = LoggerFactory.getLogger(SQLBackUpServiceImpl.class);

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUserName;

    @Value("${spring.datasource.password}")
    private String datasourcePassWord;

    @Value("${blogex.mail.username}")
    private String mailUsername;

    @Value("${blogex.mail.password}")
    private String mailPassword;

    @Value("${blogex.mail.host}")
    private String mailHost;

    @Value("${blogex.mail.port}")
    private Integer mailPort;

    @Resource
    private BlogSettingService blogSettingService;

    @Resource
    private VerifyCodeService verifyCodeService;

    public VerifyCodeVO backUpSql() {
        BlogSettingVO blogSetting = blogSettingService.getBlogSetting();
        Integer mailEnable = blogSetting.getMailEnable();
        MailEnableEnum mailEnableEnum = MailEnableEnum.of(mailEnable);
        switch (mailEnableEnum) {
            case ON:
                break;
            case OFF:
                throw new OperationNotAllowedException("邮件任务未开启，无法备份发送sql文件");
            default:
                throw new IllegalArgumentException("mailEnable枚举值不正确");
        }
        String blogMailAddress = blogSetting.getBlogMailAddress();
        String randomString = RandomUtils.randomString(4);
        File file = new File("backUp/blogex.sql");
        file.deleteOnExit();
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Pattern pattern = Pattern.compile("jdbc:(.*?)://(.*?):(.*?)/(.*?)\\?");// Pattern.CASE_INSENSITIVE 略大小写的写法
        Matcher matcher = pattern.matcher(datasourceUrl);
        if (matcher.find()) {
            String dataSourceType = matcher.group(1);
            String host = matcher.group(2);
            String port = matcher.group(3);
            String databaseName = matcher.group(4);
            File outPutFile = new File("backUp/blogex.zip");
            outPutFile.deleteOnExit();
            if ("mysql".equalsIgnoreCase(dataSourceType)) {
                handleForMysql(datasourceUserName, datasourcePassWord, host, port, databaseName, file);
                try {
                    ZipUtils.toZipWithPassWord(file, outPutFile, randomString.toCharArray());
                } catch (ZipException e) {
                    throw new RuntimeException(e);
                }
                byte[] bytes;
                try {
                    bytes = Files.readAllBytes(outPutFile.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                VerifyCodeVO verifyCode = verifyCodeService.getVerifyCode(randomString);
                List<MailUtils.MailAnnexFile> mailAnnexFileList = new ArrayList<>();
                mailAnnexFileList.add(new MailUtils.MailAnnexFile("blogex.zip", bytes));
                MailUtils.sendAnnexFile(mailHost, mailPort, mailUsername, mailPassword,
                        new String[]{blogMailAddress}, null, "博客备份sql",
                        "解压密码请看页面的图片验证码", "utf-8", mailAnnexFileList);
                return verifyCode;
            } else if ("oracle".equalsIgnoreCase(dataSourceType)) {
                handleForOracle(datasourceUserName, datasourcePassWord, host, port, databaseName, file);
            } else if ("sqlserver".equalsIgnoreCase(dataSourceType)) {
                handleForSqlserver(datasourceUserName, datasourcePassWord, host, port, databaseName, file);
            } else {
                throw new UnsupportedOperationException("目前不支持" + dataSourceType + "的备份");
            }
        } else {
            throw new BlogException(500, "获取不到数据库的连接信息！");
        }
        return null;
    }

    public void handleForMysql(String datasourceUserName, String datasourcePassWord, String host, String port, String databaseName, File outPutFile) {
        /*java中必须使用"-r"替代">"*/
        String command = "mysqldump -h" + host + " -P" + port + " -u" + datasourceUserName + " -p" + datasourcePassWord +
                " -e --max_allowed_packet=1048576 --net_buffer_length=16384 " + databaseName + " -r " + outPutFile.getAbsolutePath();
        try {
            Process process = Runtime.getRuntime().exec(command);
            new Thread(() -> {
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try {
                    while ((line = in.readLine()) != null) {
                        logger.info(line);
                    }
                } catch (IOException e) {
                    logger.error("", e);
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
            }).start();

            new Thread(() -> {
                BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line = null;
                try {
                    while ((line = err.readLine()) != null) {
                        logger.info(line);
                    }
                } catch (IOException e) {
                    logger.error("", e);
                } finally {
                    try {
                        err.close();
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
            }).start();
            int waitFor = process.waitFor();
            if (waitFor == 0) {
                logger.info("Mysql 数据库备份成功");
            } else {
                logger.error("Mysql 数据库备份失败，waitFor = " + waitFor);
            }
            System.out.println(waitFor);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void handleForOracle(String datasourceUserName, String datasourcePassWord, String host, String port, String databaseName, File outPutFile) {
        throw new UnsupportedOperationException("目前不支持oracle的备份");
    }

    public void handleForSqlserver(String datasourceUserName, String datasourcePassWord, String host, String port, String databaseName, File outPutFile) {
        throw new UnsupportedOperationException("目前不支持sqlserver的备份");
    }

}
