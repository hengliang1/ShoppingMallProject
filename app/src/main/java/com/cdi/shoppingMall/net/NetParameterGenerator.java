package com.cdi.shoppingMall.net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jiao.zhu on 2016/12/28.
 * 参数生成管理类
 */
public class NetParameterGenerator {

    private static final String TAG = "NetParameterGenerator";
    private static NetParameterGenerator netParameterInstance = null;

    private NetParameterGenerator() {

    }

    /**
     * 获取本类实例
     *
     * @return
     */
    public static NetParameterGenerator getInstance() {
        if (null == netParameterInstance) {
            synchronized (HttpPostManager.class) {
                if (null == netParameterInstance) {
                    netParameterInstance = new NetParameterGenerator();
                }
            }
        }
        return netParameterInstance;
    }

    /**
     * 上传操作日志
     *
     * @param operateStr  操作日志
     * @param token       token
     * @param operateTime 操作的时间
     * @return
     */
    JSONObject generateParameterGetUploadOperateLog(String operateStr, long operateTime, String token) {
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("action", operateStr);
            data.put("time", operateTime);
            jsonObject.put("data", data);
            jsonObject.put("access_token", token);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
