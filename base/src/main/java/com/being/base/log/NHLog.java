package com.being.base.log;

import android.os.Environment;
import android.util.Log;

import com.being.base.Constant;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Log管理类
 */
public class NHLog {
    public static boolean LOG_TOGGLE = Constant.DEBUG;
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
            Timber.plant(new NHDebugTree());
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

    /** A {@link Timber.Tree Tree} for debug builds. Automatically infers the tag from the calling class. */
    public static class NHDebugTree extends Timber.Tree {
        private static final int MAX_LOG_LENGTH = 4000;
        private static final int CALL_STACK_INDEX = 7;//another call stack index in nhlog
        private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

        /**
         * Extract the tag which should be used for the message from the {@code element}. By default
         * this will use the class name without any anonymous class suffixes (e.g., {@code Foo$1}
         * becomes {@code Foo}).
         * <p>
         * Note: This will not be called if a tag(String) was specified.
         */
        protected String createStackElementTag(StackTraceElement element) {
            String tag = element.getClassName();
            Matcher m = ANONYMOUS_CLASS.matcher(tag);
            if (m.find()) {
                tag = m.replaceAll("");
            }
            return tag.substring(tag.lastIndexOf('.') + 1);
        }

        @Override
        protected final String getTag() {
            String tag = super.getTag();
            if (tag != null) {
                return tag;
            }

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
        protected void prepareLog(int priority, Throwable t, String message, Object... args) {
            super.prepareLog(priority, t, formatMessage(message, args));
        }

        /**
         * Break up {@code message} into maximum-length chunks (if needed) and send to either
         * {@link Log#println(int, String, String) Log.println()} or
         * {@link Log#wtf(String, String) Log.wtf()} for logging.
         *
         * {@inheritDoc}
         */
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message);
                } else {
                    Log.println(priority, tag, message);
                }
                return;
            }

            // Split by line, then ensure each line can fit into Log's maximum length.
            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newline);
            }
        }
    }
}
