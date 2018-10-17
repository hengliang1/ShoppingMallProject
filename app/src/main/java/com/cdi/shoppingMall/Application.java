package com.cdi.shoppingMall;

import soundpooltest.psw.com.http_library.RaiingRequestQueue;

/**
 * Created by liang.heng on 2018/9/18.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //网络初始化
        RaiingRequestQueue.initialize(this);
    }
}
