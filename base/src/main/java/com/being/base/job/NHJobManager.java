package com.being.base.job;

import android.content.Context;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.being.base.log.NHLog;
import com.birbit.android.jobqueue.CancelResult;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.TagConstraint;
import com.birbit.android.jobqueue.config.Configuration;

/**`
 * android-priority-jobqueue管理类
 * Created by zhangpeng on 15/12/17.
 */
public class NHJobManager {
    private static NHJobManager ourInstance;

    private JobManager jobManager;

    public static NHJobManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new NHJobManager();
        }
        return ourInstance;
    }

    private NHJobManager() {
    }

    public void init(Context context, String id) {
        init(context, id, Log.ERROR);
    }

    public void init(Context context, String id, int l) {
        JobLogger logger = new JobLogger();
        logger.setDebugLevel(l);
        Configuration configuration = new Configuration.Builder(context)
                .id(id)
                .customLogger(logger)
                .build();
        jobManager = new JobManager(configuration);
        NHLog.d("create new jobmanager for userid: %s", id);
    }

    public void addJob(Job job) {
        if (jobManager != null) {
            jobManager.addJobInBackground(job);
        } else {
            NHLog.e("jobManager is null");
        }
    }

    public void cancelJobinBackground(final CancelResult.AsyncCancelCallback cancelCallback,
                                      final TagConstraint constraint, final String... tags) {
        if (jobManager != null) {
            jobManager.cancelJobsInBackground(cancelCallback, constraint, tags);
        } else {
            NHLog.e("jobManager is null");
        }
    }

    /**
     * returns the # of jobs that are waiting to be executed.
     * This might be a good place to decide whether you should wake your app up on boot etc. to complete pending jobs.
     * @return # of total jobs.
     */
    @WorkerThread
    public int getJobCount() {
        if (jobManager != null) {
            return jobManager.count();
        } else {
            return 0;
        }
    }

    public void stop() {
        if (jobManager != null) {
            jobManager.stop();
            jobManager = null;
            ourInstance = null;
        } else {
            NHLog.e("call onstop but jobManager is null");
        }
    }
}
