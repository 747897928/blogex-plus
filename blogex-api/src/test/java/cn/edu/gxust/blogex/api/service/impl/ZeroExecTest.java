package cn.edu.gxust.blogex.api.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhaoyijie
 * @since 2022/5/14 11:29
 */
@SpringBootTest
public class ZeroExecTest {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUserName;

    @Value("${spring.datasource.password}")
    private String datasourcePassWord;

    @Test
    public void test1() {
        File file = new File("backUp/blogex.sql");
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Pattern pattern = Pattern.compile("jdbc:mysql://(.*?)\\?", Pattern.DOTALL);// Pattern.CASE_INSENSITIVE 略大小写的写法
        System.out.println(datasourceUrl);
        System.out.println(datasourceUserName);
        System.out.println(datasourcePassWord);
        Matcher matcher = pattern.matcher(datasourceUrl);
        String ipHostDatabaseNameStr = "";
        while (matcher.find()) {
            ipHostDatabaseNameStr = matcher.group();
        }
        System.out.println(ipHostDatabaseNameStr);
        ipHostDatabaseNameStr = ipHostDatabaseNameStr.replace("jdbc:mysql://", "").replace("?", "");
        System.out.println(ipHostDatabaseNameStr);
        String[] split = ipHostDatabaseNameStr.split(":");
        String host = split[0];
        String[] splitArr = split[1].split("/");
        String port = splitArr[0];
        String database = splitArr[1];
        /*java中必须使用"-r"替代">"*/
        String command = "mysqldump -h" + host + " -P" + port + " -u" + datasourceUserName + " -p" + datasourcePassWord +
                " -e --max_allowed_packet=1048576 --net_buffer_length=16384 " + database + " -r " + file.getAbsolutePath();
        System.out.println(command);
        try {
            Process process = Runtime.getRuntime().exec(command);
            new Thread(() -> {
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try {
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(() -> {
                BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line = null;
                try {
                    while ((line = err.readLine()) != null) {
                        System.err.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        err.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            int waitFor = process.waitFor();
            if (waitFor == 0) {
                System.out.println("Mysql 数据库备份成功");
            } else {

            }
            System.out.println(waitFor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        File file = new File("backUp/blogex.sql");
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Pattern pattern = Pattern.compile("jdbc:(.*?)://(.*?):(.*?)/(.*?)\\?");// Pattern.CASE_INSENSITIVE 略大小写的写法
        System.out.println(datasourceUrl);
        System.out.println(datasourceUserName);
        System.out.println(datasourcePassWord);
        Matcher matcher = pattern.matcher(datasourceUrl);
        if (matcher.find()) {
            String dataSourceType = matcher.group(1);
            System.out.println("dataSourceType = " + dataSourceType);
            String host = matcher.group(2);
            System.out.println("host = " + host);
            String port = matcher.group(3);
            System.out.println("port = " + port);
            String databaseName = matcher.group(4);
            System.out.println("databaseName = " + databaseName);

        }
    }
}
