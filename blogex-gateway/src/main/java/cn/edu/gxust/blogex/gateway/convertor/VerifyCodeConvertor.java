package cn.edu.gxust.blogex.gateway.convertor;

import cn.edu.gxust.blogex.common.entity.VerifyCode;
import cn.edu.gxust.blogex.gateway.vo.VerifyCodeVO;

/**
 * @author zhaoyijie
 * @since 2022/5/22 21:13
 */
public class VerifyCodeConvertor {

    private VerifyCodeConvertor() {
        throw new IllegalStateException("Construct VerifyCodeConvertor");
    }

    public static VerifyCodeVO convert(VerifyCode verifyCode) {
        if (verifyCode == null) {
            return null;
        }
        VerifyCodeVO verifyCodeVO = new VerifyCodeVO();
        verifyCodeVO.setUuid(verifyCode.getUuid());
        verifyCodeVO.setCode(verifyCode.getCode());
        verifyCodeVO.setBase64ImgStr(verifyCode.getBase64ImgStr());
        verifyCodeVO.setExpireTime(verifyCode.getExpireTime());
        return verifyCodeVO;
    }

    public static VerifyCode convert(VerifyCodeVO verificationVO) {
        if (verificationVO == null) {
            return null;
        }
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setUuid(verificationVO.getUuid());
        verifyCode.setCode(verificationVO.getCode());
        verifyCode.setBase64ImgStr(verificationVO.getBase64ImgStr());
        verifyCode.setExpireTime(verificationVO.getExpireTime());
        return verifyCode;
    }


}
