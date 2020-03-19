package hope.base.extensions

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import hope.base.AppConstant

fun String.toast() {
    showToast(AppConstant.getApp(), this, Toast.LENGTH_SHORT)
}

fun String.longToast() {
    showToast(AppConstant.getApp(), this, Toast.LENGTH_LONG)
}

private var toast: Toast? = null

/**
 * 显示提示信息
 * @param context        上下文信息
 * @param msg            文本内容
 */
private fun showToast(context: Context?, msg: String?, duration: Int) {
    if (context == null || msg.isNullOrEmpty()) return
    val runnable = Runnable {
        toast?.cancel()
        toast = Toast.makeText(context, "", duration)
        toast!!.setText(msg)
        toast!!.show()
    }
    Handler(Looper.getMainLooper()).post(runnable)
}