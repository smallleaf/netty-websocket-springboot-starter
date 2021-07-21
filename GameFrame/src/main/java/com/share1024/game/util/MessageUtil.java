package com.share1024.game.util;

import com.google.protobuf.ByteString;
import com.share1024.game.websocket.SessionManager;
import com.share1024.pb.api.SocketPacket;
import com.share1024.ws.pojo.Session;

import java.util.Optional;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/21 18:25
 * \* Description:
 * \
 */
public class MessageUtil {

    public static void push(int uri, ByteString byteString,long... userIds){
        SocketPacket socketPacket = SocketPacket.newBuilder()
                .setUri(uri)
                .setBody(byteString)
                .build();
        for (long userId : userIds) {
            Optional<Session> sessionOp = SessionManager.getInstance().getSessionByUserId(userId);
            if(sessionOp.isPresent() && sessionOp.get().isActive()){
                sessionOp.get().sendBinary(socketPacket.toByteArray());
            }
        }
    }

}