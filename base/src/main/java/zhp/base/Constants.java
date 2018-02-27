package zhp.base;

import zhp.base.utils.DeviceInfoUtilsKt;

/**
 * Created by zhangpeng on 16/9/7.
 */
public class Constants {

    public static boolean DEBUG = false;

    public static String VERSION_NAME;

    public static int VERSION_CODE;

    public static String PACKAGE_NAME;

    public static String DEVICEID;

    public static int SCREENWIDTH;

    public static int SCREENHEIGHT;

    public static int KEYBOARD_HEIGHT;//键盘高度

    public static float SCREENDENSITY;

    public static float SCREENDENSITYDPI;

    public static float SCREENSCALEDDENSIT;

    public static String NETWORKCOUNTRYISO = "";

    public static String SIMCOUNTRYISO = "";

    public static String NETWORKOPERATOR = "";

    public static String SIMOPERATOR = "";

    public static String TimeZone = "";

    public static final String DEVICE_NAME = DeviceInfoUtilsKt.getDeviceName();

    public static final String OS_VERSION = "Android " + android.os.Build.VERSION.RELEASE;

    public static final String USER_AGENT = DeviceInfoUtilsKt.getUserAgent();

    public static final int IMAGE_SACLE_SIZE = 1280;

    public static final int MAX_PRICE = 500;

    public static int STATUSBAR_HEIGHT = 0;

    public static boolean IS_FLYME_OS;// 是否为Flyme系统
}
