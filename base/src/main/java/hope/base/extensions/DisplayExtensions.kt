package hope.base.extensions

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager
import hope.base.AppConstant

/**
 * Created by Victor on 2017/8/21. (ง •̀_•́)ง
 */

val displayMetrics = AppConstant.getApp().resources.displayMetrics!!

inline val screenWidth: Int
    get() = AppConstant.getApp().resources.displayMetrics.widthPixels

inline val screenHeight: Int
    get() = AppConstant.getApp().resources.displayMetrics.heightPixels

inline val screenDensity: Float
    get() = AppConstant.getApp().resources.displayMetrics.density

inline val scaledDensity: Float
    get() = AppConstant.getApp().resources.displayMetrics.scaledDensity

fun getStatusBarHeight(): Int {
    var result = 0
    val resourceId = AppConstant.getApp().resources.getIdentifier("status_bar_height",
            "dimen", "android")
    if (resourceId > 0) {
        result = AppConstant.getApp().resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun getVirNavBarHeight(): Int {
    var height: Int
    val wm = (AppConstant.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager)
    val display = wm.defaultDisplay
    val p = Point()
    display.getSize(p)
    val screenHeight = Math.max(p.y, p.x)
    val dm = DisplayMetrics()
    val c: Class<*> = Class.forName("android.view.Display")
    val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
    method.invoke(display, dm)
    // 横屏在右|竖屏在底
    height = Math.max(dm.heightPixels, dm.widthPixels) - screenHeight
    // 横屏在底|竖屏在底
    if (height == 0) {
        height = Math.min(dm.heightPixels, dm.widthPixels) - Math.min(p.y, p.x)
    }
    return height
}
