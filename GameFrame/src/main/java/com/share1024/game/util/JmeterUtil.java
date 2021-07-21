package com.share1024.game.util;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/21 19:47
 * \* Description:
 * \
 */
public class JmeterUtil {

    /**
     * jmeter要使用16进制
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}