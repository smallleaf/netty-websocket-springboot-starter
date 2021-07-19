package com.share1024.game.support;

import com.google.protobuf.ByteString;
import com.share1024.game.model.UserInfo;

public interface Command {

    void invoke(int uri, UserInfo userInfo, ByteString byteString);
}
