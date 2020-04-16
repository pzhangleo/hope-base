package hope.base.extensions

import hope.base.log.ZLog

fun String.logd(tag: String? = null) {
    ZLog.tag(tag).d(this)
}

fun String.logv(tag: String? = null) {
    ZLog.tag(tag).v(this)
}

fun String.logi(tag: String? = null) {
    ZLog.tag(tag).i(this)
}

fun String.loge(tag: String? = null, throwable: Throwable? = null) {
    ZLog.tag(tag).e(this, throwable)
}