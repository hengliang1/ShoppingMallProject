package com.cdi.shoppingMall.net;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jiao.zhu on 2016/12/20.
 * 所有的网络请求集中在此封装
 * 这儿继承SimulationField，是为了让代码显得更简洁
 */

public class HttpPostManager {

    private static final String TAG = "HttpPostManager";
    /**
     * HttpPostManager 本类单例实例
     */
    private static HttpPostManager netInstance = null;

    /**
     * token，接口拉数据的一个依赖参数
     */
    private String mAccessToken;
    /**
     * 是否正在请求接口
     */
    private boolean mIsRequesting = false;

    /**
     * 空构造
     */
    private HttpPostManager() {
    }

    /**
     * 获取本类实例
     */
    public static HttpPostManager getInstance() {
        if (null == netInstance) {
            synchronized (HttpPostManager.class) {
                if (null == netInstance) {
                    netInstance = new HttpPostManager();
                }
            }
        }
        return netInstance;
    }

    /**
     * 登陆获取access_token会话标识
     *
     * @param object 登陆成功获取的服务器返回的结果
     */
    private String getAccessToken(JSONObject object) {
        try {
            JSONObject data = object.getJSONObject("data");
            String access_token = data.getString("access_token");
            if (!TextUtils.isEmpty(access_token)) {
                return access_token;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    /**
//     * 登陆接口
//     * login
//     *
//     * @param appId    AppId，登陆时参数
//     * @param secret   secret，登陆时参数
//     * @param callback 异步请求，结果的回调
//     */
//    public void login(final String appId, final String secret, final RaiingRequestCallBack callback) {
//        long time = TimeUtils.getServerTime();
//        if (TextUtils.isEmpty(appId)) {
//            Log.e(RaiingConstant.MYSELF_TAG_ZJ.concat(TAG), "login: 传入的参数appid为空");
//            return;
//        }
//
//        if (TextUtils.isEmpty(secret)) {
//            Log.e(RaiingConstant.MYSELF_TAG_ZJ.concat(TAG), "login: 传入的参数secret为空");
//            return;
//        }
//
//        JSONObject params = new JSONObject();
//        try {
//            params.put(NetConstant.KEY_APPID, appId);
//            params.put(NetConstant.KEY_SECRET, secret);
//            RaiingRequest.raiingJSONObjectRequest(time, NetConstant.BASE_URL.concat(NetConstant.METHOD_LOGIN), params, new RaiingRequestCallBack() {
//                @Override
//                public void onStartRequest() {
//                    callback.onStartRequest();
//                }
//
//                @Override
//                public void onSuccessResponse(long time, JSONObject object) {
//                    if (object != null) {
//                        mAccessToken = getAccessToken(object);
//                        callback.onSuccessResponse(time, object);
//                    } else {
//                        //没有拿到登陆成功的token，理论上不可能存在该现象，当做接口请求失败处理
//                        //3个参数 在回调回去之后都没有用到，这里由于拿不到后面两个参数，随便设置一下
//                        RaiingLog.e("登陆成功，但没有拿到token");
//                        callback.onErrorResponse(time, -1, null);
//                    }
//
//                }
//
//                @Override
//                public void onErrorResponse(long time, int type, VolleyError error) {
//                    error.printStackTrace();
//                    callback.onErrorResponse(time, type, error);
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
