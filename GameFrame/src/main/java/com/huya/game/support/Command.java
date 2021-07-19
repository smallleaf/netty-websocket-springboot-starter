package com.huya.game.support;

import com.google.protobuf.ByteString;
import com.huya.game.model.UserInfo;

public interface Command {

    void invoke(int uri,UserInfo userInfo, ByteString byteString);
}
