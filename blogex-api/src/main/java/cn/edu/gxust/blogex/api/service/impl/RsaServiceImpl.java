package cn.edu.gxust.blogex.api.service.impl;

import cn.edu.gxust.blogex.api.service.RsaService;
import cn.edu.gxust.blogex.common.exception.BlogException;
import cn.edu.gxust.blogex.common.utils.FileUtils;
import cn.edu.gxust.blogex.common.utils.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author zhaoyijie
 * @since 2022/6/1 20:16
 */
@Service
public class RsaServiceImpl implements RsaService {

    private final static Logger logger = LoggerFactory.getLogger(RsaServiceImpl.class);

    @Override
    public void generateRasKeyFile() {
        RSAUtils.RsaEntity rsaEntity = RSAUtils.generateRasKey();
        String privateKey = rsaEntity.getPrivateKey();
        String publicKey = rsaEntity.getPublicKey();
        File privateKeyFile = new File(RSA_PRIVATE_KEY_FILE_PATH);
        File publicKeyFile = new File(RSA_PUBLIC_KEY_FILE_PATH);
        FileUtils.writeContentToLocal(privateKey, privateKeyFile);
        FileUtils.writeContentToLocal(publicKey, publicKeyFile);
    }

    @Override
    public String getRsaPrivateKey() {
        File privateKeyFile = new File(RSA_PRIVATE_KEY_FILE_PATH);
        if (!privateKeyFile.exists()) {
            generateRasKeyFile();
        }
        try {
            byte[] bytes = Files.readAllBytes(privateKeyFile.toPath());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("", e);
            throw new BlogException(e);
        }
    }

    @Override
    public String getRsaPublicKey() {
        File publicKeyFile = new File(RSA_PUBLIC_KEY_FILE_PATH);
        if (!publicKeyFile.exists()) {
            generateRasKeyFile();
        }
        try {
            byte[] bytes = Files.readAllBytes(publicKeyFile.toPath());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("", e);
            throw new BlogException(e);
        }
    }

    @Override
    public String encrypt(String str) {
        String rsaPublicKey = getRsaPublicKey();
        return RSAUtils.encrypt(str, rsaPublicKey);
    }

    @Override
    public String decrypt(String str) {
        String rsaPrivateKey = getRsaPrivateKey();
        return RSAUtils.decrypt(str, rsaPrivateKey);
    }

}
