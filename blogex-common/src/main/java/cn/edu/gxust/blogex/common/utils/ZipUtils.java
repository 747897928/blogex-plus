package cn.edu.gxust.blogex.common.utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip解压工具
 *
 * @author zhaoyijie
 * @since 2022/3/29 09:25
 */
public class ZipUtils {

    private ZipUtils() {
        throw new UnsupportedOperationException("Construct ZipUtils");
    }

    public static void unZipFiles(String zipFile, String destFolder) throws IOException {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            System.out.println("Extracting: " + entry.getName());
            int count;
            byte[] data = new byte[1024];
            if (entry.isDirectory()) {
                new File(destFolder + File.separator + entry.getName()).mkdirs();
                continue;
            } else {
                int di = entry.getName().lastIndexOf(File.separator);
                if (di != -1) {
                    new File(destFolder + File.separator + entry.getName().substring(0, di)).mkdirs();
                }
            }
            FileOutputStream fos = new FileOutputStream(destFolder + File.separator + entry.getName());
            BufferedOutputStream dest = new BufferedOutputStream(fos);
            while ((count = zis.read(data)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
        }
    }

    /**
     * 压缩成ZIP
     *
     * @param srcDir 压缩文件夹路径
     * @param out    压缩文件输出流
     */
    public static void toZip(String srcDir, OutputStream out) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName());
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩成ZIP
     *
     * @param srcDirFile 要打包的文件，可以是文件夹，也可以是文件
     * @param outPutFile 压缩文件输出路径
     */
    public static void toZipWithPassWord(File srcDirFile, File outPutFile, char[] password) throws ZipException {
        File parentFile = outPutFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        ZipFile zipFile = new ZipFile(outPutFile, password);
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(CompressionLevel.NORMAL);
        if (null != password) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(EncryptionMethod.AES);
            parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
        }
        if (srcDirFile.isFile()) {
            zipFile.addFile(srcDirFile, parameters);
        } else {
            File[] fs = srcDirFile.listFiles();
            // 遍历test文件夹下所有的文件、文件夹
            for (File f : fs) {
                if (f.isDirectory()) {
                    zipFile.addFolder(f, parameters);
                } else {
                    zipFile.addFile(f, parameters);
                }
            }
        }
    }


    /**
     * 解压携带密码的zip
     *
     * @param srcDirFile 压缩文件
     * @param outPutFile 输出文件
     * @param password   密码
     */
    public static void unZipWithPassWord(File srcDirFile, File outPutFile, char[] password) throws ZipException {
        ZipFile zipFile = new ZipFile(srcDirFile);
        // 如果解压需要密码
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
        zipFile.extractAll(outPutFile.getAbsolutePath());
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name) throws Exception {
        byte[] buf = new byte[2048];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                zos.putNextEntry(new ZipEntry(name + "/"));
                // 没有文件，不需要文件的copy
                zos.closeEntry();
            } else {
                for (File file : listFiles) {
                    compress(file, zos, name + "/" + file.getName());
                }
            }
        }
    }

}
