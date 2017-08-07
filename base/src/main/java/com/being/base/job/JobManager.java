package com.being.base.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**`
 * 顺序执行的job管理类
 * Created by zhangpeng on 15/12/17.
 */
public class JobManager {
    private static JobManager ourInstance;

    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    public static JobManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new JobManager();
        }
        return ourInstance;
    }

    private JobManager() {
    }


    public Future<?> addJob(FutureTask task) {
       return mExecutorService.submit(task);
    }

    public void stop() {
        mExecutorService.shutdown();
        ourInstance = null;
    }
}
