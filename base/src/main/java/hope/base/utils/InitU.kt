@file:JvmName("InitU")
package hope.base.utils

import android.app.Application
import hope.base.AppConstant
import hope.base.log.ZLog
import hope.base.ui.widget.fresco.FrescoHelper

fun init(app: Application, debug: Boolean) {
    AppConstant.init(app)
    ZLog.init(debug)
    FrescoHelper.init(app)
    DateTimeUtils.init(app)
}
