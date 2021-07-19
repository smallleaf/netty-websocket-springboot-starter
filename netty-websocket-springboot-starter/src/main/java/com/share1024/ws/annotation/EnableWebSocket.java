package com.share1024.ws.annotation;

import com.share1024.ws.autoconfigure.NettyWebSocketAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(NettyWebSocketAutoConfiguration.class)
public @interface EnableWebSocket {
}
