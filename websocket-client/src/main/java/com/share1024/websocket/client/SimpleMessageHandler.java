package com.share1024.websocket.client;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/15 11:39
 * \* Description:
 * \
 */
public class SimpleMessageHandler implements MessageHandler {
    @Override
    public void message(Object msg) {
        if(msg instanceof TextWebSocketFrame){
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            System.out.println(textWebSocketFrame.text());
        }
    }
}