package com.huya.ext;

import com.huya.annotation.*;
import com.huya.pojo.Session;
import io.netty.handler.codec.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/11 16:47
 * \* Description:
 * \
 */
public abstract class ServerAdapter implements Server {


    @BeforeHandshake
    @Override
    public void handshake(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap
            , @PathVariable String arg, @PathVariable Map pathMap) {
        doHandShake(session, headers, req, reqMap, arg, pathMap);
    }

    @OnOpen
    @Override
    public void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap,
                       @PathVariable String arg, @PathVariable Map pathMap) {
        doOpen(session, headers, req, reqMap, arg, pathMap);
    }

    public void doHandShake(Session session, HttpHeaders headers, String req,MultiValueMap reqMap,String arg,
                          Map pathMap){}

    public void doOpen(Session session, HttpHeaders headers, String req,MultiValueMap reqMap,String arg, Map pathMap){}
}