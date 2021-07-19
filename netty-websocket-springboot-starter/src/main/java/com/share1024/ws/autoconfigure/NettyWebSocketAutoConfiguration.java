package com.share1024.ws.autoconfigure;

import com.share1024.ws.standard.ServerConfigProperties;
import com.share1024.ws.standard.ServerEndpointExporter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnMissingBean(ServerEndpointExporter.class)
@Configuration
@EnableConfigurationProperties(ServerConfigProperties.class)
public class NettyWebSocketAutoConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(ServerConfigProperties serverConfigProperties) {
        return new ServerEndpointExporter(serverConfigProperties);
    }


}
