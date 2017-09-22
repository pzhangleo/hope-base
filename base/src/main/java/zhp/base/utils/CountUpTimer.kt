package zhp.base.utils

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.os.SystemClock

/**
 * 正向计时
 * Created by zhangpeng on 16/1/22.
 */
abstract class CountUpTimer
/**
 * @param millisInFuture The number of millis in the future from the call
 * to [.start] until the countup is done and [.onFinish]
 * is called.if set 0, the countup will never end...
 * @param countDownInterval The interval along the way to receive
 * [.onTick] callbacks.
 */
(
        /**
         * Millis since epoch when alarm should stop.
         */
        private val mMillisInFuture: Long,
        /**
         * The interval in millis that the user receives callbacks
         */
        private val mCountdownInterval: Long) {

    private var mStopTimeInFuture: Long = 0

    /**
     * boolean representing if the timer was cancelled
     */
    private var mCancelled = false

    private var mStartTime: Long = 0

    /**
     * Cancel the countdown.
     */
    @Synchronized
    fun cancel() {
        mCancelled = true
        mHandler.removeMessages(MSG)
    }

    /**
     * Start the countdown.
     */
    @Synchronized
    fun start(): CountUpTimer {
        mCancelled = false
        if (mMillisInFuture <= 0) {//正向计时不会停止，会一直进行，最好不要这样干
            //            onFinish();
            //            return this;
        }
        mStartTime = SystemClock.elapsedRealtime()
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture
        mHandler.sendMessage(mHandler.obtainMessage(MSG))
        return this
    }


    /**
     * Callback fired on regular interval.
     * @param millisFromStart The amount of time until finished.
     */
    abstract fun onTick(millisFromStart: Long)

    /**
     * Callback fired when the time is up.
     */
    abstract fun onFinish()

     // handles counting down
    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {

        override fun handleMessage(msg: Message) {

            synchronized(this@CountUpTimer) {
                if (mCancelled) {
                    return
                }

                val millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime()
                val millisElapsed = SystemClock.elapsedRealtime() - mStartTime

                if (millisLeft <= 0 && mMillisInFuture > 0) {
                    onFinish()
                } else if (millisLeft < mCountdownInterval && mMillisInFuture > 0) {
                    // no tick, just delay until done
                    sendMessageDelayed(obtainMessage(MSG), millisLeft)
                } else {
                    val lastTickStart = SystemClock.elapsedRealtime()
                    onTick(millisElapsed)

                    // take into account user's onTick taking time to execute
                    var delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime()

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval

                    sendMessageDelayed(obtainMessage(MSG), delay)
                }
            }
        }
    }

    var MSG = 1
}
