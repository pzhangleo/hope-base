package hope.base.extensions

import hope.base.log.ZLog

fun String.logd(tag: String = "") {
    ZLog.tag(tag).d(this)
}

fun String.logv(tag: String = "") {
    ZLog.tag(tag).v(this)
}

fun String.logi(tag: String = "") {
    ZLog.tag(tag).i(this)
}