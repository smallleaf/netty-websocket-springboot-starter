package com.huya.game.annotation;


import com.huya.game.config.JavaConfig;
import com.huya.game.support.SocketServerBeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yesheng
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({JavaConfig.class,SocketServerBeanPostProcessor.class})
public @interface EnableGameFrameServer {
}
