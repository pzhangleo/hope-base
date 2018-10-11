package com.zhp.base.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 和Android系统相关的工具类
 * Created by zhangpeng on 16/1/11.
 */
public class AndroidUtils {
    public static void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static boolean isKeyboardShowed(View view) {
        if (view == null) {
            return false;
        }
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputManager.isActive(view);
    }

    public static void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive()) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Deprecated
    public static void openAlbum(Activity activity, File file, boolean crop, int ox, int oy, int requestCode) {
        openAlbum(activity, file, crop, 1, 1, ox, oy, requestCode);
    }

    @Deprecated
    public static void openAlbum(Activity activity, File file, boolean crop, int ax, int ay, int ox, int oy, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);//ACTION_GET_CONTENT ACTION_PICK
        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file, ""));
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "image/*");
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy);
        }
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void openAlbum(Activity activity, File file, boolean crop, int ox, int oy, int requestCode, String authority) {
        openAlbum(activity, file, crop, 1, 1, ox, oy, requestCode, authority);
    }

    public static void openAlbum(Activity activity, File file, boolean crop, int ax, int ay, int ox, int oy, int requestCode, String authority) {
        Intent intent = new Intent(Intent.ACTION_PICK);//ACTION_GET_CONTENT ACTION_PICK
        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file, authority));
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "image/*");
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy);
        }
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void selectVideo(Activity activity, File file, int requestCode, String authority) {
        Intent intent = new Intent(Intent.ACTION_PICK);//ACTION_GET_CONTENT ACTION_PICK
        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file, authority));
        }
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "video/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开相机后如果需要裁剪需要自己处理
     *
     * @param activity
     * @param file
     * @param requestCode
     */
    @Deprecated
    public static void openCamera(Activity activity, File file, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        activity.startActivityForResult(intent, requestCode); //启动系统拍照页面
    }

    /**
     * 打开相机后如果需要裁剪需要自己处理
     *
     * @param activity
     * @param file
     * @param requestCode
     */
    public static void openCamera(Activity activity, File file, int requestCode, String authority) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file, authority));
        activity.startActivityForResult(intent, requestCode); //启动系统拍照页面
    }

    /**
     * 打开相机后如果需要裁剪需要自己处理
     *
     * @param activity
     * @param file
     * @param requestCode
     */
    public static void openCamera(Activity activity, File file, boolean crop, int ax, int ay, int ox, int oy, int requestCode, String authority) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file, authority));
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy);
        }
        activity.startActivityForResult(intent, requestCode); //启动系统拍照页面
    }

    /**
     * @param fragment
     * @param file
     * @param crop        开启截取后，默认截取方图
     * @param ox
     * @param oy
     * @param requestCode
     */
    @Deprecated
    public static void openAlbum(Fragment fragment, File file, boolean crop, int ox, int oy, int requestCode) {
        openAlbum(fragment, file, crop, 1, 1, ox, oy, requestCode);
    }

    @Deprecated
    public static void openAlbum(Fragment fragment, File file, boolean crop, int ax, int ay, int ox, int oy, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);//ACTION_GET_CONTENT ACTION_PICK
        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "image/*");
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy);
        }
        intent.putExtra("return-data", false);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * @param fragment
     * @param file
     * @param crop        开启截取后，默认截取方图
     * @param ox
     * @param oy
     * @param requestCode
     */
    public static void openAlbum(Fragment fragment, File file, boolean crop, int ox, int oy, int requestCode, String authority) {
        openAlbum(fragment, file, crop, 1, 1, ox, oy, requestCode, authority);
    }

    public static void openAlbum(Fragment fragment, File file, boolean crop, int ax, int ay, int ox, int oy, int requestCode, String authority) {
        Intent intent = new Intent(Intent.ACTION_PICK);//ACTION_GET_CONTENT ACTION_PICK
        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(fragment.getContext(), file, authority));
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "image/*");
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy);
        }
        intent.putExtra("return-data", false);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * @param fragment
     * @param file
     * @param crop        开启截取后，默认截取方图
     * @param ox
     * @param oy
     * @param requestCode
     */
    @Deprecated
    public static void openCamera(Fragment fragment, File file, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        fragment.startActivityForResult(intent, requestCode); //启动系统拍照页面
    }

    /**
     * @param fragment
     * @param file
     * @param crop        开启截取后，默认截取方图
     * @param ox
     * @param oy
     * @param requestCode
     */
    public static void openCamera(Fragment fragment, File file, boolean crop, int ax, int ay, int ox, int oy, int requestCode, String authority) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(fragment.getContext(), file, authority));
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy);
        }
        fragment.startActivityForResult(intent, requestCode); //启动系统拍照页面
    }

    /**
     * @param fragment
     * @param file
     * @param crop        开启截取后，默认截取方图
     * @param ox
     * @param oy
     * @param requestCode
     */
    public static void openCamera(Fragment fragment, File file, int requestCode, String authority) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(fragment.getContext(), file, authority));
        fragment.startActivityForResult(intent, requestCode); //启动系统拍照页面
    }

    public static void mediaScan(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public static void recordVideo(Activity activity, File file, int quality, int durationSecond, int requestCode, String authority) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file, authority));
        if (Build.MANUFACTURER.contains("LG")) {
            intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 30 * 1024 * 1024);
        } else {
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, quality);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, durationSecond);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void cropImageUri(Activity activity, Uri uri, int ax, int ay, int ox, int oy, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        if (ax > 0) {
            intent.putExtra("aspectX", ax);
        }
        if (ay > 0) {
            intent.putExtra("aspectY", ay);
        }
        if (ox > 0) {
            intent.putExtra("outputX", ox);
        }
        if (oy > 0) {
            intent.putExtra("outputY", oy);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, requestCode);
    }

    private static void cropIntent(Intent intent, int ax, int ay, int ox, int oy) {
        intent.putExtra("crop", "true");
        if (ax > 0) {
            intent.putExtra("aspectX", ax);
        }
        if (ay > 0) {
            intent.putExtra("aspectY", ay);
        }
        if (ox > 0) {
            intent.putExtra("outputX", ox);
        }
        if (oy > 0) {
            intent.putExtra("outputY", oy);
        }
    }

    public static String getRealPathFromURI(Context context, Uri uri, File file) {
        if (uri == null) {
            return null;
        }
        if (uri.getScheme().contains("file")) {
            return uri.getPath();
        }

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            if (Build.VERSION.SDK_INT > 19) {
                // Will return "image:x*"
                String wholeID = DocumentsContract.getDocumentId(uri);
                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                cursor = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[]{id}, null);
            } else {
                cursor = context.getContentResolver().query(uri,
                        projection, null, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cursor = context.getContentResolver().query(uri,
                    projection, null, null, null);
        }
        String path = "";
        if (cursor == null) {
            if (uri.getAuthority() != null) {
                InputStream inputStream;
                try {
                    inputStream = context.getContentResolver().openInputStream(uri);
                    FileUtils.writeFile(file, inputStream);
                    path = file.getPath();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                int column_index = cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                cursor.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        return path;
    }

    public static Observable<String> getRealPathFromURIAsync(final Context context, final Uri uri,
                                                             final File file) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) {
                String realPathFromURI = null;
                try {
                    realPathFromURI = getRealPathFromURI(context, uri, file);
                    e.onNext(realPathFromURI);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    e.onError(e1);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static void sendSms(Context context, String content) {
        try {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("sms_body", content);
            context.startActivity(smsIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail(Context context, String content) {
        try {
            Intent data = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            data.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openMarket(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    /**
     * 解析选择图片或者拍照后的result
     *
     * @param context
     * @param selFile     设置的文件
     * @param requestCode
     * @param resultCode
     * @param data
     * @param camera      请求拍照或者录像的requestCode
     * @param album       请求相册的requestCode
     * @return
     */
    @Nullable
    public static String parseActivityMediaResult(Context context, File selFile, int requestCode,
                                                  int resultCode, Intent data, int camera,
                                                  int album) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return selFile != null ? selFile.getPath() : "";
        }
        Uri photoUri = null;
        if (requestCode == camera) {
            if (data != null && data.getAction() != null) {//用line等第三方camera可能action为0
                photoUri = Uri.parse(data.getAction());
            } else {
                photoUri = Uri.fromFile(selFile);
            }
        } else if (requestCode == album) {
            if (data != null) {
                photoUri = data.getData();
            } else {
                photoUri = Uri.fromFile(selFile);
            }
        }
        String pathFromURI = getRealPathFromURI(context, photoUri, selFile);
        if (pathFromURI != null) {
            return pathFromURI;
        }
        return selFile.getPath();
    }

    public static Observable<String> parseActivityMediaResultAsync(final Context context, final File selFile, final int requestCode,
                                                                   final int resultCode, final Intent data, final int camera,
                                                                   final int album) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                String result = parseActivityMediaResult(context, selFile, requestCode,
                        resultCode, data, camera, album);
                emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 启用系统播放器播放网络流媒体视频
     *
     * @param context
     * @param url
     */
    public static void openVideoPlayer(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(url), "video/mp4");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用系统播放器播放本地视频文件
     *
     * @param context
     * @param file
     */
    public static void openVideoPlayer(Context context, File file, String authority) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.setDataAndType(getUriForFile(context, file, authority), "video/mp4");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前进程名
     *
     * @return
     */
    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取该pid对应的进程名
     *
     * @param cxt 上下文环境
     * @param pid 进程pid
     * @return
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return getProcessName();
    }

    /**
     * Returns true if the device is in Doze/Idle mode. Should be called before checking the network connection because
     * the ConnectionManager may report the device is connected when it isn't during Idle mode.
     */
    @TargetApi(23)
    private static boolean isDozing(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            final String myPackageName = context.getPackageName();
            return !powerManager.isIgnoringBatteryOptimizations(myPackageName) && powerManager.isDeviceIdleMode();
        } else {
            return false;
        }
    }


    private static Uri getUriForFile(Context context, File file, String authority) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
