package com.share1024.test.service;

public interface ConnectService {

    void connect(int id,String wsUrl);

    void batchConnect(String js);
}
