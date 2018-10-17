package com.cdi.shoppingMall.net;

/**
 * Created by jiao.zhu on 2016/12/1.
 * server网络接口字符串常量集中管理类
 * 前缀说明：
 * -METHOD开头的是接口名
 * -KEY开头的是接口输入参数的字段名
 * -无前缀的均是网络请求相关常量
 */

public class NetConstant {

    /**
     * 服务器地址
     */
    public static String BASE_URL = "http://192.168.1.208:8002/";

    /*************************
     ***P A R A M E T E R ****
     ************************/
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_APPID = "appid";
    public static final String KEY_SECRET = "secret";
    public static final String KEY_REQUEST = "request";

    /**
     * app id
     */
    public static final String APP_ID = "THR2117649409";

    /**
     * secret
     */
    public static final String SECRET = "2OHEXcWp3OCOjq0wfOqgEaYvlIDtO9nRHBtI5n09BU28vAHUQTBKPp5u8IRdDIWE";
}
