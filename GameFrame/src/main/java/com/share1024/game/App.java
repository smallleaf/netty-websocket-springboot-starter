package com.share1024.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/11 12:06
 * \* Description:
 * \
 */
@SpringBootApplication
@EnableAsync
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}