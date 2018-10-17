/**
 * Copyright 2014 The Darks Logs Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package darks.log.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import darks.log.raiing.RaiingLog;
import darks.log.utils.time.AndroidDateFormater;
import darks.log.utils.time.DateFormater;
import darks.log.utils.time.JavaDateFormater;

/**
 * TimeUtil.java
 *
 * @author Liu lihua
 * @version 1.0.0
 */
public class TimeUtil {
    private static volatile boolean androidFlag = false;

    private static ConcurrentMap<String, DateFormater> formatter;
    /**
     * 保存服务器时间和本地时间的差值，保持和服务器时间一致性， 单位ms
     */
    private static long sServerDiffTime = 0;

    static {
        androidFlag = EnvUtils.isAndroidEnv();
        formatter = new ConcurrentHashMap<String, DateFormater>();
    }

    /**
     * 把unix时间戳转化为可读的格式，主要用于调试
     *
     * @param time unix时间戳,单位s
     * @return 返回可读的时间字符串, 例如 2015-03-12-15-26-33
     */
    public static String formatTime1(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.SIMPLIFIED_CHINESE);
        return sdf.format(time * 1000L);
    }

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
    public static void updateService(long serviceTime) {
        long time = System.currentTimeMillis();
        sServerDiffTime = serviceTime - time;
    }

    /**
     * Get date formatter to adapter Java and Android platform.
     *
     * @param pattern Date pattern style
     * @return DateFormater
     */
    public static DateFormater getFormatter(String pattern) {
        DateFormater df = formatter.get(pattern);
        if (df != null) {
            return df;
        }
        synchronized (pattern) {
            df = formatter.get(pattern);
            if (df != null) {
                return df;
            }
            if (androidFlag) {
                df = getAndroidDateFormater(pattern);
            } else {
                df = new JavaDateFormater(pattern);
            }
            formatter.put(pattern, df);
        }
        return df;
    }

    /**
     * To solve the exception happened in some Android system when using
     * java.text.SimpleDateFormat.
     *
     * @param pattern Date pattern
     * @return DateFormater
     */
    private static DateFormater getAndroidDateFormater(String pattern) {
        try {
            DateFormater df = new JavaDateFormater(pattern);
            String date = df.format(new Date());
            if (date != null) {
                return df;
            }
        } catch (Exception e) {
        }
        return new AndroidDateFormater(pattern);
    }

}