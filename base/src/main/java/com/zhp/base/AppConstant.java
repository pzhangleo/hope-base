package com.zhp.base;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * Created by zhangpeng on 2017/7/25.
 */

public class AppConstant {

    @SuppressLint("StaticFieldLeak")
    private static Application sApp;

    public static Application getApp() {
        return sApp;
    }

    public static void init(Application application) {
        sApp = application;
    }
}
