package com.zhp.base.utils

import java.io.*
import java.nio.charset.Charset
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class GzipUtil {

    companion object {

        @JvmStatic
        fun compress(stringToCompress: String): ByteArray? {
            try {
                ByteArrayOutputStream().use { baos ->
                    GZIPOutputStream(baos).use { gzipOutput ->
                        gzipOutput.write(stringToCompress.toByteArray(Charset.forName("UTF-8")))
                        gzipOutput.finish()
                        return baos.toByteArray()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }

        @JvmStatic
        fun decompress(compressed: ByteArray): String? {
            try {
                GZIPInputStream(ByteArrayInputStream(compressed)).use { gzipInput ->
                    StringWriter().use { stringWriter ->
                        val bf = BufferedReader(InputStreamReader(gzipInput, "UTF-8"))
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

    }

}