/**
 * Copyright 2014 The Darks Logs Project (Liu lihua)
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

package darks.log.externs;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import darks.log.Logger;
import darks.log.raiing.RaiingLoggerConfig;
import darks.log.utils.TimeUtil;

/**
 * CrashHandler.java
 *
 * @author Liu lihua
 * @version 1.0.0
 */
public class AndroidCrashHandler implements UncaughtExceptionHandler {

    public static Logger log = Logger.getLogger(RaiingLoggerConfig.TAG_CATEGROY_NAME);
    //	private static Logger log = Logger.getLogger(AndroidCrashHandler.class);
    public static AndroidCrashHandler instance;
    private UncaughtExceptionHandler defaultHandler;
    private Context context;

    private Map<String, String> infos = new HashMap<String, String>();

    private SimpleDateFormat format = new SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss", Locale.getDefault());// 用于格式化日期,作为日志文件名的一部分

    private Callback callback;

    private AndroidCrashHandler() {
    }

    public static synchronized AndroidCrashHandler getInstance() {
        if (instance == null) {
            instance = new AndroidCrashHandler();
        }
        return instance;
    }

    /**
     * Setup crash parameters.
     *
     * @param context  context
     * @param callback Call back object.When ANR error happened, it will use callback to
     *                 notify developers.
     */
    public void setup(Context context, Callback callback) {
        this.callback = callback;
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && defaultHandler != null) {
            defaultHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        collectDeviceInfo(context);
        logCrash(ex);
        saveCrashInfo2File(ex);
        if (callback != null) {
            Message msg = new Message();
            msg.obj = ex;
            callback.handleMessage(msg);
        }
        return true;
    }

    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = String.valueOf(pi.versionCode);
                infos.put("VersionName", versionName);
                infos.put("VersionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            log.error("Error occured when collect package information.", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                log.error("Error occured when collect crash information.", e);
            }
        }
    }

    private void logCrash(Throwable ex) {
        log.error("===================crash崩溃==================");
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            sb.append(entry.getKey()).append('=').append(entry.getValue())
                    .append('\n');
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        try {
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
        } finally {
            printWriter.close();
        }
        sb.append(writer.toString());
        log.error(sb);
    }

    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\r\n");
        }
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        Throwable cause = ex.getCause();
        // 循环着把所有的异常信息写入writer中
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();// 记得关闭
        String result = writer.toString();
        sb.append(result);
        // 保存文件
        //设置崩溃信息
//        long timetamp = System.currentTimeMillis() / 1000L;
        long timetamp = TimeUtil.getServerTime() / 1000L;
        Date date = new Date();
        date.setTime(TimeUtil.getServerTime());
        String time = format.format(date);
        String fileName = "crash-" + time + "-" + timetamp + ".txt";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(RaiingLoggerConfig.mLogPath);
                Log.d("", "异常的保存路径为--->>>" + dir + "/"
                        + fileName);
                if (!dir.exists())
                    dir.mkdir();
                FileOutputStream fos = new FileOutputStream(dir + "/"
                        + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
                return fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}