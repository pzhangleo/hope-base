package hope.base.log;

import android.os.Environment;
import android.util.Log;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hope.base.Constants;
import timber.log.Timber;

/**
 * Log管理类
 */
public class ZLog {
    public static boolean LOG_TOGGLE = Constants.DEBUG;
    public static final boolean thread_toggle = true;
	private static final String LOG_FILE = Environment.getExternalStorageDirectory().getPath() + "log.txt";
	private static Date date;
	public static SimpleDateFormat sd = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS] ", Locale.US);

    /**
     * 程序启动后需要调用该初始化方法
     */
    public static void init(boolean toggle) {
        LOG_TOGGLE = toggle;
        if (LOG_TOGGLE) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static Timber.Tree tag(String tag) {
        return Timber.tag(tag);
    }

    public static void v(String msg, Object... params) {
        if (!LOG_TOGGLE) {
            return;
        }
        Timber.v(msg, params);
    }

    public static void d(String msg, Object... params) {
        if (!LOG_TOGGLE) {
            return;
        }
        Timber.d(msg, params);
    }

    public static void i(String msg, Object... params) {
        if (!LOG_TOGGLE) {
            return;
        }
        Timber.i(msg, params);
    }

    public static void w(String msg, Object... params) {
        if (!LOG_TOGGLE) {
            return;
        }
        Timber.w(msg, params);
    }

    public static void e(String msg, Object... params) {
        if (!LOG_TOGGLE) {
            return;
        }
        Timber.e(msg, params);
    }

	public static void e(String msg, Throwable e, Object... params) {
        if (!LOG_TOGGLE) {
            return;
        }
        Timber.e(e, msg, params);
	}

	public static void f(String TAG, String msg) {
		if (!LOG_TOGGLE) {
			return;
		}
		try {
			FileWriter fw = new FileWriter(LOG_FILE, true);
			fw.write(sd.format(new Date()) + TAG + "\n\t" + formatMessage(msg) + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String GET_THREAD_INFO(Thread th) {
		StringBuilder sb = new StringBuilder("");
		if (th != null) {
			sb.append("Thread<id:").append(th.getId()).append(">");
		}

		return sb.toString();
	}

    public static void logHttpResponse(String TAG, String content) {
        if (LOG_TOGGLE) {
            int logcatSize = 1024 * 4 - 40;
            if (content != null) {
                for (int i = 0, j = content.length() / logcatSize; i <= j; i++) {
                    Log.v(TAG,
                            content.substring(i == 0 ? 0 : i * logcatSize - 1,
                                    i == j ? content.length() : (i + 1) * logcatSize - 1)
                                    + ""
                    );
                }
            }
        }
    }

    private static String formatMessage(String format, Object... params) {
        String msg = format;
        try {
            msg = String.format(format, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thread_toggle ? ("[" + getCurrentThreadName() + "]" + msg) : msg;
    }

    private static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

}
