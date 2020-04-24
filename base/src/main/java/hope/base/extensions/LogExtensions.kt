package hope.base.extensions

import hope.base.log.ZLog

fun String.logd(tag: String? = null) {
    if (tag.isNullOrEmpty()) {
        ZLog.d(this)
    } else {
        ZLog.tag(tag).d(this)
    }
}

fun String.logv(tag: String? = null) {
    if (tag.isNullOrEmpty()) {
        ZLog.v(this)
    } else {
        ZLog.tag(tag).v(this)
    }
}

fun String.logi(tag: String? = null) {
    if (tag.isNullOrEmpty()) {
        ZLog.i(this)
    } else {
        ZLog.tag(tag).i(this)
    }
}

fun String.loge(tag: String? = null, throwable: Throwable? = null) {
    if (tag.isNullOrEmpty()) {
        ZLog.e(this, throwable)
    } else {
        ZLog.tag(tag).e(this, throwable) }
}
