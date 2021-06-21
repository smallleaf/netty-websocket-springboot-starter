package com.huya.udp.game;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.DatagramPacket;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/21 17:32
 * \* Description:
 * \
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private Channel serverChannel;
    public ServerHandler(Channel serverChannel){
        this.serverChannel = serverChannel;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof DatagramPacket){
            //TODO 判断请求是否合法
            //TODO 需要做校验，判断这个用户是否在这局游戏，对数据做签名校验，然后把地址加入到里面，
            //TODO 每一次都需要更新最新的地址信息，加入地址信息有变化，那么服务推送消息可能就会推送失败。
            //TODO 需要对数据做排序，每次请求都需要带上数据编号，需要做数据编号判断，防止消息发送乱序。
            //TODO 可以加上定时器心跳，每个2s客户端发送心跳给服务端，服务端记录连接地址，超过10s的心跳，直接从本地移除
            //TODO https://www.cnblogs.com/xueweihan/p/5482792.html
            //TODO
        }
    }
}