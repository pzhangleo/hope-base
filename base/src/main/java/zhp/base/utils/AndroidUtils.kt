package zhp.base.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * 和Android系统相关的工具类
 * Created by zhangpeng on 16/1/11.
 */


    /**
     * 获取当前进程名
     *
     * @return
     */
    fun getProcessName(): String? {
        try {
            val file = File("/proc/" + android.os.Process.myPid() + "/" + "cmdline")
            val mBufferedReader = BufferedReader(FileReader(file))
            val processName = mBufferedReader.readLine().trim { it <= ' ' }
            mBufferedReader.close()
            return processName
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun showKeyboard(view: View?) {
        if (view == null) {
            return
        }
        val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun isKeyboardShowed(view: View?): Boolean {
        if (view == null) {
            return false
        }
        val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputManager.isActive(view)
    }

    fun hideKeyboard(view: View?) {
        if (view == null) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!imm.isActive) {
            return
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @Deprecated("")
    fun openAlbum(activity: Activity, file: File, crop: Boolean, ox: Int, oy: Int, requestCode: Int) {
        openAlbum(activity, file, crop, 1, 1, ox, oy, requestCode)
    }

    @Deprecated("")
    fun openAlbum(activity: Activity, file: File?, crop: Boolean, ax: Int, ay: Int, ox: Int, oy: Int, requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)//ACTION_GET_CONTENT ACTION_PICK
        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file, ""))
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "image/*")
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy)
        }
        intent.putExtra("return-data", false)
        activity.startActivityForResult(intent, requestCode)
    }

    fun openAlbum(activity: Activity, file: File, crop: Boolean, ox: Int, oy: Int, requestCode: Int, authority: String) {
        openAlbum(activity, file, crop, 1, 1, ox, oy, requestCode, authority)
    }

    fun openAlbum(activity: Activity, file: File?, crop: Boolean, ax: Int, ay: Int, ox: Int, oy: Int, requestCode: Int, authority: String) {
        val intent = Intent(Intent.ACTION_PICK)//ACTION_GET_CONTENT ACTION_PICK
        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file, authority))
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "image/*")
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy)
        }
        intent.putExtra("return-data", false)
        activity.startActivityForResult(intent, requestCode)
    }

    /**
     * 打开相机后如果需要裁剪需要自己处理
     *
     * @param activity
     * @param file
     * @param requestCode
     */
    @Deprecated("")
    fun openCamera(activity: Activity, file: File, requestCode: Int) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        activity.startActivityForResult(intent, requestCode) //启动系统拍照页面
    }

    /**
     * 打开相机后如果需要裁剪需要自己处理
     *
     * @param activity
     * @param file
     * @param requestCode
     */
    fun openCamera(activity: Activity, file: File, requestCode: Int, authority: String) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file, authority))
        activity.startActivityForResult(intent, requestCode) //启动系统拍照页面
    }

    /**
     * @param fragment
     * @param file
     * @param crop        开启截取后，默认截取方图
     * @param ox
     * @param oy
     * @param requestCode
     */
    @Deprecated("")
    fun openAlbum(fragment: Fragment, file: File, crop: Boolean, ox: Int, oy: Int, requestCode: Int) {
        openAlbum(fragment, file, crop, 1, 1, ox, oy, requestCode)
    }

    @Deprecated("")
    fun openAlbum(fragment: Fragment, file: File?, crop: Boolean, ax: Int, ay: Int, ox: Int, oy: Int, requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)//ACTION_GET_CONTENT ACTION_PICK
        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "image/*")
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy)
        }
        intent.putExtra("return-data", false)
        fragment.startActivityForResult(intent, requestCode)
    }

    /**
     * @param fragment
     * @param file
     * @param crop        开启截取后，默认截取方图
     * @param ox
     * @param oy
     * @param requestCode
     */
    fun openAlbum(fragment: Fragment, file: File, crop: Boolean, ox: Int, oy: Int, requestCode: Int, authority: String) {
        openAlbum(fragment, file, crop, 1, 1, ox, oy, requestCode, authority)
    }

    fun openAlbum(fragment: Fragment, file: File?, crop: Boolean, ax: Int, ay: Int, ox: Int, oy: Int, requestCode: Int, authority: String) {
        val intent = Intent(Intent.ACTION_PICK)//ACTION_GET_CONTENT ACTION_PICK
        //        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(fragment.context, file, authority))
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                "image/*")
        if (crop) {
            cropIntent(intent, ax, ay, ox, oy)
        }
        intent.putExtra("return-data", false)
        fragment.startActivityForResult(intent, requestCode)
    }

    /**
     * @param fragment
     * @param file
     * @param crop        开启截取后，默认截取方图
     * @param ox
     * @param oy
     * @param requestCode
     */
    @Deprecated("")
    fun openCamera(fragment: Fragment, file: File, requestCode: Int) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        fragment.startActivityForResult(intent, requestCode) //启动系统拍照页面
    }

    /**
     * @param fragment
     * @param file
     * @param crop        开启截取后，默认截取方图
     * @param ox
     * @param oy
     * @param requestCode
     */
    fun openCamera(fragment: Fragment, file: File, requestCode: Int, authority: String) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(fragment.context, file, authority))
        fragment.startActivityForResult(intent, requestCode) //启动系统拍照页面
    }

    fun mediaScan(context: Context, file: File) {
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val uri = Uri.fromFile(file)
        intent.data = uri
        context.sendBroadcast(intent)
    }

    fun recordVideo(activity: Activity, file: File, quality: Int, durationSecond: Int, requestCode: Int) {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        if (Build.MANUFACTURER.contains("LG")) {
            intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 30 * 1024 * 1024)
        } else {
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, quality)
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, durationSecond)
        }
        activity.startActivityForResult(intent, requestCode)
    }

    fun cropImageUri(activity: Activity, uri: Uri, ax: Int, ay: Int, ox: Int, oy: Int, requestCode: Int) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop", "true")
        if (ax > 0) {
            intent.putExtra("aspectX", ax)
        }
        if (ay > 0) {
            intent.putExtra("aspectY", ay)
        }
        if (ox > 0) {
            intent.putExtra("outputX", ox)
        }
        if (oy > 0) {
            intent.putExtra("outputY", oy)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intent.putExtra("return-data", false)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection
        activity.startActivityForResult(intent, requestCode)
    }

    private fun cropIntent(intent: Intent, ax: Int, ay: Int, ox: Int, oy: Int) {
        intent.putExtra("crop", "true")
        if (ax > 0) {
            intent.putExtra("aspectX", ax)
        }
        if (ay > 0) {
            intent.putExtra("aspectY", ay)
        }
        if (ox > 0) {
            intent.putExtra("outputX", ox)
        }
        if (oy > 0) {
            intent.putExtra("outputY", oy)
        }
    }

    @SuppressLint("NewApi")
    fun getRealPathFromURI(context: Context, uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        if (uri.scheme.contains("file")) {
            return uri.path
        }

        val projection = arrayOf(MediaStore.Images.Media.DATA)
        var cursor: Cursor? = null
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                // Will return "image:x*"
                val wholeID = DocumentsContract.getDocumentId(uri)
                // Split at colon, use second item in the array
                val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                // where id is equal to
                val sel = MediaStore.Images.Media._ID + "=?"

                cursor = context.contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, arrayOf(id), null)
            } else {
                cursor = context.contentResolver.query(uri,
                        projection, null, null, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            cursor = context.contentResolver.query(uri,
                    projection, null, null, null)
        }

        if (cursor == null) {
            return null
        }

        var path: String? = null
        try {
            val column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            path = cursor.getString(column_index)
            cursor.close()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        return path
    }

    fun sendSms(context: Context, content: String) {
        try {
            val smsIntent = Intent(Intent.ACTION_VIEW)
            smsIntent.type = "vnd.android-dir/mms-sms"
            smsIntent.putExtra("sms_body", content)
            context.startActivity(smsIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun sendEmail(context: Context, content: String) {
        try {
            val data = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            data.putExtra(Intent.EXTRA_TEXT, content)
            context.startActivity(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun openMarket(context: Context) {
        val appPackageName = context.packageName // getPackageName() from Context or Activity object
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)))
        } catch (anfe: android.content.ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)))
        }

    }

    /**
     * 解析选择图片或者拍照后的resulto
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
    fun parseActivityMediaResult(context: Context, selFile: File, requestCode: Int,
                                 resultCode: Int, data: Intent?, camera: Int,
                                 album: Int): String? {
        if (resultCode == Activity.RESULT_CANCELED) {
            return selFile.path
        }
        var photoUri: Uri? = null
        if (requestCode == camera) {
            if (data != null && data.action != null) {//用line等第三方camera可能action为0
                photoUri = Uri.parse(data.action)
            } else {
                photoUri = Uri.fromFile(selFile)
            }
        } else if (requestCode == album) {
            if (data != null) {
                photoUri = data.data
            } else {
                photoUri = Uri.fromFile(selFile)
            }
        }
        val pathFromURI = getRealPathFromURI(context, photoUri)
        return pathFromURI ?: selFile.path
    }

    /**
     * 启用系统播放器播放网络流媒体视频
     *
     * @param context
     * @param url
     */
    fun openVideoPlayer(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(url), "video/mp4")
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 使用系统播放器播放本地视频文件
     *
     * @param context
     * @param file
     */
    fun openVideoPlayer(context: Context, file: File, authority: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(getUriForFile(context, file, authority), "video/mp4")
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 获取该pid对应的进程名
     *
     * @param cxt 上下文环境
     * @param pid 进程pid
     * @return
     */
    fun getProcessName(cxt: Context, pid: Int): String? {
        val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (procInfo in runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName
            }
        }
        return null
    }

    /**
     * Returns true if the device is in Doze/Idle mode. Should be called before checking the network connection because
     * the ConnectionManager may report the device is connected when it isn't during Idle mode.
     */
    @TargetApi(23)
    private fun isDozing(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            val myPackageName = context.packageName
            return !powerManager.isIgnoringBatteryOptimizations(myPackageName) && powerManager.isDeviceIdleMode
        } else {
            return false
        }
    }


    private fun getUriForFile(context: Context?, file: File?, authority: String): Uri {
        if (context == null || file == null) {
            throw NullPointerException()
        }
        val uri: Uri
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.applicationContext, authority, file)
        } else {
            uri = Uri.fromFile(file)
        }
        return uri
    }

