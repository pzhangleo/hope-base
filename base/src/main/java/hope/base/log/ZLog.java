package hope.base.log;

import android.os.Environment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            Timber.plant(new DebugTree());
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
//		if (!LOG_TOGGLE) {
//			return;
//		}
//		try {
//			FileWriter fw = new FileWriter(LOG_FILE, true);
//			fw.write(sd.format(new Date()) + TAG + "\n\t" + formatMessage(msg) + "\n");
//			fw.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public static String GET_THREAD_INFO(Thread th) {
		StringBuilder sb = new StringBuilder("");
		if (th != null) {
			sb.append("Thread<id:").append(th.getId()).append(">");
		}

		return sb.toString();
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

    /** A {@link Timber.Tree Tree} for debug builds. Automatically infers the tag from the calling class. */
    static class DebugTree extends Timber.DebugTree {
        private static final int CALL_STACK_INDEX = 6;//another call stack index in nhlog

        @Nullable
        @Override
        public String getTag$timber_release() {
            // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
            // because Robolectric runs them on the JVM but on Android the elements are different.
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace.length <= CALL_STACK_INDEX) {
                throw new IllegalStateException(
                        "Synthetic stacktrace didn't have enough elements: are you using proguard?");
            }
            return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
        }

        @Override
        protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
            super.log(priority, tag, ZLog.formatMessage(message), t);
        }
    }
}
