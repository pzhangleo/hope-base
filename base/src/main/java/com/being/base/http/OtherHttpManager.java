package com.being.base.http;

import com.being.base.http.callback.GsonResponseCallbackImpl;
import com.being.base.http.callback.ResponseCallback;

/**
 * OtherHttp请求管理类
 * 不包括证书双向验证
 * Created by Zhp on 2014/5/19.
 * <pre>
 * Simple Demo:
 * HttpManager.getInstance().post(mTestUrl, requestParams, new ResponseCallbackImpl<TestGsonModel>() {

            &#064;Override
            public void onStart() {
            }

            &#064;Override
            public void onSuccess(TestGsonModel baseData) {

            }

            &#064;Override
            public void onFail(TestGsonModel failData, Throwable error) {

            }
        });
 * </pre>
 */
@SuppressWarnings("unchecked")
public class OtherHttpManager {

    private static OtherHttpManager mInstance;

    private AsyncOkHttp mAsyncOkHttp;

    public static OtherHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (OtherHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new OtherHttpManager();
                }
            }
        }
        return mInstance;
    }

    private OtherHttpManager() {
        mAsyncOkHttp = new AsyncOkHttp();
    }

    /**
     * 基础post请求，在回调中返回获取的数据。
     * 所有的回调都运行在UI线程中
     * 使用Gson处理数据
     * @param url     请求的url
     * @param params  请求的参数
     * @param responseCallback   请求的回调
     * @return RequestHandle
     */
    public CallHandler post(final String url, final RequestParams params, final ResponseCallback responseCallback) {
        return mAsyncOkHttp.post(url, params, new GsonResponseCallbackImpl(responseCallback));
    }

    /**
     * 基础get请求，在回调中返回获取的数据。
     * 所有的回调都运行在UI线程中
     * @param url     请求的url
     * @param params  请求的参数
     * @param responseCallback   请求的回调
     * @return CallHandler
     */
    public CallHandler get(final String url, final RequestParams params, final ResponseCallback responseCallback) {
        return mAsyncOkHttp.get(url, params, new GsonResponseCallbackImpl(responseCallback));
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        mAsyncOkHttp.cancelAll();
    }

}