package com.huya.game.service;

import com.huya.game.model.UserInfo;
import com.huya.game.pb.pb.SocketPacket;

public interface ConnectService {

    /**
     * 连接前检查
     * @param userInfo
     * @return
     */
    boolean preConnect(UserInfo userInfo);

    void connectSuccess(UserInfo userInfo);

    void onData(UserInfo userInfo, SocketPacket socketPacket);
}
