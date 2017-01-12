package com.being.base.utils;

import android.content.Context;

import java.io.File;

/**
 * 临时文件存储工具类
 * Created by zhangpeng on 17/1/7.
 */
public class CacheFileUtils {

    public static final String TEMP = "temp";

    public static final String JPG = ".jpg";

    public static final String MP4 = ".mp4";


    /**
     * 生成临时文件目录
     * @param context
     * @return
     */
    public static File generateTempDir(Context context) {
        return StorageUtils.getOwnFilesDirectory(context, TEMP);
    }

    /**
     * 生成临时文件
     * @param context
     * @param fileName
     * @return
     */
    public static File generateTempFilePath(Context context, String fileName) {
        String dir = StorageUtils.getOwnFilesDirectory(context, TEMP).getAbsolutePath();
        return new File(dir, fileName);
    }

    /**
     * 生成临时图片文件
     * @param context
     * @return
     */
    public static File generateTempPictureFilePath(Context context) {
        String dir = StorageUtils.getOwnFilesDirectory(context, TEMP).getAbsolutePath();
        return new File(dir, generatePictureFilename());
    }

    /**
     * 生成临时视频文件
     * @param context
     * @return
     */
    public static File generateTempVideoFilePath(Context context) {
        String dir = StorageUtils.getOwnFilesDirectory(context, TEMP).getAbsolutePath();
        return new File(dir, generateVideoFilename());
    }

    public static File getPictureFileDir(Context context) {
        String dir = StorageUtils.getOwnFilesDirectory(context, TEMP).getAbsolutePath();
        return new File(dir);
    }

    public static String generatePictureFilename() {
        long dateTake = System.currentTimeMillis();
        return "pic" + "_" + dateTake + JPG;
    }

    public static String generateVideoFilename() {
        long dateTake = System.currentTimeMillis();
        return "video" + "_" + dateTake + MP4;
    }
}
