@file:JvmName("NumberExtensions")
package hope.base.extensions

import java.math.BigDecimal

fun String.stripTrailingZeros(): String {
    return try {
        val number = BigDecimal(this)
        number.stripTrailingZeros().toPlainString()
    } catch (e: Exception) {
        this
    }
}
