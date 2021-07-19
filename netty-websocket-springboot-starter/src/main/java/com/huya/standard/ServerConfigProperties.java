package com.huya.standard;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 13:29
 * \* Description:
 * \
 */
@ConfigurationProperties(prefix = "spring.netty")
public class ServerConfigProperties {
    private Map<String,ServerEndpointConfigProperties> websockets = new HashMap<>();

    public Map<String, ServerEndpointConfigProperties> getWebsockets() {
        return websockets;
    }
    public void setWebsockets(Map<String, ServerEndpointConfigProperties> websockets) {
        this.websockets = websockets;
    }
}