package zhp.base.utils

import android.content.Context

import java.io.File

/**
 * 临时文件存储工具类
 * Created by zhangpeng on 17/1/7.
 */
class CacheFileUtils {
    companion object {

        const val TEMP = "temp"

        const val JPG = ".jpg"

        const val MP4 = ".mp4"

        /**
         * 生成临时文件目录
         * @param context
         * @return
         */
        @JvmStatic
        fun generateTempDir(context: Context): File {
            return StorageUtils.getOwnFilesDirectory(context, TEMP)
        }

        /**
         * 生成临时文件
         * @param context
         * @param fileName
         * @return
         */
        @JvmStatic
        fun generateTempFilePath(context: Context, fileName: String): File {
            val dir = StorageUtils.getOwnFilesDirectory(context, TEMP).absolutePath
            return File(dir, fileName)
        }

        /**
         * 生成临时图片文件
         * @param context
         * @return
         */
        @JvmStatic
        fun generateTempPictureFilePath(context: Context): File {
            val dir = StorageUtils.getOwnFilesDirectory(context, TEMP).absolutePath
            return File(dir, generatePictureFilename())
        }

        /**
         * 生成临时视频文件
         * @param context
         * @return
         */
        @JvmStatic
        fun generateTempVideoFilePath(context: Context): File {
            val dir = StorageUtils.getOwnFilesDirectory(context, TEMP).absolutePath
            return File(dir, generateVideoFilename())
        }
        @JvmStatic
        fun getPictureFileDir(context: Context): File {
            val dir = StorageUtils.getOwnFilesDirectory(context, TEMP).absolutePath
            return File(dir)
        }
        @JvmStatic
        fun generatePictureFilename(): String {
            val dateTake = System.currentTimeMillis()
            return "pic" + "_" + dateTake + JPG
        }
        @JvmStatic
        fun generateVideoFilename(): String {
            val dateTake = System.currentTimeMillis()
            return "video" + "_" + dateTake + MP4
        }
    }


}
