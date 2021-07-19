package com.huya.game.service;

import com.huya.game.model.UserInfo;

public interface ConnectService {

    /**
     * 连接前检查
     * @param userInfo
     * @return
     */
    boolean preConnect(UserInfo userInfo);

    void connectSuccess(UserInfo userInfo);

    void close(UserInfo userInfo);
}
