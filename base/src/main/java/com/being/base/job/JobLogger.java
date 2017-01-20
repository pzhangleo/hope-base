package com.being.base.job;

import android.util.Log;

import com.being.base.log.NHLog;
import com.birbit.android.jobqueue.log.CustomLogger;

/**
 * Created by zhangpeng on 16/1/19.
 */
public class JobLogger implements CustomLogger {

    public static final int NONE = 0;

    private int mLogLevel = Log.ERROR;

    @Override
    public boolean isDebugEnabled() {
        return mLogLevel >= Log.VERBOSE;
    }

    public void setDebugLevel(int l) {
        mLogLevel = l;
    }

    @Override
    public void d(String text, Object... args) {
        if (mLogLevel <= Log.DEBUG) {
            NHLog.v(text, args);
        }
    }

    @Override
    public void e(Throwable t, String text, Object... args) {
        if (mLogLevel <= Log.ERROR) {
            NHLog.e(text, t, args);
        }
    }

    @Override
    public void e(String text, Object... args) {
        if (mLogLevel <= Log.ERROR) {
            NHLog.e(text);
        }
    }

    @Override
    public void v(String text, Object... args) {
        if (mLogLevel <= Log.VERBOSE) {
            NHLog.v(text, args);
        }
    }

}
