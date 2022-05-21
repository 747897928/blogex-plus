package cn.edu.gxust.blogex.api.service.impl;


import eu.bitwalker.useragentutils.UserAgent;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaoyijie
 * @since 2022/4/22 21:08
 */

public class CommentServiceImplTest {

    @Test
    public void test1(){
        //Mac OS X
        //Chrome 10
        //Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36
        //String userAgentStr = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36";
        //Mac OS X
        //Opera 11
        //Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11

        //Android 2.x
        //Mobile Safari
        //Mozilla/5.0 (Linux; U; Android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1

        //Windows 10
        //Chrome 8
        //Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36

        //iOS 4 (iPhone)
        //Mobile Safari
        //Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5
        String userAgentStr = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5";
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        String osName = userAgent.getOperatingSystem().getName();
        String browserName = userAgent.getBrowser().getName();
        System.out.println(osName);
        System.out.println(browserName);
    }



}