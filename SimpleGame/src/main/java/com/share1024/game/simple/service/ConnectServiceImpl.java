package com.share1024.game.simple.service;

import com.share1024.game.model.UserInfo;
import com.share1024.game.service.ConnectService;
import org.springframework.stereotype.Service;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 17:19
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
    public void close(UserInfo userInfo) {

    }
}