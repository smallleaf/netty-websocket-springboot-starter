package com.share1024.game.service;

import com.share1024.game.model.UserInfo;

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
