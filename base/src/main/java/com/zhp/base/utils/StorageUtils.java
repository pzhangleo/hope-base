package com.zhp.base.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.zhp.base.log.NHLog;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 基础存储工具类
 * 获取各种文件路径
 * Created by Zhp on 2014/7/9.
 */
public class StorageUtils {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted and app has appropriate permission. Else -
     * Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
     * {@link Context#getCacheDir() Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    public static File getFilesDirectory(Context context) {
        return getFilesDirectory(context, true);
    }

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> (if card is mounted and app has appropriate permission) or
     * on device's file system depending incoming parameters.
     *
     * @param context        Application context
     * @param preferExternal Whether prefer external location for cache
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
     * {@link Context#getCacheDir() Context.getCacheDir()} returns null).
     */
    private static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
            NHLog.d("Can't define system cache directory! '%s' will be used.", "context.getCacheDir()");
        }
        return appCacheDir;
    }

    private static File getFilesDirectory(Context context, boolean preferExternal) {
        File appFilesDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appFilesDir = getExternalFilesDir(context);
        }
        if (appFilesDir == null) {
            appFilesDir = context.getFilesDir();
        }
        if (appFilesDir == null) {
            appFilesDir = context.getFilesDir();
            NHLog.d("Can't define system cache directory! '%s' will be used.", "context.getFilesDir()");
        }
        return appFilesDir;
    }

    /**
     * Returns specified application cache directory. Cache directory will be created on SD card by defined path if card
     * is mounted and app has appropriate permission. Else - Android defines cache directory on device's file system.
     *
     * @param context  Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "Android/data/com.yunmall.ym/cache/cachedir")
     * @return Cache {@link File directory}
     */
    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(getCacheDirectory(context), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    public static File getOwnFilesDirectory(Context context, String filesDir) {
        File appFilesDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appFilesDir = new File(getFilesDirectory(context), filesDir);
        }
        if (appFilesDir == null || (!appFilesDir.exists() && !appFilesDir.mkdirs())) {
            appFilesDir = context.getFilesDir();
        }
        return appFilesDir;
    }

    public static boolean isExternalStorageWritable(Context context) {
        if (hasExternalStoragePermission(context)) {
            String externalStorageState;
            try {
                externalStorageState = Environment.getExternalStorageState();
            } catch (NullPointerException e) { // (sh)it happens (Issue #660)
                externalStorageState = "";
            }
            return MEDIA_MOUNTED.equals(externalStorageState);
        } else {
            return false;
        }
    }

    /**
     * Will return File instance for file representing last URL segment in given folder.
     * If file already exists and renameTargetFileIfExists was set as true, will try to find file
     * which doesn't exist, naming template for such cases is "filename.ext" =&gt; "filename (%d).ext",
     * or without extension "filename" =&gt; "filename (%d)"
     *
     * @return File in given directory constructed by last segment of mRequest URL
     */
    public static File getTargetFileByParsingURL(File dir, String url,boolean renameIfExists) {
        if (!dir.isDirectory()) return dir;//不是目录文件不处理
        if (TextUtils.isEmpty(url)) return null;
        String requestURL = url;
        String filename = requestURL.substring(requestURL.lastIndexOf('/') + 1, requestURL.length());
        File targetFileRtn = new File(dir, filename);
        if (targetFileRtn.exists() && renameIfExists) {
            String format;
            if (!filename.contains(".")) {
                format = filename + " (%d)";
            } else {
                format = filename.substring(0, filename.lastIndexOf('.')) + " (%d)" + filename.substring(filename.lastIndexOf('.'), filename.length());
            }
            int index = 0;
            while (true) {
                targetFileRtn = new File(dir, String.format(format, index));
                if (!targetFileRtn.exists())
                    return targetFileRtn;
                index++;
            }
        }
        return targetFileRtn;
    }

    public static File getPictureDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }

    public static File getMovieDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    }

    protected static File getExternalCacheDir(Context context) {
        File appCacheDir = context.getExternalCacheDir();
        if (appCacheDir == null) {
            File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
            File cacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
            if (makeNoMedia(cacheDir)) return null;
            return cacheDir;
        } else {
            if (makeNoMedia(appCacheDir)) return null;
            return appCacheDir;
        }
    }

    protected static File getExternalFilesDir(Context context) {
        File appFilesDir = context.getExternalFilesDir(null);
        if (appFilesDir == null) {
            File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
            File filesDir = new File(new File(dataDir, context.getPackageName()), "files");
            if (makeNoMedia(filesDir)) return null;
            return filesDir;
        } else {
            if (makeNoMedia(appFilesDir)) return null;
            return appFilesDir;
        }
    }

    protected static boolean makeNoMedia(File cacheDir) {
        if (!cacheDir.exists()) {
            if (!cacheDir.mkdirs()) {
                NHLog.d("Unable to create external cache directory");
                return true;
            }
            try {
                new File(cacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                NHLog.d("Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return false;
    }

    protected static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
