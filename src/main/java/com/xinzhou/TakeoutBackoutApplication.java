package com.xinzhou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


@MapperScan("com.xinzhou.dao")
@SpringBootApplication
@EnableWebSocket
public class TakeoutBackoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeoutBackoutApplication.class, args);
    }
    //打包spring项目

}
