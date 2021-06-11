package com.huya.game;

import com.huya.annotation.EnableWebSocket;
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
@EnableWebSocket
@EnableAsync
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}