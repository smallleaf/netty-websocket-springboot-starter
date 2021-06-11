package com.huya.game.model;

/**
 * \* @Author: yesheng
 * \* Date: 2021/6/11 14:35
 * \* Description:
 * \
 */
public class UserInfo {

    private long userId;
    private long roomId;

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", roomId=" + roomId +
                '}';
    }
}