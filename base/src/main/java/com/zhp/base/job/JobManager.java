package com.zhp.base.job;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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


    public <T> Future<T> addJob(Callable<T> task) {
       return mExecutorService.submit(task);
    }

    public Future<?> addJob(Runnable runnable) {
        return mExecutorService.submit(runnable);
    }

    public <T> Future<T> submit(Runnable task, T result){
        return mExecutorService.submit(task, result);
    }


    public void stop() {
        mExecutorService.shutdown();
        ourInstance = null;
    }
}
