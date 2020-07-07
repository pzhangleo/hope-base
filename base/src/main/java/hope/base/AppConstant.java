package hope.base;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by zhangpeng on 2017/7/25.
 */

public class AppConstant {

    @SuppressLint("StaticFieldLeak")
    private static Context sApp;

    public static Context getApp() {
        return sApp;
    }

    public static void init(Context application) {
        sApp = application;
    }
}
