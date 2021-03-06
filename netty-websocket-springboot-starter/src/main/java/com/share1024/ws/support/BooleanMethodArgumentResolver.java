package com.share1024.ws.support;

import com.share1024.ws.annotation.OnClose;
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
        return parameter.getMethod().isAnnotationPresent(OnClose.class)
                && (boolean.class.isAssignableFrom(parameter.getParameterType()) || Boolean.class.isAssignableFrom(parameter.getParameterType()));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Channel channel, Object object) throws Exception {
        return object;
    }
}