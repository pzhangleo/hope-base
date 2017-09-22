package zhp.base.http;

import okhttp3.Call;

/**
 * 保存有发起的Call，支持waitForCompletion操作
 * Created by zhangpeng on 16/3/3.
 */
public class CallHandler {

    private volatile boolean isComplete;

    private Call mCall;

    private final Object mWaitObject = new Object();

    private Exception mException;

    public CallHandler() {
    }

    public CallHandler(Call call) {
        this.mCall = call;
    }

    public Call getCall() {
        return mCall;
    }

    /**
     * 等待任务完成，会阻塞方法执行的线程
     * 用于异步线程中的同步
     */
    public void waitForCompletion() throws Exception {
        synchronized (mWaitObject) {
            try {
                mWaitObject.wait();
            }
            catch (InterruptedException e) {
                // do nothing
            }
            if (!isComplete) {
                if (mException != null) {
                    throw mException;
                } else {
                    throw new Exception("fail");
                }
            }
        }
    }

    public void waitForCompletion(long timeout) throws Throwable {
        synchronized (mWaitObject) {
            try {
                mWaitObject.wait(timeout);
            }
            catch (InterruptedException e) {
                // do nothing
            }
            if (!isComplete) {
                if (mException != null) {
                    throw mException;
                } else {
                    throw new Exception("fail");
                }
            }
        }
    }

    /**
     * notify successful completion of the operation
     */
    public void notifyComplete() {
        synchronized (mWaitObject) {
            isComplete = true;
            mWaitObject.notifyAll();
        }
    }

    /**
     * notify unsuccessful completion of the operation
     */
    public void notifyFailure(Exception e) {
        synchronized (mWaitObject) {
            isComplete = false;
            mWaitObject.notifyAll();
            if (e != null) {
                mException = e;
            }
        }

    }

    public boolean isComplete() {
        return isComplete;
    }

    void setComplete(boolean complete) {
        isComplete = complete;
    }
}
