package com.share1024.test.service.impl;

import com.share1024.test.service.ConnectService;
import com.share1024.websocket.client.Client;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 19:15
 * \* Description:
 * \
 */
@Service
public class ConnectServiceImpl implements ConnectService {
    private Map<Integer, Client> wsClientCache = new HashMap<>();

    @Override
    public void connect(int id, String wsUrl) {
        Client client = new Client(wsUrl);
        client.connect();
        wsClientCache.put(id,client);
    }
}