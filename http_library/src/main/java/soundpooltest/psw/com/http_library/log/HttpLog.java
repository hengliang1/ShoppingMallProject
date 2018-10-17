package soundpooltest.psw.com.http_library.log;

import android.util.Log;

/**
 * 用于http模块的log输出
 * Created by jun on 2016/11/2.
 */
public class HttpLog {
    private static final String TAG = "HttpLog";

    private static PrintLog mLog;

    public static void setLog(PrintLog log) {
        mLog = log;
    }

    public static void e(String tag, String s) {
        if (mLog != null) {
            mLog.log(tag + " ," + s);
        } else {
            Log.e(TAG, "e: " + s);
        }
    }

    public static void d(String tag, String s) {
        if (mLog != null) {
            mLog.log(tag + " ," + s);
        } else {
            Log.d(TAG, "e: " + s);
        }

    }

    public static void i(String tag, String s) {
        if (mLog != null) {
            mLog.log(tag + " ," + s);
        } else Log.i(TAG, "e: " + s);
    }

    public static void w(String tag, String s) {
        if (mLog != null) {
            mLog.log(tag + " ," + s);
        } else {
            Log.w(TAG, "e: " + s);
        }
    }

    /**
     * 提供对外输出http请求模块log的接口
     */
    public interface PrintLog {
        void log(String log);
    }
}
