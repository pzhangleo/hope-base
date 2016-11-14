package com.being.base.job;

import com.being.base.Constant;
import com.being.base.log.NHLog;
import com.birbit.android.jobqueue.log.CustomLogger;

/**
 * Created by zhangpeng on 16/1/19.
 */
public class JobLogger implements CustomLogger {

    @Override
    public boolean isDebugEnabled() {
        return Constant.DEBUG;
    }

    @Override
    public void d(String text, Object... args) {
        NHLog.v(text, args);
    }

    @Override
    public void e(Throwable t, String text, Object... args) {
        NHLog.e(text, t, args);
    }

    @Override
    public void e(String text, Object... args) {
        NHLog.e(text);
    }

    @Override
    public void v(String text, Object... args) {
        NHLog.v(text, args);
    }

}
