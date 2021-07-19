package com.share1024.websocket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/15 10:11
 * \* Description:
 * \
 */
public class Client implements Runnable{
    private Channel channel;
    private URI uri;
    private Bootstrap bootstrap;
    private WebsocketClientHandler clientHandler;
    public Client(String wsUrl){
        try {
            uri = new URI(wsUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        bootstrap = new Bootstrap();
        clientHandler = new WebsocketClientHandler(uri,new SimpleMessageHandler());
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HttpClientCodec());
                        ch.pipeline().addLast(new HttpObjectAggregator(8192));
                        ch.pipeline().addLast(clientHandler);
                    }
                });
    }

    public void connect() {
        try {
            channel = bootstrap.connect(uri.getHost(),uri.getPort()).sync().channel();
            clientHandler.syncHand();
            start();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean isClose(){
        return channel == null || !channel.isActive();
    }

    private void start(){
        GlobalExecutor.getInstance().scheduledExecutorService.schedule(this,2, TimeUnit.SECONDS);
    }


    public void sendTextMessage(String text){
        channel.writeAndFlush(new TextWebSocketFrame(text));
    }

    public void write(PingWebSocketFrame pingWebSocketFrame){
        channel.writeAndFlush(pingWebSocketFrame);
    }
    @Override
    public void run() {
        try {
            PingWebSocketFrame pingWebSocketFrame = new PingWebSocketFrame();
            write(pingWebSocketFrame);
        }finally {
            if(isClose()){
                return;
            }
            GlobalExecutor.getInstance().scheduledExecutorService.schedule(this,2, TimeUnit.SECONDS);
        }
    }
}