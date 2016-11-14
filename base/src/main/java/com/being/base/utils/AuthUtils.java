package com.being.base.utils;


import com.being.base.utils.codec.digest.DigestUtils;
import com.being.base.utils.codec.digest.HmacUtils;

/**
 * 授权相关工具类
 * Created by Zhp on 2014/5/21.
 */
public class AuthUtils {

    public static String md5Sign(long timestamp, String app_secret, String access_secret) {
        String text = timestamp + app_secret + access_secret;
        return DigestUtils.md5Hex(text);
    }

    public static String HmacSHA1Sign(String key, String token, String secret) {
        String content = token + secret;
        return HmacUtils.hmacMd5Hex(key, content);
    }

}
