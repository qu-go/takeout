package com.xinzhou.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class FTPConfig {

    public static String host;
    // 端口

    public static  int port;
    // ftp用户名

    public static  String userName;
    // ftp用户密码

    public static  String passWord;
    // 文件在服务器端保存的主目录

    public static  String basePath;
    // 访问图片时的基础url

    public static String baseUrl;

    @Value("${FTP.ADDRESS}")
    public void setHost(String host) {
        FTPConfig.host = host;
    }
    @Value("${FTP.PORT}")
    public void setPort(int port) {
        FTPConfig.port = port;
    }

    @Value("${FTP.USERNAME}")
    public void setUserName(String userName) {
        FTPConfig.userName = userName;
    }
    @Value("${FTP.PASSWORD}")
    public void setPassWord(String passWord) {
        FTPConfig.passWord = passWord;
    }
    @Value("${FTP.BASEPATH}")
    public void setBasePath(String basePath) {
        FTPConfig.basePath = basePath;
    }
    @Value("${IMAGE.BASE.URL}")
    public void setBaseUrl(String baseUrl) {
        FTPConfig.baseUrl = baseUrl;
    }
}
