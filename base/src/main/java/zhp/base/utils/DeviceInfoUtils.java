package zhp.base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.TimeZone;

import zhp.base.utils.codec.digest.DigestUtils;

/**
 * 设备属性工具类
 * Created by Zhp on 2014/5/20.
 */
public class DeviceInfoUtils {

    public static String getClientId(Context context) {
        return getDeviceId(context) + "_" + System.currentTimeMillis();
    }

    /**
     * 获取设备唯一id，不100%保证唯一
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        String tmDevice = "", tmSerial = "", androidId = "";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
        }
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return DigestUtils.sha1Hex(tmDevice+tmSerial+androidId);
    }

    public static String getNetworkCountryIso(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getNetworkCountryIso();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getSimCountryIso(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimCountryIso();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getNetworkOperator(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getNetworkOperator();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getSimOperator(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimOperator();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDeviceName() {
        return Build.DEVICE;
    }

    public static String getOsName() {
        return Build.MODEL + "," + Build.VERSION.SDK_INT + "," + Build.VERSION.RELEASE;
    }

    public static String getVersionName(Context context) {
        PackageInfo packInfo = getPackageInfo(context);

        String name = "";
        if (packInfo != null) {
            name = packInfo.versionName;
        }
        return name;
    }

    public static int getVersionCode(Context context) {
        PackageInfo packInfo = getPackageInfo(context);

        int code = 0;
        if (packInfo != null) {
            code = packInfo.versionCode;
        }
        return code;
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    public static float getScreenDensityDpi(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    public static float getScreenScaledDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.scaledDensity;
    }

    public static String getMetaData(Context context, String key) {
        Bundle metaData = null;
        String value = null;
        if (context == null || key == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                value = metaData.getString(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**.
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getNavBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }

    public static String getTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        if (timeZone != null) {
            return String.valueOf(timeZone.getRawOffset() / 1000);
        }
        return "";
    }

    /**
     * 获取UserAgent,并将非法字符排除
     * @return
     */
    public static String getUserAgent() {
        String ua = System.getProperties().getProperty("http.agent");
        for (int i = 0, length = ua.length(); i < length; i++) {
            char c = ua.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                c = ' ';
            }
        }
        return ua;
    }

}
