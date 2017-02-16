package com.being.base.utils;//

import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间工具类 Created by Zhp on 2014/7/8.
 */
public final class DateTimeUtils {

	public final static String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public final static String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public final static String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

	public final static String PATTERN_MM_DD = "MM-dd";

    /**
     * 格式化显示日期yyyy-MM-dd
     *
     * @param timeInMillis 需要格式化显示的毫秒数
     * @return
     */
    public static String formatDate(long timeInMillis) {
        return formatDateTime(timeInMillis, false, 0);
    }

    /**
     * 格式化时间显示
     * @param timeInMillis
     * @param isTime 是否需要显示时分
     * @return
     */
    public static String formatDate(long timeInMillis,boolean isTime){
        return formatDateTime(timeInMillis, isTime, 0);
    }

    /**
     * 格式化显示日期yyyy-MM-dd
     *
     * @param timeString 需要格式化显示的时间字符串，例如2014-8-8 12:10
     * @return
     */
    public static String formatDate(String timeString,String fromPattern,String toPattern) {
        return formatDateTime(timeString,fromPattern,toPattern);
    }

    /**
     * 格式化日期时间
     * @param timeInMillis 需要格式化的毫秒数
     * @param toPattern 格式化样式
     * @return
     */
    public static String formatDate(long timeInMillis, String toPattern) {
        String result = "";
        if (!TextUtils.isEmpty(toPattern)) {
            try {
                DateTime dateTime = new DateTime(timeInMillis);
                result = dateTime.toString(toPattern, Locale.US);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 格式化显示日期和时间
     *
     * @param timeInMillis 需要格式化显示的毫秒数
     * @param time 是否需要时间显示
     * @param type 格式化样式0：yyyy-MM-dd HH:mm
     *             格式化样式1：MM-dd HH:mm
     *             格式化样式2：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateTime(long timeInMillis, boolean time, int type) {
        DateTime dateTime = new DateTime(timeInMillis);
        String result = null;
        switch (type) {
            case 0:
                if (time) {
                    String pattern = PATTERN_YYYY_MM_DD_HH_MM;
                    result = dateTime.toString(pattern, Locale.US);
                } else {
                    String pattern = PATTERN_YYYY_MM_DD;
                    result = dateTime.toString(pattern, Locale.US);
                }
                break;
            case 1:
                result = dateTime.toString(PATTERN_MM_DD);
                break;
            case 2:
                result = dateTime.toString(PATTERN_YYYY_MM_DD_HH_MM_SS);
                break;
            default:
                String pattern = PATTERN_YYYY_MM_DD;
                result = dateTime.toString(pattern, Locale.US);
                break;
        }
        return result;
    }

    /**
     * 格式化显示日期和时间
     *
     * @param timeString 需要格式化显示的时间字符串，例如2014-8-8 12:10
     * @param fromPattern 是否需要时间显示
     * @param toPattern 格式化样式0：yyyy-MM-dd HH:mm
     * @return
     */
    public static String formatDateTime(String timeString, String fromPattern,String toPattern) {
		String result = null;
		try {
			if (!TextUtils.isEmpty(fromPattern)
					&& !TextUtils.isEmpty(toPattern)) {
				DateTimeFormatter formater = DateTimeFormat
						.forPattern(fromPattern);
				DateTime dateTime = DateTime.parse(timeString, formater);
				result = dateTime.toString(toPattern, Locale.US);
			}
		} catch (Exception e) {
			return timeString;
		}
		return result;
	}

//    /**
//     * 计算时间间隔
//     *
//     * @param startMs 开始时间
//     * @return 几天前或几小时前等等
//     */
//    public static String periodBetweenNow(long startMs) {
//        DateTime now = DateTime.now();
//        DateTime start = new DateTime(startMs);
//        int days = Days.daysBetween(start, now).getDays();
//        if (days > 0) {
//            if (days > 7) {
//                if (start.getYear() == now.getYear()) {
//                    return formatDateTime(startMs, false, 1);
//                } else {
//                    return formatDateTime(startMs, false, 0);
//
//                }
//            }
//            if (days < 7) {
//                if (days > 1) {
//                    return String.format(FApp.getApp().getString(R.string.some_days_age), days);
//                } else {
//                    return FApp.getApp().getString(R.string.yestoday);
//                }
//            }
//        }
//        int hours = Hours.hoursBetween(start, now).getHours() % 24;
//        if (hours > 0) {
//            return String.format(FApp.getApp().getString(R.string.some_hours_ago), hours);
//        }
//        int minutes = Minutes.minutesBetween(start, now).getMinutes() % 60;
//        if (minutes > 0) {
//            return String.format(FApp.getApp().getString(R.string.some_minutes_ago), minutes);
//        }
//        int seconds = Seconds.secondsBetween(start, now).getSeconds() % 60;
//        if (seconds > 0) {
//            return String.format(FApp.getApp().getString(R.string.some_seconds_ago), seconds);
//        }
//        return FApp.getApp().getString(R.string.just_now);
//    }

    /**
     * 返回一段间隔分钟数(绝对值)
     * @return
     */
    public static int getMinutes(long currentMs, long priviousMs) {
        DateTime current = new DateTime(currentMs);
        DateTime privious = new DateTime(priviousMs);
        return Math.abs(Minutes.minutesBetween(privious, current).getMinutes());
    }

    /**
     * 返回一段间隔秒数(绝对值)
     * @return
     */
    public static int getSeconds(long currentMs, long priviousMs) {
        DateTime current = new DateTime(currentMs);
        DateTime privious = new DateTime(priviousMs);
        return Math.abs(Seconds.secondsBetween(privious, current).getSeconds());
    }

    /**
     * 返回一段间隔分钟数
     * @param priviousMs 毫秒数
     * @param timeZoneID 时区id 例如Asia/Shanghai
     *                   使用getTimeZoneID()获取
     * @return
     */
    public static int getMinutesFromNow(long priviousMs, String timeZoneID) {
        DateTime current = DateTime.now();
        DateTimeZone dateTimeZone = null;
        try {
            dateTimeZone = DateTimeZone.forID(timeZoneID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DateTime privious = new DateTime(priviousMs, dateTimeZone);
        return Minutes.minutesBetween(privious, current).getMinutes();
    }

    /**
     * 返回一段间隔小时数
     * @param priviousMs 毫秒数
     * @param timeZoneID 时区id 例如Asia/Shanghai
     *                   使用getTimeZoneID()获取
     * @return
     */
    public static int getHoursFromNow(long priviousMs, String timeZoneID) {
        DateTime current = DateTime.now();
        DateTimeZone dateTimeZone = null;
        try {
            dateTimeZone = DateTimeZone.forID(timeZoneID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DateTime privious = new DateTime(priviousMs, dateTimeZone);
        return Hours.hoursBetween(privious, current).getHours();
    }

    /**
     * 返回一段间隔小时数
     * @return
     */
    public static int getHours(long currentMs, long priviousMs) {
        DateTime current = new DateTime(currentMs);
        DateTime privious = new DateTime(priviousMs);
        return Hours.hoursBetween(privious, current).getHours();
    }

    public static String formatPeriod(long start, long end) {
        Period period = new Period(end- start);
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .printZeroIfSupported()
                .appendHours().appendSeparator(":")
                .minimumPrintedDigits(2)
                .appendMinutes().appendSeparator(":")
                .appendSeconds()
                .toFormatter();
        return formatter.print(period);
    }

    /**
     * 将2013:10:08 11:48:07如此格式的时间 转化为毫秒数
     *
     * @param datetime 字符串时间
     * @return 毫秒数
     */
    public static long dateTimeToMS(String datetime) {
        if (TextUtils.isEmpty(datetime))
            return 0;
        DateTime dateTime = DateTime.parse(datetime);
        return dateTime.getMillis();
    }

    /**
     * 获取默认时区id
     * @return
     */
    public static String getDefaultTimeZoneID() {
        return DateTimeZone.getDefault().getID();
    }

    /**
     * 获取默认时区id
     * @return
     */
    public static String getTimeZoneID(TimeZone timeZone) {
        return DateTimeZone.forTimeZone(timeZone).getID();
    }

    public static void main(String[] args) {
        String dateStart = "01/14/2012 09:20:58";
        String dateStop = "01/14/2012 09:26:00";

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);

        Date d1 = null;
        Date d2 = null;
        long end = 1409586000000l;
        long cur = 1409586559512l;
        Period period = new Period(cur- end);
        PeriodFormatter formatter = new PeriodFormatterBuilder().appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").toFormatter();
        DateTimeZone dateTimeZone = DateTimeZone.getDefault();
        System.out.print(getMinutes(end, cur) + "\n");
        System.out.print(formatDate(end, true) + "\n");
        System.out.print(formatDate(cur, true) + "\n");

    }
}
