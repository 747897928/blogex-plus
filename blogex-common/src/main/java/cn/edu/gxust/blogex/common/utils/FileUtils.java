package cn.edu.gxust.blogex.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author zhaoyijie
 * @since 2022/3/29 11:30
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
        throw new UnsupportedOperationException("Construct FileUtils");
    }

    public static boolean writeContentToLocal(String content, File destFile) {
        try {
            File parentFile = destFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(destFile), StandardCharsets.UTF_8);
            outputStreamWriter.write(content);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            return true;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    public static void writeBytesToLocal(InputStream inputStream, File destFile) throws IOException {
        File parentFile = destFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream outputStream = new FileOutputStream(destFile);
        byte[] bytes = new byte[1024];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        outputStream.flush();
        outputStream.close();
    }

    public static void writeBytesToLocal(byte[] bytes, File destFile) throws IOException {
        File parentFile = destFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream outputStream = new FileOutputStream(destFile);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    public static void deleteFiles(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File f : files) {
                    deleteFiles(f);
                }
            }
        }
        file.delete();
    }

}
