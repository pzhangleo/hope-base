package hope.base.utils

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import hope.base.log.ZLog

fun init(app: Application, debug: Boolean) {
    ZLog.init(debug)
    AndroidThreeTen.init(app)
}