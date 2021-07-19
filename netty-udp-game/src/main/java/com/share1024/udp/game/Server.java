package com.share1024.udp.game;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/21 17:25
 * \* Description:
 * \
 */
public class Server {
    private Channel serverChannel;

    public void onStart(int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            ServerHandler serverHandler = new ServerHandler(serverChannel);
            bootstrap.group(group).channel(NioDatagramChannel.class)
                    .handler(serverHandler);
            serverChannel = bootstrap.bind(port).sync().channel();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}