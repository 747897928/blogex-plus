package cn.edu.gxust.blogex.api.convertor;

import cn.edu.gxust.blogex.api.vo.VerificationVO;
import cn.edu.gxust.blogex.common.entity.Verification;

/**
 * @author zhaoyijie
 * @since 2022/5/22 21:05
 */
public class VerificationConvertor {

    private VerificationConvertor() {
        throw new IllegalStateException("Construct VerificationConvertor");
    }

    public static VerificationVO convert(Verification verification) {
        if (verification == null) {
            return null;
        }
        VerificationVO verificationVO = new VerificationVO();
        verificationVO.setCodeUuid(verification.getCodeUuid());
        verificationVO.setSlidingImage(verification.getSlidingImage());
        verificationVO.setOriginalImage(verification.getOriginalImage());
        verificationVO.setxWidth(verification.getxWidth());
        verificationVO.setyHeight(verification.getyHeight());
        verificationVO.setExpireTime(verification.getExpireTime());
        return verificationVO;
    }

    public static Verification convert(VerificationVO verificationVO) {
        if (verificationVO == null) {
            return null;
        }
        Verification verification = new Verification();
        verification.setCodeUuid(verificationVO.getCodeUuid());
        verification.setSlidingImage(verificationVO.getSlidingImage());
        verification.setOriginalImage(verificationVO.getOriginalImage());
        verification.setxWidth(verificationVO.getxWidth());
        verification.setyHeight(verificationVO.getyHeight());
        verification.setExpireTime(verificationVO.getExpireTime());
        return verification;
    }

}
