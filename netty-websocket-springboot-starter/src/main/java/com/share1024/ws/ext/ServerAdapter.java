package com.share1024.ws.ext;

import com.share1024.ws.annotation.*;
import com.share1024.ws.pojo.Session;
import io.netty.handler.codec.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/11 16:47
 * \* Description:
 * \
 */
public abstract class ServerAdapter implements Server {


    @BeforeHandshake
    @Override
    public void handshake(Session session, HttpHeaders headers, @RequestParam String req,
                          @RequestParam MultiValueMap reqMap
            , @PathVariable String arg, @PathVariable Map pathMap) {
        doHandShake(session, headers, req, reqMap, arg, pathMap);
    }

    @OnOpen
    @Override
    public void onOpen(Session session, HttpHeaders headers, @RequestParam String req,
                       @RequestParam MultiValueMap reqMap,
                       @PathVariable String arg, @PathVariable Map pathMap) {
        doOpen(session, headers, req, reqMap, arg, pathMap);
    }

    @OnMessage
    @Override
    public void onMessage(Session session, String message) {
        doMessage(session, message);
    }

    @OnClose
    @Override
    public void onClose(Session session,boolean isWebsocketClose){
        doClose(session, isWebsocketClose);
    }

    @OnError
    @Override
    public void onError(Session session, Throwable throwable){
        doError(session, throwable);
    }

    @OnBinary
    @Override
    public void onBinary(Session session, byte[] bytes){
        doBinary(session, bytes);
    }

    @OnEvent
    @Override
    public void onEvent(Session session, Object evt){
        doEvent(session, evt);
    }

    public void doEvent(Session session, Object evt){
    }
    public void doBinary(Session session, byte[] bytes){ }

    public void doError(Session session, Throwable throwable){}

    public void doClose(Session session,boolean isWebsocketClose){
    }

    public void doMessage(Session session, String message){
    }

    public void doHandShake(Session session, HttpHeaders headers, String req,MultiValueMap reqMap,String arg,
                          Map pathMap){}

    public void doOpen(Session session, HttpHeaders headers, String req,MultiValueMap reqMap,String arg, Map pathMap){}
}