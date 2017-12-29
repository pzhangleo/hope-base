package zhp.base.utils

//

import android.app.Application
import android.test.mock.MockContext
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日期时间工具类 Created by Zhp on 2014/7/8.
 */
class DateTimeUtils {

    companion object {

        const val PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"

        const val PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"

        const val PATTERN_YYYY_MM_DD = "yyyy-MM-dd"

        const val PATTERN_MM_DD = "MM-dd"


        /**
         * 获取默认时区id
         * @return
         */
        val defaultTimeZoneID: String
            get() = ZoneId.systemDefault().id

        @JvmStatic
        fun init(application: Application) {
            AndroidThreeTen.init(application)
        }

        /**
         * 格式化日期时间
         * @param timeInMillis 需要格式化的毫秒数
         * @param toPattern 格式化样式
         * @return
         */
        @JvmStatic
        fun formatDate(timeInMillis: Long, toPattern: String): String {
            var result = ""
            if (!StringUtils.isEmpty(toPattern)) {
                try {
                    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeInMillis), ZoneId.systemDefault())
                    result = dateTime.format(DateTimeFormatter.ofPattern(toPattern))
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            return result
        }

        /**
         * 格式化显示日期和时间
         *
         * @param timeString 需要格式化显示的时间字符串，例如2014-8-8 12:10
         * @param toPattern 格式化样式0：yyyy-MM-dd HH:mm
         * @return
         */
        @JvmStatic
        fun formatDateTime(timeString: String, toPattern: String): String? {
            val instant = Instant.parse(timeString)
            var result: String? = null
            try {
                if (!StringUtils.isEmpty(toPattern)) {
                    val formater = DateTimeFormatter.ofPattern(toPattern)
                    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                    result = localDateTime.format(formater)
                }
            } catch (e: Exception) {
                return timeString
            }

            return result
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
        @JvmStatic
        fun getMinutes(currentMs: Long, previousMs: Long): Long {
            val current = Instant.ofEpochMilli(currentMs)
            val previous = Instant.ofEpochMilli(previousMs)
            return Math.abs(ChronoUnit.MINUTES.between(current, previous))
        }

        /**
         * 返回一段间隔秒数(绝对值)
         * @return
         */
        @JvmStatic
        fun getSeconds(currentMs: Long, previousMs: Long): Long {
            val current = Instant.ofEpochMilli(currentMs)
            val previous = Instant.ofEpochMilli(previousMs)
            return Math.abs(ChronoUnit.SECONDS.between(current, previous))
        }

        /**
         * 返回一段间隔小时数
         * @return
         */
        @JvmStatic
        fun getHours(currentMs: Long, previousMs: Long): Long {
            val current = Instant.ofEpochMilli(currentMs)
            val previous = Instant.ofEpochMilli(previousMs)
            return Math.abs(ChronoUnit.HOURS.between(current, previous))
        }

        /**
         * 返回一段间隔分钟数
         * @param previousMs 毫秒数
         * @param timeZoneID 时区id 例如Asia/Shanghai
         * 使用getTimeZoneID()获取
         * @return
         */
        @JvmStatic
        fun getMinutesFromNow(previousMs: Long, timeZoneID: String): Long {
            val current = ZonedDateTime.now()
            var dateTimeZone: ZoneId? = null
            try {
                dateTimeZone = ZoneId.of(timeZoneID)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val previous = ZonedDateTime.ofInstant(Instant.ofEpochMilli(previousMs), dateTimeZone!!)
            return ChronoUnit.MINUTES.between(previous, current)
        }

        /**
         * 返回一段间隔小时数
         * @param previousMs 毫秒数
         * @param timeZoneID 时区id 例如Asia/Shanghai
         * 使用getTimeZoneID()获取
         * @return
         */
        @JvmStatic
        fun getHoursFromNow(previousMs: Long, timeZoneID: String): Long {
            val current = ZonedDateTime.now()
            var dateTimeZone: ZoneId? = null
            try {
                dateTimeZone = ZoneId.of(timeZoneID)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val previous = ZonedDateTime.ofInstant(Instant.ofEpochMilli(previousMs), dateTimeZone!!)
            return ChronoUnit.HOURS.between(previous, current)
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
        @JvmStatic
        fun dateTimeToMS(datetime: String): Long {
            if (StringUtils.isEmpty(datetime))
                return 0
            val dateTime = Instant.parse(datetime)
            return dateTime.toEpochMilli()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            AndroidThreeTen.init(MockContext())
            val dateStart = "01/14/2012 09:20:58"
            val dateStop = "01/14/2012 09:26:00"

            val format = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US)

            val d1: Date? = null
            val d2: Date? = null
            val end = 1409586000000L
            val cur = 1409586559512L

            print(getMinutes(end, cur).toString() + "\n")
            print(formatDate(end, "yyyy-MM-dd HH:mm:ss") + "\n")
            print(formatDate(cur, "yyyy-MM-dd HH:mm:ss") + "\n")

        }

    }
}
