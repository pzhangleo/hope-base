package com.being.base.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.being.base.log.NHLog;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

/**
 * Created by zhangpeng on 16/10/13.
 */

public class BaseJob extends Job {

    protected BaseJob(Params params) {
        super(params);
    }

    @Override
    public void onAdded() {
        NHLog.tag(this.getClass().getSimpleName()).d("job add");
    }

    @Override
    public void onRun() throws Throwable {
        NHLog.tag(this.getClass().getSimpleName()).d("job run");

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        NHLog.tag(this.getClass().getSimpleName()).d("job cancel");

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        NHLog.tag(this.getClass().getSimpleName()).d("job shouldReRunOnThrowable");
        return null;
    }
}
