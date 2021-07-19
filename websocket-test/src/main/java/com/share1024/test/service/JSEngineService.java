package com.share1024.test.service;

import com.share1024.test.model.ConnectInfo;

import java.util.List;

public interface JSEngineService {
   List<ConnectInfo> getBatchConnectUrl(String js);
}
