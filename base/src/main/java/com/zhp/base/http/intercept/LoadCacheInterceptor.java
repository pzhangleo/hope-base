package com.zhp.base.http.intercept;

import android.content.Context;

import com.zhp.base.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 没有网络的情况下,尝试使用cache
 * Created by zhangpeng on 17/2/21.
 */

public class LoadCacheInterceptor implements Interceptor {

    private Context context;

    public LoadCacheInterceptor(Context application) {
        context = application;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected(context) && "get".equalsIgnoreCase(request.method())) {
            Response cache = chain.proceed(request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build());
            if (cache.code() != 504) {
                return cache;
            } else {
                throw new IOException("Network is unavailable");
            }
        }
        return chain.proceed(request);
    }
}
