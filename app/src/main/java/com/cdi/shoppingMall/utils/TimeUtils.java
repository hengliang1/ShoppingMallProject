package com.cdi.shoppingMall.utils;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import darks.log.raiing.RaiingLog;
import darks.log.utils.TimeUtil;


/**
 * 为了方便，暂时从发烧总监项目中加入time处理的工具。后续可以考虑采用统一的工具类。
 *
 * @author jun.wang@raiing.com.
 * @version 1.1 2015/6/25
 */
public class TimeUtils {
    /**
     * 保存服务器时间和本地时间的差值，保持和服务器时间一致性， 单位ms
     */
    private static long sServerDiffTime = 0;


    /**
     * 获取服务器时间，单位ms
     *
     * @return 当前时间， 单位ms
     */
    public static long getServerTime() {
        long time = System.currentTimeMillis();
        return time + sServerDiffTime;
    }

    /**
     * 获取服务器时间后， 更新本地的时间
     *
     * @param serviceTime 服务器时间， 单位ms
     */
    public static void updateServiceTime(long serviceTime) {
        //更新log部分的时间
        TimeUtil.updateService(serviceTime);
        long time = System.currentTimeMillis();
        sServerDiffTime = serviceTime - time;
    }


    /**
     * 传递秒为单位时间戳, 输入格式为 比如:16:50
     *
     * @param time UNIX时间戳,单位s
     * @return 输出格式为 比如: 03:50
     */
    private static String formatSecondTime(long time) {
        Calendar mCalendar = Calendar.getInstance(Locale.getDefault());
        mCalendar.setTime(new Date(time * 1000L));
        int amOrpm = mCalendar.get(Calendar.AM_PM);
        // int hour = mCalendar.get(Calendar.HOUR);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        String result = "";
        DecimalFormat formatter = new DecimalFormat();
        formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        formatter.applyPattern("00");
        String hourS = formatter.format(hour);
        String minuteS = formatter.format(minute);

        result = hourS + ":" + minuteS;
        return result;
    }

    /**
     * 把unix时间戳转化为可读的格式，主要用于调试
     *
     * @param time unix时间戳,单位s
     * @return 返回可读的时间字符串, 时区中国, 如2015年04月17日
     */
    public static String formatSecondTime2(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日",
                Locale.SIMPLIFIED_CHINESE);
        String ret = sdf.format(new Date(time * 1000L));
        return ret;
    }


    /**
     * 把unix时间戳转化为可读的格式，主要用于调试
     *
     * @param time unix时间戳,单位s
     * @return 返回可读的时间字符串, 时区中国, 如2015年04月17日 15:30
     */
    public static String formatSecondTime3(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm",
                Locale.SIMPLIFIED_CHINESE);
        String ret = sdf.format(new Date(time * 1000L));
        return ret;
    }

    /**
     * 传递秒为单位时间戳, 输入格式 比如:2014/05/15
     *
     * @param time 单位 s
     */
    public static String formatSecondTime4(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        return format.format(new Date(time * 1000L));
    }

    /**
     * 传递秒为单位时间戳, 输入格式 比如:14/5/15
     *
     * @param time 单位 s
     */
    public static String formatSecondTime6(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy/M/dd", Locale.US);
        return format.format(new Date(time * 1000L));
    }

    /**
     * @param time UNIX时间戳,单位s
     * @return 传递秒为单位时间戳, 输入格式 比如:5/15
     */
    public static String formatSecondTime7(long time) {
        SimpleDateFormat format = new SimpleDateFormat("M/dd", Locale.US);
        return format.format(new Date(time * 1000L));
    }

    /**
     * 将unix时间戳转换成这一天的00:00的unix时间戳
     *
     * @param time 单位s
     * @return 返回格式化后的0点的时间戳
     */
    public static int formatSecondTime5(long time) {
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.setTimeInMillis(time * 1000L);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return (int) (calendar.getTimeInMillis() / 1000L);
    }

    /**
     * @param time UNIX时间戳,单位s. 分钟:秒
     * @return 传递秒为单位时间戳, 输入格式 比如:03:22
     */
    public static String formatSecondTime10(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return format.format(new Date(time * 1000L));
    }


    /**
     * 传递秒为单位时间戳, 输入格式 比如:2014/05/15 15:30:30
     *
     * @param time 单位 s
     */
    public static String formatSecondTime9(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        return format.format(new Date(time * 1000L));
    }

    /**
     * @param unix 通过UNIX时间戳获取年份
     * @return 年
     */
    public static int getYearFromUnix(long unix) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unix * 1000L);
        return calendar.get(Calendar.YEAR);
    }


    /**
     * @param unix 单位秒
     * @return 通过今天的unix获取下一天的零点的UNix(解决草泥马的各种令时问题)
     */
    public static int getNextDayCalendar(int unix) {
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.setTimeInMillis(unix * 1000L);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        //闰年2月29天
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29;
        }
        if (day == arr[month]) {
//            如果今天是本月的最后一天
            day = 1;
            if (month == 11) {
                //PS: 一月==0不是1,所以month==11表示12月
                month = 0;
                year += 1;
            } else {
                month += 1;
            }
        } else {
            day += 1;
        }


        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (int) (calendar.getTimeInMillis() / 1000L);
    }

    /**
     * 时间格式转换,  09/15 03:51
     *
     * @param time unix
     * @return
     */
    public static String formatSecondTime8(int time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd",
                Locale.SIMPLIFIED_CHINESE);
        String ret = sdf.format(new Date(time * 1000L));

        return ret + " " + formatSecondTime(time);
    }

    /**
     * HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String formatTimeHMS(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        return sdf.format(calendar.getTime());
    }

    /**
     * 把unix时间戳转化为可读的格式
     *
     * @param time 毫秒级时间戳
     * @return
     */
    public static String formatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",
                Locale.SIMPLIFIED_CHINESE);
        return sdf.format(time);
    }

    /**
     * 把unix时间戳转化为可读的格式，主要用于调试
     *
     * @param time unix时间戳,单位s
     * @return 返回可读的时间字符串, 例如 2015-03-12 15:26:33
     */
    public static String formatTime1(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.SIMPLIFIED_CHINESE);
        return sdf.format(time * 1000L);
    }

    /**
     * 把unix时间戳转化为可读的格式，主要用于调试
     *
     * @param time unix时间戳,单位s
     * @return 返回可读的时间字符串, 例如 2015-03-12
     */
    public static String formatTime2(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                Locale.SIMPLIFIED_CHINESE);
        return sdf.format(time * 1000L);
    }

    /**
     * 时间格式转换,  09-15 03:51:12
     *
     * @param time unix
     * @return
     */
    public static String formatTime3(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss",
                Locale.SIMPLIFIED_CHINESE);
        return sdf.format(new Date(time * 1000L));
    }

    /**
     * 时间格式转换,  09-15
     *
     * @param time unix
     * @return
     */
    public static String formatTime4(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.SIMPLIFIED_CHINESE);
        return sdf.format(new Date(time * 1000L));
    }

    /**
     * 时间格式转换,  2017-09-15 08-26-33
     *
     * @param time unix
     * @return
     */
    public static String formatTime5(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss",
                Locale.SIMPLIFIED_CHINESE);
        return sdf.format(new Date(time * 1000L));
    }

    /**
     * 时间格式转换,  09:15
     *
     * @param time unix
     * @return
     */
    public static String formatTime6(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.SIMPLIFIED_CHINESE);
        return sdf.format(new Date(time * 1000L));
    }

    /**
     * 把unix时间戳转化为可读的格式，主要用于调试
     *
     * @param time unix时间戳,单位s
     * @return 返回可读的时间字符串, 例如 2015-03-12 15:26
     */
    public static String formatTime7(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.SIMPLIFIED_CHINESE);
        return sdf.format(time * 1000L);
    }

    /**
     * 根据语言不同格式化不同的时间字符串,主要用户生日信息的显示
     *
     * @param timeInSecond Unix时间戳,单位s
     * @return 格式化后的时间字符串
     */
    public static String getBirthdayInfoByLanguage(int timeInSecond) {
        if (timeInSecond < 0) {
            RaiingLog.d("传入的时间参数小于0,不合法");
            return "";
        }
        String time = "";
//        if (Application..getResources().getConfiguration().locale.getCountry().equals(
//                Locale.CHINA.getCountry())) {
//            time = getTime(timeInSecond, new SimpleDateFormat("MMM yyyy", Locale.getDefault()));
//        } else {
        time = getTime(timeInSecond, new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA));
//        }
        return time;
    }

    private static String getTime(int timeInSecond, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInSecond * 1000L));
    }

    /**
     * 求两个unix相差的天数(相比较的两个unix对准到00:00),
     *
     * @param unix0 对准到00:00
     * @param unix1 对准到00:00
     * @return -1:数据异常,否则:返回2个时间相隔的天数
     */
    public static int getDaysInterval(long unix0, long unix1) {
        Calendar c = Calendar.getInstance();
        long small = unix0;
        long big = unix1;
        if (unix0 > big) {
            small = unix1;
            big = unix0;
        }
        small = getTimeAt0000(small);
        big = getTimeAt0000(big);
        c.setTimeInMillis(small * 1000L);
        int delta = -1;
        int max = 365 * 100;//防止死循环,做的最大循环数
        for (int i = 0; i < max; i++) {
//            Log.d(TAG,"求两个unix相差的天数-->>test "+i);
            if (small == big) {
                delta = i;
                break;
            }
            c.add(Calendar.DATE, 1);
            small = c.getTimeInMillis() / 1000L;
        }

        return delta;
    }

    /**
     * 将给定的unix时间戳转换为0点0分0秒的unix时间戳
     *
     * @param timeSecond unix时间戳
     * @return unix时间戳
     */
    public static long getTimeAt0000(long timeSecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeSecond * 1000L);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (calendar.getTimeInMillis() / 1000L);
    }
}
