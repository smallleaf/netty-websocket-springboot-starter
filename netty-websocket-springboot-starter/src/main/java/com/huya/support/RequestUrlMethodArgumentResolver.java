package com.huya.support;

import com.huya.annotation.RequestUri;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.core.MethodParameter;

public class RequestUrlMethodArgumentResolver implements MethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestUri.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Channel channel, Object object) throws Exception {
        return ((FullHttpRequest) object).uri();
    }
}
