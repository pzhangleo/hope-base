@file:JvmName("InitU")
package hope.base.utils

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import hope.base.AppConstant
import hope.base.log.ZLog
import hope.base.ui.widget.fresco.FrescoHelper

fun init(app: Application, debug: Boolean) {
    AppConstant.getApp()
    ZLog.init(debug)
    AndroidThreeTen.init(app)
    FrescoHelper.init(app)
    DateTimeUtils.init(app)
}
