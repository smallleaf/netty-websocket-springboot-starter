package com.huya.websocket.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/15 10:24
 * \* Description:
 * \
 */
public class WebsocketClientHandler extends ChannelInboundHandlerAdapter {

    private WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;
    private MessageHandler messageHandler;
    public WebsocketClientHandler(URI uri,MessageHandler messageHandler){
        this.handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders());
        this.messageHandler = messageHandler;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        handshakeFuture = ctx.newPromise();
    }

    public void syncHand(){
        try {
            handshakeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!handshaker.isHandshakeComplete()){
            try {
                //握手协议返回，设置结束握手
                this.handshaker.finishHandshake(ctx.channel(), (FullHttpResponse)msg);
                //设置成功
                this.handshakeFuture.setSuccess();
                System.out.println("握手成功");
            }catch (Exception e){
                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("WebSocket客户端连接失败，状态为:%s", res.status());
                this.handshakeFuture.setFailure(new Exception(errorMsg));
            }
            return;
        }
        messageHandler.message(msg);
    }
}