package com.being.base.http.callback;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 基础网络请求回调
 * GSON_TYPE为BaseObject的子类泛型
 * Created by Zhp on 2014/5/16.
 */
public abstract class ResponseCallback<GSON_TYPE> implements ICallback<GSON_TYPE> {

    protected Request mRequest;

    /**
     * 同步模式,在调用者的线程中执行
     */
    private boolean mSync;

    public ResponseCallback() {
    }

    public ResponseCallback(boolean sync) {
        mSync = sync;
    }

    public void onStart() {
    }

    /**
     * 请求进度
     * @param bytesWritten 已写入
     * @param totalSize    总数
     * @param download
     */
    public abstract void onProgress(long bytesWritten, long totalSize, boolean download);

    /**
     * 解析返回数据
     *
     * @param response 返回数据
     * @return
     */
    public abstract GSON_TYPE parseResponse(Response response) throws IOException;

    public Request updateRequestHeaders(Request request) {
        return request;
    }

    /**
     * 需要提供Gson对象
     * 用于返回数据解析
     * @return Gson对象
     */
    public abstract Gson getGson();

    /**
     * 返回需要解析的数据对象Class
     * @return 需要解析的数据对象Class
     */
    public abstract Type getClazz();

    public Request getRequest() {
        return mRequest;
    }

    public void setRequest(Request request) {
        this.mRequest = request;
    }

    public boolean isSync() {
        return mSync;
    }

}
