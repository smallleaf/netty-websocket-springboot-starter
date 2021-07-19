package com.huya.game.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/11 14:28
 * \* Description:
 * \
 */
@Configuration
@ComponentScan("com.huya.game")
public class JavaConfig {

    @Bean
    public Executor executor(){
        int poolSize = 2 * Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(poolSize,poolSize,30, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
    }
}