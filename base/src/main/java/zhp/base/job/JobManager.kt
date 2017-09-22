package zhp.base.job

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**`
 * 顺序执行的job管理类
 * Created by zhangpeng on 15/12/17.
 */
object JobManager {

    private val mExecutorService = Executors.newSingleThreadExecutor()


    fun <T> addJob(task: Callable<T>): Future<T> {
        return mExecutorService.submit(task)
    }

    fun addJob(runnable: Runnable) {
        mExecutorService.submit(runnable)
    }

    fun <T> submit(task: Runnable, result: T): Future<T> {
        return mExecutorService.submit(task, result)
    }


    fun stop() {
        mExecutorService.shutdown()
    }

    @JvmStatic
    fun getInstance() : JobManager {
        return this
    }
}
