package com.share1024.game.simple;

import com.share1024.game.annotation.EnableGameFrameServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 16:44
 * \* Description:
 * \
 */
@SpringBootApplication
@EnableGameFrameServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}