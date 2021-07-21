package com.share1024.game.websocket;

import com.share1024.ws.pojo.Session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/11 14:46
 * \* Description:
 * \
 */
public class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    private SessionManager(){}
    public static SessionManager getInstance(){
        return INSTANCE;
    }

    private Map<Long, Session> userIdSessionCache = new ConcurrentHashMap<>();

    public void addSession(long uid,Session session){
        userIdSessionCache.put(uid,session);
    }

    public Optional<Session> getSessionByUserId(long userId){
        return Optional.ofNullable(userIdSessionCache.get(userId));
    }

    /**
     *
     * @param uid
     * @param removeSession
     * @return
     */
    public void removeSession(long uid,Session removeSession){
        Optional<Session> sessionOptional = Optional.ofNullable(userIdSessionCache.get(uid));
        if(!sessionOptional.isPresent()){
            return;
        }
        synchronized (sessionOptional.get()){
            sessionOptional = Optional.ofNullable(userIdSessionCache.get(uid));
            //需要判断当前的session是否是移除的
            if(sessionOptional.isPresent() && sessionOptional.get().id().equals(removeSession.id())){
                userIdSessionCache.remove(uid);
            }
        }
    }
}