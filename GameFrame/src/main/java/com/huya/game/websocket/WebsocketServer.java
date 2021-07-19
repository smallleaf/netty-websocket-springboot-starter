package com.huya.game.websocket;

import com.huya.annotation.OnBinary;
import com.huya.annotation.OnEvent;
import com.huya.annotation.OnMessage;
import com.huya.annotation.ServerEndpoint;
import com.huya.ext.ServerAdapter;
import com.huya.game.model.UserInfo;
import com.huya.game.pb.pb.SocketPacket;
import com.huya.game.pb.pb.SocketPackets;
import com.huya.game.service.ConnectService;
import com.huya.pojo.Session;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.rtsp.RtspResponseStatuses.UNAUTHORIZED;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/11 12:08
 * \* Description:
 * \
 */
@ServerEndpoint
public class WebsocketServer extends ServerAdapter {
    private Logger logger = LoggerFactory.getLogger(WebsocketServer.class);
    private static final String USER_INFO_KEY = "USER_INFO";

    @Autowired
    private ConnectService connectService;

    @Autowired
    private Executor executor;


    @Override
    public void doHandShake(Session session, HttpHeaders headers, String req, MultiValueMap reqMap, String arg, Map pathMap) {
        UserInfo userInfo = getUserInfo(reqMap);
        if(!connectService.preConnect(userInfo)){
            //发送失败
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, UNAUTHORIZED,
                    Unpooled.wrappedBuffer(("check connect fail " + userInfo).getBytes()));
            HttpUtil.setContentLength(res, res.content().readableBytes());
            session.channel().writeAndFlush(res);
            close(session);
        }
    }

    @Override
    public void doOpen(Session session, HttpHeaders headers, String req, MultiValueMap reqMap, String arg, Map pathMap) {
        UserInfo userInfo = getUserInfo(reqMap);
        session.setAttribute(USER_INFO_KEY,userInfo);
        SessionManager.getInstance().addSession(userInfo.getUserId(),session);
        connectService.connectSuccess(userInfo);
    }


    private UserInfo getUserInfo(MultiValueMap reqMap){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Integer.valueOf(String.valueOf(reqMap.getFirst("userId"))));
        return userInfo;
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        logger.error("onError",throwable);
        close(session);
    }

    /**
     * 这里还是IO线程，游戏业务线程有耗时一定要放在业务线程，尽快让IO线程释放
     * @param session
     * @param bytes
     */
    @Override
    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        executor.execute(()->{
            try {
                Optional<UserInfo> userInfoOptional = Optional.ofNullable(session.getAttribute(USER_INFO_KEY));
                if(!userInfoOptional.isPresent()){
                    close(session);
                    return;
                }
                SocketPackets packets = SocketPackets.parseFrom(bytes);
                for (SocketPacket socketPacket : packets.getSocketPacketsList()) {
                    connectService.onData(userInfoOptional.get(),socketPacket);
                }
            }catch (Exception e){
                logger.error("onBinary:"+e.getMessage(),e);
            }
        });
    }

    @OnMessage
    @Override
    public void onMessage(Session session, String message) {
        logger.info("get data {}",message);
        session.sendText("get data "+ message);
    }

    @OnEvent
    @Override
    public void onEvent(Session session, Object evt) {
        if(evt instanceof IdleStateEvent){
            session.close();
        }
    }

    private void close(Session session){
        session.close();
        Optional<UserInfo> userInfoOptional = Optional.ofNullable(session.getAttribute(USER_INFO_KEY));
        if(userInfoOptional.isPresent()){
            SessionManager.getInstance().removeSession(userInfoOptional.get().getUserId(),session);
        }
    }
}