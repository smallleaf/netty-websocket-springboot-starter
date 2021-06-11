package com.huya.game.service;

import com.huya.game.model.UserInfo;
import com.huya.game.pb.pb.SocketPacket;

public interface ConnectService {

    boolean checkConnect(UserInfo userInfo);

    void connectSuccess(UserInfo userInfo);

    void onData(UserInfo userInfo, SocketPacket socketPacket);
}
