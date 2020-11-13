package hope.base.extensions

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import hope.base.AppConstant

private const val NO_GRAVITY = -1

fun String.toast(gravity: Int = NO_GRAVITY) {
    showToast(AppConstant.getApp(), this, Toast.LENGTH_SHORT, gravity)
}

fun String.longToast(gravity: Int = NO_GRAVITY) {
    showToast(AppConstant.getApp(), this, Toast.LENGTH_LONG, gravity)
}

private var toast: Toast? = null

/**
 * 显示提示信息
 * @param context 上下文信息
 * @param msg 文本内容
 */
private fun showToast(context: Context?, msg: String?, duration: Int, gravity: Int = NO_GRAVITY) {
    if (context == null || msg.isNullOrEmpty()) return
    val runnable = Runnable {
        toast?.cancel()
        toast = Toast.makeText(context, "", duration)
        if (gravity != NO_GRAVITY) {
            toast!!.setGravity(gravity, 0, 0)
        }
        toast!!.setText(msg)
        toast!!.show()
    }
    Handler(Looper.getMainLooper()).post(runnable)
}
