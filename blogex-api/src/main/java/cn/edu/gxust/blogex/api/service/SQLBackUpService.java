package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.vo.VerifyCodeVO;

/**
 * @author zhaoyijie
 * @since 2022/5/15 12:38
 */
public interface SQLBackUpService {

    /**
     * 备份sql，sql文件将会压缩发送到邮件，解压密码将会在这个vo图片的base64内
     */
    VerifyCodeVO backUpSql();

}
