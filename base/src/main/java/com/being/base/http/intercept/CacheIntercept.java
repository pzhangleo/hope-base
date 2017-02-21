package com.being.base.http.intercept;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangpeng on 17/2/21.
 */

public class CacheIntercept implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response cacheResponse;
        try {
            Response netWorkResponse = chain.proceed(request);
            if ("get".equalsIgnoreCase(request.method())) {
                return netWorkResponse.newBuilder()
                        .header("Cache-Control", "max-age=3")
                        .build();
            }
            return netWorkResponse;
        } catch (IOException e) {
            e.printStackTrace();
            cacheResponse = chain.proceed(request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE).build());
            return cacheResponse;
        }
    }
}
