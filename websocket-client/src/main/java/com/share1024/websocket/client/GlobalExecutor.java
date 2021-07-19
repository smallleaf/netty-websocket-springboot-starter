package com.share1024.websocket.client;

import sun.plugin.JavaRunTime;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 19:36
 * \* Description:
 * \
 */
public class GlobalExecutor {
    private static final GlobalExecutor INSTANCE = new GlobalExecutor();

    public static GlobalExecutor getInstance() {
        return INSTANCE;
    }
    public ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

}