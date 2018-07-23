package com.zhp.base.http.callback;

import android.support.annotation.Nullable;

import java.io.File;

/**
 * 下载回调
 * Created by zhangpeng on 16/2/18.
 */
public abstract class DownloadCallback extends ResponseCallbackImpl<File> {
    @Override
    public abstract void onSuccess(File baseData);

    @Override
    public abstract boolean onFail(int statusCode, @Nullable File failDate, @Nullable Throwable error);
}
