package com.being.base.utils;

import android.app.Activity;
import android.app.Application;
import android.view.MotionEvent;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;

import io.bugtags.platform.BugtagsRemoteConfig;

/**
 * Bugtags工具类
 * Created by zhangpeng on 16/5/13.
 */
public class BugTagsUtils {

    public static boolean TOGGLE = true;

    public static void initBugtags(Application application, String key, int event, BugtagsOptions options) {
        if (TOGGLE) {
            Bugtags.start(key, application, event, options);
        }

    }

    public static void onResume(Activity activity) {
        if (TOGGLE) {
            Bugtags.onResume(activity);
        }
    }

    public static void onPause(Activity activity) {
        if (TOGGLE) {
            Bugtags.onPause(activity);
        }
    }

    public static void onDispatchTouchEvent(Activity activity, MotionEvent event) {
        if (TOGGLE) {
            Bugtags.onDispatchTouchEvent(activity, event);
        }
    }

    public static void setUserData(String key, String value) {
        if (TOGGLE) {
            Bugtags.setUserData(key, value);
        }
    }

    public static void sendLog(String log) {
        if (TOGGLE) {
            Bugtags.log(log);
        }
    }

    public static void sendException(Throwable e) {
        if (TOGGLE) {
            Bugtags.sendException(e);
        }
    }

}
