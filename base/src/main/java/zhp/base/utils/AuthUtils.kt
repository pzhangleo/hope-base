package zhp.base.utils


import zhp.base.utils.codec.digest.DigestUtils
import zhp.base.utils.codec.digest.HmacUtils

/**
 * 授权相关工具类
 * Created by Zhp on 2014/5/21.
 */
class AuthUtils {

    companion object {
        @JvmStatic
        fun md5Sign(timestamp: Long, app_secret: String, access_secret: String): String {
            val text = timestamp.toString() + app_secret + access_secret
            return DigestUtils.md5Hex(text)
        }
        @JvmStatic
        fun HmacSHA1Sign(key: String, token: String, secret: String): String {
            val content = token + secret
            return HmacUtils.hmacMd5Hex(key, content)
        }
    }


}
