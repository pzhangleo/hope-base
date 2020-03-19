@file:JvmName("GzipUtil")
package hope.base.utils

import java.io.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

fun String.compress(): ByteArray? {
    try {
        ByteArrayOutputStream().use { baos ->
            GZIPOutputStream(baos).use { gzipOutput ->
                gzipOutput.write(this.toByteArray(Charsets.UTF_8))
                gzipOutput.finish()
                return baos.toByteArray()
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}

fun ByteArray.decompress(): String? {
    try {
        GZIPInputStream(ByteArrayInputStream(this)).use { gzipInput ->
            StringWriter().use {
                val bf = BufferedReader(InputStreamReader(gzipInput, Charsets.UTF_8))
                var result = ""
                var line = bf.readLine()
                while (!line.isNullOrEmpty()) {
                    result += line
                    line = bf.readLine()
                }
                return result
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}