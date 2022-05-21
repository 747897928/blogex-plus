package cn.edu.gxust.blogex.api.service;

import cn.edu.gxust.blogex.api.dto.VerifyCodeDTO;
import cn.edu.gxust.blogex.api.vo.VerifyCodeVO;

/**
 * @author zhaoyijie
 * @since 2022/3/24 09:52
 */
public interface VerifyCodeService {

    VerifyCodeVO getVerifyCode();

    VerifyCodeVO getVerifyCode(String randomStr);

    void verifyCode(VerifyCodeDTO verifyCodeDTO);

}
