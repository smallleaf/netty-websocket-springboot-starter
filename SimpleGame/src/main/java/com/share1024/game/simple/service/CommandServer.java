package com.share1024.game.simple.service;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.GeneratedMessageV3;
import com.share1024.game.annotation.SocketCommand;
import com.share1024.game.annotation.SocketServer;
import com.share1024.game.model.UserInfo;
import com.share1024.game.util.JmeterUtil;
import com.share1024.game.util.MessageUtil;
import com.share1024.pb.api.SocketPacket;
import com.share1024.pb.simplegame.RequestUri;
import com.share1024.pb.simplegame.TestReq;
import com.share1024.pb.simplegame.TestReqOrBuilder;
import com.share1024.pb.simplegame.TestRsp;
import com.share1024.ws.pojo.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/21 18:33
 * \* Description:
 * \
 */
@SocketServer
public class CommandServer {

    private Logger logger = LoggerFactory.getLogger(CommandServer.class);

    @SocketCommand(uri = RequestUri.test_VALUE)
    public void test(UserInfo userInfo, TestReq testReq){
        TestRsp testRsp = TestRsp.newBuilder()
                .setId(testReq.getId())
                .build();
        logger.info("test {}",testReq.getId());
        MessageUtil.push(RequestUri.test_VALUE,testRsp.toByteString(),userInfo.getUserId());
    }

    public static void main(String[] args) {
        TestReq testReq = TestReq.newBuilder()
                .setId(1).build();

        SocketPacket socketPacket = SocketPacket.newBuilder()
                .setUri(RequestUri.test_VALUE)
                .setBody(testReq.toByteString())
                .build();
        System.out.println(JmeterUtil.bytesToHexString(socketPacket.toByteArray()));
    }
}