package com.being.base.http.intercept;

import com.being.base.utils.NetworkUtils;

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

    public LoadCacheInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isOnline() && "get".equalsIgnoreCase(request.method())) {
            return chain.proceed(request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build());
        }
        return chain.proceed(request);
    }
}
