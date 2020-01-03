@file:JvmName("CacheFileUtils")
package hope.base.utils

import android.content.Context
import java.io.File

/**
 * 临时文件存储工具类
 * Created by zhangpeng on 17/1/7.
 */
private const val TEMP = "temp"
private const val JPG = ".jpg"
private const val MP4 = ".mp4"
/**
 * 生成临时文件目录
 * @param context
 * @return
 */
fun Context.generateTempDir(): File {
    return StorageUtils.getOwnFilesDirectory(this, TEMP)
}

/**
 * 生成临时文件
 * @param context
 * @param fileName
 * @return
 */
fun Context.generateTempFilePath(fileName: String): File {
    val dir = StorageUtils.getOwnFilesDirectory(this, TEMP).absolutePath
    return File(dir, fileName)
}

/**
 * 生成临时图片文件
 * @param context
 * @return
 */
fun Context.generateTempPictureFilePath(): File {
    val dir = StorageUtils.getOwnFilesDirectory(this, TEMP).absolutePath
    return File(dir, generatePictureFilename())
}

/**
 * 生成临时视频文件
 * @param context
 * @return
 */
fun Context.generateTempVideoFilePath(): File {
    val dir = StorageUtils.getOwnFilesDirectory(this, TEMP).absolutePath
    return File(dir, generateVideoFilename())
}

fun Context.getPictureFileDir(): File {
    val dir = StorageUtils.getOwnFilesDirectory(this, TEMP).absolutePath
    return File(dir)
}

fun generatePictureFilename(): String {
    val dateTake = System.currentTimeMillis()
    return "pic_$dateTake$JPG"
}

fun generateVideoFilename(): String {
    val dateTake = System.currentTimeMillis()
    return "video_$dateTake$MP4"
}
