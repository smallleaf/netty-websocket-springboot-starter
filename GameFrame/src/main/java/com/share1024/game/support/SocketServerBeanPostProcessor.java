package com.share1024.game.support;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.ByteString;
import com.share1024.game.annotation.SocketCommand;
import com.share1024.game.annotation.SocketServer;
import com.share1024.game.model.UserInfo;
import com.share1024.game.util.PbMethodCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 14:53
 * \* Description:
 * \
 */
public class SocketServerBeanPostProcessor implements InstantiationAwareBeanPostProcessor, Command {
    private Logger logger = LoggerFactory.getLogger(SocketServerBeanPostProcessor.class);

    private Map<Integer,SocketMethod> socketMethodCache = new HashMap<>();

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(SocketServer.class)){
            for (Method method : bean.getClass().getMethods()) {
                SocketCommand socketCommand = method.getAnnotation(SocketCommand.class);
                if(socketCommand == null){
                    continue;
                }
                if(socketMethodCache.containsKey(socketCommand.uri())){
                    throw new RuntimeException("add socket method error exits the uri "+socketCommand.uri());
                }
                checkParams(method);
                socketMethodCache.put(socketCommand.uri(),new SocketMethod(method,bean));
            }
        }
        return true;
    }

    private boolean checkParams(Method method){
        for (Class<?> parameterType : method.getParameterTypes()) {
            if(!isUserInfo(parameterType) && !isPbMessage(parameterType)){
                throw new RuntimeException("the parameter type not support "+parameterType);
            }
        }
        return true;
    }

    private boolean isUserInfo(Class<?> parameterType){
        return UserInfo.class.isAssignableFrom(parameterType);
    }

    private boolean isPbMessage(Class<?> parameterType){
        return AbstractMessage.class.isAssignableFrom(parameterType);
    }


    public class SocketMethod {
        private Method method;
        private Object bean;

        public SocketMethod( Method method,Object bean) {
            this.method = method;
            this.bean = bean;
        }

        public void invoke(UserInfo userInfo,ByteString byteString){
            try {
                method.invoke(bean,getParams(userInfo,byteString));
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

        private Object[] getParams(UserInfo userInfo, ByteString byteString) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length <= 0){
                return new Object[0];
            }
            Object[] args = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                if(isUserInfo(parameterTypes[i])){
                    args[i] = userInfo;
                }
                if(isPbMessage(parameterTypes[i])){
                    args[i] = PbMethodCache.parseFrom(parameterTypes[i],byteString);
                }
            }
            return args;
        }

    }

    @Override
    public void invoke(int uri, UserInfo userInfo, ByteString byteString) {
        Optional<SocketMethod> socketMethodOp = Optional.ofNullable(socketMethodCache.get(uri));
        if(!socketMethodOp.isPresent()){
            logger.error("cannot find the uri handle {} user info {}",uri,userInfo.toString());
            return;
        }
        socketMethodOp.get().invoke(userInfo,byteString);
    }
}