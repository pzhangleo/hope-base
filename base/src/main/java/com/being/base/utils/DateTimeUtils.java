package com.being.base.utils;//

import android.test.mock.MockContext;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具类 Created by Zhp on 2014/7/8.
 */
public final class DateTimeUtils {

	public final static String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public final static String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public final static String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

	public final static String PATTERN_MM_DD = "MM-dd";

    /**
     * 格式化日期时间
     * @param timeInMillis 需要格式化的毫秒数
     * @param toPattern 格式化样式
     * @return
     */
    public static String formatDate(long timeInMillis, String toPattern) {
        String result = "";
        if (!StringUtils.isEmpty(toPattern)) {
            try {
                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeInMillis), ZoneId.systemDefault());
                result = dateTime.format(DateTimeFormatter.ofPattern(toPattern));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 格式化显示日期和时间
     *
     * @param timeString 需要格式化显示的时间字符串，例如2014-8-8 12:10
     * @param toPattern 格式化样式0：yyyy-MM-dd HH:mm
     * @return
     */
    public static String formatDateTime(String timeString,String toPattern) {
        Instant instant = Instant.parse(timeString);
        String result = null;
		try {
			if (!StringUtils.isEmpty(toPattern)) {
				DateTimeFormatter formater = DateTimeFormatter.ofPattern(toPattern);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                result = localDateTime.format(formater);
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
//                    return FApp.getApp().getString(R.string.yesterday);
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
    public static long getMinutes(long currentMs, long previousMs) {
        Instant current = Instant.ofEpochMilli(currentMs);
        Instant previous = Instant.ofEpochMilli(previousMs);
        return Math.abs(ChronoUnit.MINUTES.between(current, previous));
    }

    /**
     * 返回一段间隔秒数(绝对值)
     * @return
     */
    public static long getSeconds(long currentMs, long previousMs) {
        Instant current = Instant.ofEpochMilli(currentMs);
        Instant previous = Instant.ofEpochMilli(previousMs);
        return Math.abs(ChronoUnit.SECONDS.between(current, previous));
    }

    /**
     * 返回一段间隔小时数
     * @return
     */
    public static long getHours(long currentMs, long previousMs) {
        Instant current = Instant.ofEpochMilli(currentMs);
        Instant previous = Instant.ofEpochMilli(previousMs);
        return Math.abs(ChronoUnit.HOURS.between(current, previous));
    }

    /**
     * 返回一段间隔分钟数
     * @param previousMs 毫秒数
     * @param timeZoneID 时区id 例如Asia/Shanghai
     *                   使用getTimeZoneID()获取
     * @return
     */
    public static long getMinutesFromNow(long previousMs, String timeZoneID) {
        ZonedDateTime current = ZonedDateTime.now();
        ZoneId dateTimeZone = null;
        try {
            dateTimeZone = ZoneId.of(timeZoneID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ZonedDateTime previous = ZonedDateTime.ofInstant(Instant.ofEpochMilli(previousMs), dateTimeZone);
        return ChronoUnit.MINUTES.between(previous, current);
    }

    /**
     * 返回一段间隔小时数
     * @param previousMs 毫秒数
     * @param timeZoneID 时区id 例如Asia/Shanghai
     *                   使用getTimeZoneID()获取
     * @return
     */
    public static long getHoursFromNow(long previousMs, String timeZoneID) {
        ZonedDateTime current = ZonedDateTime.now();
        ZoneId dateTimeZone = null;
        try {
            dateTimeZone = ZoneId.of(timeZoneID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ZonedDateTime previous = ZonedDateTime.ofInstant(Instant.ofEpochMilli(previousMs), dateTimeZone);
        return ChronoUnit.HOURS.between(previous, current);
    }

//    public static String formatPeriod(long start, long end) {
//        Period period = new Period(end - start);
//        PeriodFormatter formatter = new PeriodFormatterBuilder()
//                .printZeroIfSupported()
//                .appendHours().appendSeparator(":")
//                .minimumPrintedDigits(2)
//                .appendMinutes().appendSeparator(":")
//                .appendSeconds()
//                .toFormatter();
//        return formatter.print(period);
//    }

    /**
     * 将2013:10:08 11:48:07如此格式的时间 转化为毫秒数
     *
     * @param datetime 字符串时间
     * @return 毫秒数
     */
    public static long dateTimeToMS(String datetime) {
        if (StringUtils.isEmpty(datetime))
            return 0;
        Instant dateTime = Instant.parse(datetime);
        return dateTime.toEpochMilli();
    }

    /**
     * 获取默认时区id
     * @return
     */
    public static String getDefaultTimeZoneID() {
        return ZoneId.systemDefault().getId();
    }

    public static void main(String[] args) {
        AndroidThreeTen.init(new MockContext());
        String dateStart = "01/14/2012 09:20:58";
        String dateStop = "01/14/2012 09:26:00";

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);

        Date d1 = null;
        Date d2 = null;
        long end = 1409586000000l;
        long cur = 1409586559512l;

        System.out.print(getMinutes(end, cur) + "\n");
        System.out.print(formatDate(end, "yyyy-MM-dd HH:mm:ss") + "\n");
        System.out.print(formatDate(cur, "yyyy-MM-dd HH:mm:ss") + "\n");

    }
}
