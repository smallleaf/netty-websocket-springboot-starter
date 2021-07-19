package com.huya.ext;

import com.huya.annotation.PathVariable;
import com.huya.annotation.RequestParam;
import com.huya.pojo.Session;
import io.netty.handler.codec.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface Server {

    /**
     *Override need @BeforeHandshake
     */
    default void handshake(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap
            , @PathVariable String arg, @PathVariable Map pathMap){}


    /**
     *Override need @OnOpen
     */
    default void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap,
                        @PathVariable String arg, @PathVariable Map pathMap){}

    /**
     * 发送文本消息
      * @param session
     * @param message
     */
    default void onMessage(Session session, String message) {
    }
    /**
     *Override need @OnClose
     */
    default void onClose(Session session,boolean isWebsocketClose){}

    /**
     *Override need @OnError
     */
    default void onError(Session session, Throwable throwable) {}

    /**
     *Override need @OnBinary
     */
    default void onBinary(Session session, byte[] bytes) {}

    /**
     *Override need @OnEvent
     */
    default void onEvent(Session session, Object evt) {}
}
