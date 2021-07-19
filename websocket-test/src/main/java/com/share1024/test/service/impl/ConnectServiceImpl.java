package com.share1024.test.service.impl;

import com.share1024.test.model.ConnectInfo;
import com.share1024.test.service.ConnectService;
import com.share1024.test.service.JSEngineService;
import com.share1024.websocket.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 19:15
 * \* Description:
 * \
 */
@Service
public class ConnectServiceImpl implements ConnectService {
    private Logger logger = LoggerFactory.getLogger(ConnectServiceImpl.class);
    private Map<Integer, Client> wsClientCache = new HashMap<>();
    @Autowired
    private JSEngineService jsEngineService;

    @Override
    public void connect(int id, String wsUrl) {
        Client client = new Client(wsUrl);
        client.connect();
        wsClientCache.put(id,client);
    }

    @Override
    public void batchConnect(String js) {
        List<ConnectInfo> connectInfos = jsEngineService.getBatchConnectUrl(js);
        int size = CollectionUtils.isEmpty(connectInfos)?0:connectInfos.size();
        long start = System.currentTimeMillis();
        logger.info("batchConnect size {}",size);
        if(size<=0){
            return;
        }
        int success = 0;
        for (ConnectInfo connectInfo : connectInfos) {
            try {
                connect(connectInfo.getId(),connectInfo.getWsUrl());
                success++;
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
        }
        logger.info("batchConnect size {} success {} cost {} ",size,success,System.currentTimeMillis() - start);
    }
}