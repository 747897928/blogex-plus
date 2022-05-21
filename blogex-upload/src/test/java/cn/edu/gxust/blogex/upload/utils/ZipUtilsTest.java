package cn.edu.gxust.blogex.upload.utils;

import cn.edu.gxust.blogex.common.utils.ZipUtils;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author zhaoyijie
 * @since 2022/4/29 21:44
 */
public class ZipUtilsTest {

    @Test
    public void unZipFiles() {
    }

    @Test
    public void toZip() throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream("bin.zip");
        ZipUtils.toZip(System.getProperty("user.dir") + "/" + "bin", outputStream);
    }

    @Test
    public void test1() throws ZipException {
        //https://github.com/srikanth-lingala/zip4j
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(new File("bin.zip"), "aDS15".toCharArray());
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(CompressionLevel.NORMAL);
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(EncryptionMethod.AES);
        parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
        // 要打包的文件夹
        File currentFile = new File("bin/");
        File[] fs = currentFile.listFiles();
        // 遍历test文件夹下所有的文件、文件夹
        for (File f : fs) {
            if (f.isDirectory()) {
                zipFile.addFolder(f, parameters);
            } else {
                zipFile.addFile(f, parameters);
            }
        }
    }

    @Test
    public void test2(){
        try {
            ZipFile zipFile = new ZipFile(new File("bin.zip"));
            // 如果解压需要密码
            if(zipFile.isEncrypted()) {
                zipFile.setPassword("aDS15".toCharArray());
            }
            zipFile.extractAll(new File("newBin/").getAbsolutePath());
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}