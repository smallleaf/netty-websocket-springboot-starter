package com.share1024.game.websocket;

import com.share1024.ws.annotation.ServerEndpoint;
import com.share1024.ws.ext.ServerAdapter;
import com.share1024.game.model.UserInfo;
import com.share1024.game.service.ConnectService;
import com.share1024.game.support.Command;
import com.share1024.pb.api.SocketPacket;
import com.share1024.ws.pojo.Session;
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

    @Autowired
    private Command command;

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
        logger.info("the client connect success.{}",session.channel().toString());
    }


    private UserInfo getUserInfo(MultiValueMap reqMap){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Integer.valueOf(String.valueOf(reqMap.getFirst("userId"))));
        return userInfo;
    }

    @Override
    public void doError(Session session, Throwable throwable) {
        logger.error("onError",throwable);
        close(session);
    }

    /**
     * 这里还是IO线程，游戏业务线程有耗时一定要放在业务线程，尽快让IO线程释放
     * @param session
     * @param bytes
     */
    @Override
    public void doBinary(Session session, byte[] bytes) {
        executor.execute(()->{
            try {
                Optional<UserInfo> userInfoOptional = Optional.ofNullable(session.getAttribute(USER_INFO_KEY));
                if(!userInfoOptional.isPresent()){
                    close(session);
                    return;
                }
                SocketPacket socketPacket = SocketPacket.parseFrom(bytes);
                command.invoke(socketPacket.getUri(),userInfoOptional.get(),socketPacket.getBody());
            }catch (Exception e){
                logger.error("onBinary:"+e.getMessage(),e);
            }
        });
    }

    @Override
    public void doMessage(Session session, String message) {
        logger.info("get data {}",message);
        session.sendText("get data "+ message);
    }

    @Override
    public void doEvent(Session session, Object evt) {
        if(evt instanceof IdleStateEvent){
            logger.info("the client timeout session id {}",session.toString());
            close(session);
        }
    }

    private void close(Session session){
        session.close();
        Optional<UserInfo> userInfoOptional = Optional.ofNullable(session.getAttribute(USER_INFO_KEY));
        if(userInfoOptional.isPresent()){
            SessionManager.getInstance().removeSession(userInfoOptional.get().getUserId(),session);
            connectService.close(userInfoOptional.get());
        }
    }
}