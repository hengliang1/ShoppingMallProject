package soundpooltest.psw.com.http_library;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.event.EventBus;
import soundpooltest.psw.com.http_library.constant.HttpConstant;
import soundpooltest.psw.com.http_library.log.HttpLog;

/**
 * Created by jun on 2016/11/2.
 */
public class RaiingRequest {
    private static final String TAG = "RaiingRequest";

    /**
     * 请求响应成功
     */
    private static final int REQUEST_SUCCESS = 1;
    /**
     * 请求响应失败
     */
    private static final int REQUEST_FAILED = 2;

    /**
     * 最大可以允许的服务器无响应的时间间隔,单位ms
     */
    private static final int MAX_TIME_INTERVAL = 30 * 1000;

    /**
     * 保存请求响应的结果
     */
    private static CopyOnWriteArrayList<Integer> sResponseResult = new CopyOnWriteArrayList<>();
    /**
     * 是否可以正常连接服务器
     */
    private static boolean mConnectException = true;

    static {
        // 响应结果检查的Timer
        Timer responseCheckTimer = new Timer();
        responseCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (sResponseResult.isEmpty()) {
                    return;
                }
                int size = sResponseResult.size();
                Log.d(TAG, "responseCheckTimer: 30s内总共进行的请求次数: " + sResponseResult);
                mConnectException = true;

                for (int i = 0; i < size; i++) {
                    int value = sResponseResult.get(i);
                    if (value != REQUEST_FAILED) {
                        mConnectException = false;
                        break;
                    }
                }
                // 清空缓存
                sResponseResult.clear();
                if (mConnectException) {
                    // 通知连接服务器异常
                    EventBus.getDefault().post(new ServerConnectedStatusNotify(ServerConnectedStatusNotify.SERVER_CONNECT_EXCEPTION));
                } else {
                    // 通知连接服务器正常
                    EventBus.getDefault().post(new ServerConnectedStatusNotify(ServerConnectedStatusNotify.SERVER_CONNECT_FINE));
                }
            }
        }, MAX_TIME_INTERVAL, MAX_TIME_INTERVAL);
    }

    /**
     * post 方式请求。注意请求的URL。BASE_URL在这里统一指定的，请求超时为10S。默认接口走这个
     *
     * @param url      访问的URL，除BASE_URL以外的部分,比如: register
     * @param params   接口的参数，JSON
     * @param callBack 请求过程的回调接口
     * @param time     数据正确性校验的参数
     */
    public static void raiingJSONObjectRequest(final long time, final String url, final JSONObject params, final RaiingRequestCallBack callBack) {
        // 超时时间为10秒
        raiingJSONObjectRequestCustomOverTime(time, url, params, 10, callBack);
    }

    /**
     * 短超时的post 方式请求，为了适配某些响应时间慢的接口。注意请求的URL。BASE_URL在这里统一指定的，请求超时为3S。默认接口走这个
     *
     * @param url      访问的URL，除BASE_URL以外的部分,比如: register
     * @param params   接口的参数，JSON
     * @param callBack 请求过程的回调接口
     * @param time     数据正确性校验的参数
     */
    public static void raiingJSONObjectRequestShortOverTime(final long time, final String url, final JSONObject params, final RaiingRequestCallBack callBack) {
        // 超时时间为3秒
        raiingJSONObjectRequestCustomOverTime(time, url, params, 3, callBack);
    }

    /**
     * post 方式请求。注意请求的URL。BASE_URL在这里统一指定的
     *
     * @param url      访问的URL，除BASE_URL以外的部分,比如: register
     * @param params   接口的参数，JSON
     * @param callBack 请求过程的回调接口
     * @param overTime 接口的超时时间（单位S）
     * @param time     数据正确性校验的参数
     */
    private static void raiingJSONObjectRequestCustomOverTime(final long time, final String url, final JSONObject params, int overTime, final RaiingRequestCallBack callBack) {
        if (TextUtils.isEmpty(url)) {
            HttpLog.e(TAG, "raiingJSONObjectRequest: 传入的url参数为空");
            return;
        }

        if (params == null) {
            HttpLog.e(TAG, "raiingJSONObjectRequest: 传入的params参数为空");
            return;
        }

        if (time < 0) {
            HttpLog.e(TAG, "getDataLists: 传入的参数time不存在");
            return;
        }

        new RaiingJSONObjectRequest(url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d(TAG, "时间标志：time：" + time +
//                        "raiingJSONObjectRequest: 请求URL:" + url +
//                        " ,次数:" + reqCnt +
//                        "，响应结果: " + response.toString() +
//                        ",时间差：" + (TimeUtil.getServerTime() - time));
                if (callBack != null) {
                    callBack.onSuccessResponse(time, response);
                }
                // 请求成功
                sResponseResult.add(REQUEST_SUCCESS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                // TODO 暂时定为0
                int type = 0;
                /// 添加网络的判断
                if (error instanceof NoConnectionError) {
                    // 如果是在网络正常的时候，就将log打印出来
                    if (mConnectException) {
                        HttpLog.d(TAG, "NoConnectionError，请求接口时的时间（校准后的本地时间）：" + time +
                                " ,请求url：" + url);
                    }
                    type = HttpConstant.EXCEPTION_NOCONNECTION;
                    // 没网，请求失败
                    sResponseResult.add(REQUEST_FAILED);
                } else if (error instanceof TimeoutError) {
                    // 如果是在网络正常的时候，就将log打印出来
                    if (mConnectException) {
                        HttpLog.d(TAG, "TimeoutError，请求接口时的时间（校准后的本地时间）：" + time +
                                " ,请求url：" + url);
                    }
                    // 超时，请求失败
                    sResponseResult.add(REQUEST_FAILED);
                } else {
                    // 可以正常请求接口，请求接口失败，将错误接口的参数，url，错误信息打出来方便查找问题
                    if (response != null) {
                        byte[] htmlBodyBytes = response.data;
                        HttpLog.d(TAG, "请求接口时的时间（校准后的本地时间）：" + time +
                                " ,请求url：" + url +
                                " ,参数: " + params.toString() +
                                ",raiingJSONObjectRequest: response status code : " + response.statusCode +
                                ",response data: " + new String(htmlBodyBytes));
                    }
                    // 请求成功
                    sResponseResult.add(REQUEST_SUCCESS);
                }

                if (callBack != null) {
                    callBack.onErrorResponse(time, type, error);
                }
            }
        }).setRetryPolicy(new DefaultRetryPolicy(overTime * 1000, 0, 0));
    }

    /**
     * 服务器连接的状态通知
     */
    public static class ServerConnectedStatusNotify {
        /**
         * 服务器连接异常
         */
        public static final int SERVER_CONNECT_EXCEPTION = 1;
        /**
         * 服务器连接正常
         */
        public static final int SERVER_CONNECT_FINE = 2;

        private int type;

        public ServerConnectedStatusNotify(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
