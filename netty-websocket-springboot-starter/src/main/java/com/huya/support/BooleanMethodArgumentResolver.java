package com.huya.support;

import com.huya.annotation.OnClose;
import com.huya.annotation.OnMessage;
import io.netty.channel.Channel;
import org.springframework.core.MethodParameter;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 12:01
 * \* Description:
 * \
 */
public class BooleanMethodArgumentResolver implements MethodArgumentResolver{
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getMethod().isAnnotationPresent(OnClose.class) && Boolean.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Channel channel, Object object) throws Exception {
        return object;
    }
}