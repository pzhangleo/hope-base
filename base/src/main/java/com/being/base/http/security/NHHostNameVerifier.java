package com.being.base.http.security;

import android.annotation.SuppressLint;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 暂时允许所有HostName
 * Created by zhangpeng on 16/1/19.
 */
public class NHHostNameVerifier implements HostnameVerifier{
    @SuppressLint("BadHostnameVerifier")
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
