package com.being.base.http.retrofit.calladapter;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.Nullable;

import com.being.base.http.callback.ICallback;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by zhangpeng on 17/3/21.
 */

public interface BaseCall<T> {
    void cancel();

    void enqueue(@Nullable ICallback<T> callback, Lifecycle lifecycle);

    Response<T> execute() throws IOException;

    BaseCall<T> clone();
}
