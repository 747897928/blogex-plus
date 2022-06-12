package cn.edu.gxust.blogex.api.service;

/**
 * @author zhaoyijie
 * @since 2022/6/1 20:11
 */
public interface RsaService {

    String RSA_PRIVATE_KEY_FILE_PATH = "rsa_keys/rsa_1024_private.key";

    String RSA_PUBLIC_KEY_FILE_PATH = "rsa_keys/rsa_1024_public.key";

    void generateRasKeyFile();

    String getRsaPrivateKey();

    String getRsaPublicKey();

    String encrypt(String str);

    String decrypt(String str);
}
