package cn.edu.gxust.blogex.upload.service.impl;

import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author zhaoyijie
 * @since 2022/3/29 10:35
 */
public class Live2dServiceImplTest {


    @Test
    public void test1() {
        File file = findFile(new File("/Users/zhaoyijie/MyProject/vsProject/desktopLive2d/src/live2d/model/95type_405")
                , "95type_405.model.json");
        System.out.println(file);
    }

    public File findFile(File parentFile, String fileName) {
        if (parentFile.isDirectory()) {
            File[] listFiles = parentFile.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    File resultFile = findFile(file, fileName);
                    if (resultFile != null) {
                        return resultFile;
                    }
                }
            } else {
                return null;
            }
        } else {
            if (parentFile.getName().equals(fileName)) {
                return parentFile;
            } else {
                return null;
            }
        }
        return null;
    }
}