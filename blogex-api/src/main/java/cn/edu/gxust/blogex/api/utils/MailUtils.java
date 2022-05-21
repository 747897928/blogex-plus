package cn.edu.gxust.blogex.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author zhaoyijie
 * @since 2022/5/13 22:18
 */
public class MailUtils {

    private final static Logger logger = LoggerFactory.getLogger(MailUtils.class);

    private final static JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    static {
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.ssl.enable","true");
        javaMailProperties.setProperty("mail.smtp.connectiontimeout","10000");
        javaMailProperties.setProperty("mail.smtp.timeout","10000");
        javaMailProperties.setProperty("mail.smtp.writetimeout","100000");
        javaMailSender.setJavaMailProperties(javaMailProperties);
    }

    public static void sendMail(String host, int port, String userName, String passWord, String[] toArr, String[] ccArr, String subject, String content) {
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(passWord);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setFrom(userName);
        mailMessage.setTo(toArr);
        if (ccArr != null && ccArr.length > 0) {
            mailMessage.setCc(ccArr);
        }
        mailMessage.setText(content);
        try {
            javaMailSender.send(mailMessage);
            logger.info("sendMail " + Arrays.toString(toArr) + "--" + subject + "---" + content);
        } catch (MailException e) {
            logger.error("send message failed, error message = " + e.getClass() + "----" + e.getMessage());
        }
    }

    /**
     * 发送附件(多个附件)
     *
     * @param toArr             发送者数组
     * @param ccArr             抄送者数组
     * @param title             邮件主题(标题)
     * @param text              邮件内容
     * @param mailAnnexFileList 附件列表
     * @author zhaoyijie
     */
    public static void sendAnnexFile(String host, int port, String userName, String passWord, String[] toArr, String[] ccArr, String title, String text, String encoding, List<MailAnnexFile> mailAnnexFileList) {
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(passWord);
        //防止中文名字 base64加密以后 名字太长被截断 导致中文乱码问题
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, encoding);
            helper.setFrom(userName);
            helper.setTo(toArr);
            if (StringUtils.isNotBlank(title)) {
                helper.setSubject(title);
            } else {
                helper.setSubject("默认主题");
            }
            if (StringUtils.isNotBlank(text)) {
                helper.setText(text, true);
            } else {
                helper.setText("附件", true);
            }
            if (ccArr != null && ccArr.length > 0) {
                helper.setCc(ccArr);
            }
            for (MailAnnexFile mailAnnexFile : mailAnnexFileList) {
                String fileName = mailAnnexFile.getFileName();
                byte[] fileBytes = mailAnnexFile.getFileBytes();
                InputStreamSource inputStreamSource = new ByteArrayResource(fileBytes);
                helper.addAttachment(MimeUtility.decodeText(fileName), inputStreamSource);
            }
            javaMailSender.send(message);
            logger.info("send mail succeed！" + host + "---" + port + "---" + userName + "---" + text + "---" + Arrays.toString(toArr) + "---" + Arrays.toString(ccArr));
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error("邮件发送附件异常：" + e.getClass() + "----" + e.getMessage());
        }
    }

    public static JavaMailSenderImpl getJavaMailSender() {
        return javaMailSender;
    }

    public static void setJavaMailProperties(Properties javaMailProperties){
        javaMailSender.setJavaMailProperties(javaMailProperties);
    }

    public static class MailAnnexFile {

        private final String fileName;

        private final byte[] fileBytes;

        public MailAnnexFile(String fileName, byte[] fileBytes) {
            this.fileName = fileName;
            this.fileBytes = fileBytes;
        }

        public String getFileName() {
            return fileName;
        }

        public byte[] getFileBytes() {
            return fileBytes;
        }

        @Override
        public String toString() {
            return "MailAnnexFile{" +
                    "fileName='" + fileName + '\'' +
                    ", fileBytes=" + Arrays.toString(fileBytes) +
                    '}';
        }
    }
}
