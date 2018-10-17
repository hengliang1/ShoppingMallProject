package soundpooltest.psw.com.http_library;


import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * 请求网络的回调
 */
public interface RaiingRequestCallBack {
    /**
     * 开始请求
     */
    void onStartRequest();


    /**
     * 连接服务端接口成功，并且返回json格式的数据
     *
     * @param object 请求成功响应的信息
     */
    void onSuccessResponse(long time, JSONObject object);

    /**
     * 连接服务端接口失败，返回的错误信息
     *
     * @param type 错误类型
     */
    void onErrorResponse(long time, int type, VolleyError error);
}
