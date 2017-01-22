package com.being.base.http;

import java.util.concurrent.ConcurrentHashMap;

import okhttp3.HttpUrl;

/**
 * 请求参数类
 * Created by zhangpeng on 15/12/30.
 */
@SuppressWarnings("unused")
public class BaseRequestParams extends RequestParams {

    /**
     * 用来拼接在所有请求url中，方便后端日志查看
     */
    protected final ConcurrentHashMap<String, String> baseUrlParams = new ConcurrentHashMap<String, String>();

    protected String mCommand;

    /**
     * 如果command中包含斜杠，
     * 则分拆后分别调用addPathSegment
     * @param command
     */
    public BaseRequestParams(String command) {
        this.mCommand = command;
        addPathSegment(command);
    }

    /**
     * 添加用户debug的基本参数
     * @param key
     * @param value
     */
    private void putBase(String key, String value) {
        if (key != null && value != null) {
            baseUrlParams.put(key, value);
        }
    }

    @Override
    public HttpUrl getUrlWithQueryString(String url) {
        if (url == null) {
            return null;
        }
        HttpUrl httpUrl = super.getUrlWithPathSegment(url);
        HttpUrl.Builder builder = httpUrl.newBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : baseUrlParams.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        for (ConcurrentHashMap.Entry<String, String> entry : this.getEntrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    /**
     * post时，将基础参数组装成queryString
     * @param url
     * @return
     */
    @Override
    public HttpUrl getUrlWithPathSegment(String url) {
        if (url == null) {
            return null;
        }
        HttpUrl httpUrl = super.getUrlWithPathSegment(url);
        HttpUrl.Builder builder = httpUrl.newBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : this.baseUrlParams.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    public String getCacheKey(String url) {
        if (url == null) {
            return null;
        }
        HttpUrl httpUrl = HttpUrl.parse(url);
        HttpUrl.Builder builder = httpUrl.newBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : this.getEntrySet()) {
            if (!entry.getKey().equals("sign") && !entry.getKey().equals("timestamp")) {
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return builder.build().toString();
    }

}
