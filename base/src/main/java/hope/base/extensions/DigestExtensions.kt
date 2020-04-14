package hope.base.extensions

import android.util.Base64
import hope.base.utils.codec.digest.DigestUtils

fun String?.md5Hex(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    return DigestUtils.md5Hex(this)
}

fun String?.sha256Hex(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    return DigestUtils.sha256Hex(this)
}

fun String?.base64(flag: Int = Base64.URL_SAFE): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    return Base64.encodeToString(this.toByteArray(Charsets.UTF_8), flag)
}