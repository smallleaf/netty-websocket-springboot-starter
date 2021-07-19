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
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/15 10:11
 * \* Description:
 * \
 */
public class Client {
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
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void sendTextMessage(String text){
        channel.writeAndFlush(new TextWebSocketFrame(text));
    }


    public static void main(String[] args) throws InterruptedException {
        String wsUrl = "ws://127.0.0.1:80?userId=123456";
        Client client = new Client(wsUrl);
        client.connect();
        client.sendTextMessage("测试");
    }

}