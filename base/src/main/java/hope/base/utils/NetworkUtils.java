package hope.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.IOException;

/**
 * 网络判断工具类
 * Created by Zhp on 2014/7/3.
 */
public class NetworkUtils {

    /**
     * 获取网络状况
     *
     * @return true代表高速网络，false代表低速，null为WIFI
     */
    public static Boolean getNetWorkType(Context context) {
        NetworkInfo mNetworkInfo = getNetworkInfo(context);
        if (mNetworkInfo != null) {
            if (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return null;
            } else if (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (mNetworkInfo.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return false;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return true;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    public static boolean isWIfi(Context context) {
        return getNetWorkType(context) == null;
    }

    public static boolean isConnected(Context context) {
        NetworkInfo mNetworkInfo = getNetworkInfo(context);
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    public static boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mManager.getActiveNetworkInfo();
    }
}
