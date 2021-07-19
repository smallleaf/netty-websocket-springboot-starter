package com.huya.autoconfigure;

import com.huya.standard.ServerConfigProperties;
import com.huya.standard.ServerEndpointExporter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@ConditionalOnMissingBean(ServerEndpointExporter.class)
@Configuration
@EnableConfigurationProperties(ServerConfigProperties.class)
public class NettyWebSocketAutoConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(ServerConfigProperties serverConfigProperties) {
        return new ServerEndpointExporter(serverConfigProperties);
    }


}
