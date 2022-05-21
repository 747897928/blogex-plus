package cn.edu.gxust.blogex.api.service;

/**
 *
 * @author :zhaoyijie
 * @since 2021/1/30 12:31
 */
public interface BlogMailService {

    /**
     * 发送邮件
     *
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param to      收件人邮箱地址
     * @return 是否发生成功
     */
    boolean sendMail(String subject, String text, String to);

    /**
     * 验证邮箱的有效性
     *
     * @param email 邮箱号
     * @return 是否是邮箱
     */
    default boolean isEmail(String email) {
        return email.matches("^\\w+@[a-zA-Z0-9]{2,10}(?:\\.[a-z]{2,4}){1,3}$");
    }
}
