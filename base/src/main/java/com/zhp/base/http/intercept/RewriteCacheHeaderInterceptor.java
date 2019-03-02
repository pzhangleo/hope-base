package com.zhp.base.http.intercept;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * get请求默认增加cache
 * Created by zhangpeng on 17/2/21.
 */

public class RewriteCacheHeaderInterceptor implements Interceptor {

    private int mAgeInSecond = 3;

    public RewriteCacheHeaderInterceptor(int ageInSecond) {
        mAgeInSecond = ageInSecond;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (!"get".equalsIgnoreCase(request.method())) {
            return chain.proceed(request);
        } else {
            Response netWorkResponse = chain.proceed(request);
            return netWorkResponse.newBuilder()
                    .header("Cache-Control", "max-age=" + mAgeInSecond)
                    .build();
        }
    }
}
