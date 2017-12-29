package zhp.base.utils

import android.os.Build

/**
 * Created by zhangpeng on 16/6/6.
 */
object DeviceCompatUtils {

    fun isHighPerformentDevide(): Boolean {
        return (Build.MODEL.contains("G9350") || Build.MODEL.contains("N9208")
                || Build.MODEL.contains("G9250") || Build.MODEL.contains("G9287C")
                || Build.MODEL.contains("G925F"))
    }

}
