package com.huya.game.service.impl;

import com.huya.game.model.UserInfo;
import com.huya.game.pb.pb.SocketPacket;
import com.huya.game.service.ConnectService;
import org.springframework.stereotype.Service;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/11 14:40
 * \* Description:
 * \
 */
@Service
public class ConnectServiceImpl implements ConnectService {
    @Override
    public boolean preConnect(UserInfo userInfo) {
        return true;
    }

    @Override
    public void connectSuccess(UserInfo userInfo) {

    }

    @Override
    public void onData(UserInfo userInfo, SocketPacket socketPacket) {

    }
}