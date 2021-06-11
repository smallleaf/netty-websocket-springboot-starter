package com.huya.standard;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
public class NettyWebSocketSelector implements EnvironmentAware {

    private final Map<String, ServerEndpointConfig> endpointConfigMap = new HashMap<>();
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter(endpointConfigMap);
    }

    @Override
    public void setEnvironment(Environment environment) {
        StandardEnvironment standardEnv = (StandardEnvironment) environment;
        standardEnv.setIgnoreUnresolvableNestedPlaceholders(true);
        String prefix = "spring.websocket.";
        String websocketNames = environment.getProperty(prefix+"name");
        if(!StringUtils.isEmpty(websocketNames)){
            for (String name : websocketNames.split(",")) {
                endpointConfigMap.put(name,getServerEndPointConfig(environment,prefix+ name));
            }
        }else{
            endpointConfigMap.put("default",getServerEndPointConfig(environment,prefix));
        }
    }

    private  ServerEndpointConfig getServerEndPointConfig(Environment environment,  String prefix) {
        try {
            Class<?> binderClass = Class.forName("org.springframework.boot.context.properties.bind.Binder");
            Method getMethod = binderClass.getDeclaredMethod("get", Environment.class);
            Method bindMethod = binderClass.getDeclaredMethod("bind", String.class, Class.class);
            Object binderObject = getMethod.invoke(null, environment);
            String prefixParam = prefix.endsWith(".") ? prefix.substring(0, prefix.length() - 1) : prefix;
            Object bindResultObject = bindMethod.invoke(binderObject, prefixParam, ServerEndpointConfigProperties.class);
            Method resultGetMethod = bindResultObject.getClass().getDeclaredMethod("get");
            return new ServerEndpointConfig((ServerEndpointConfigProperties)resultGetMethod.invoke(bindResultObject));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
