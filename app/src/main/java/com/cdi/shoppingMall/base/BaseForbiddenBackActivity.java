package com.cdi.shoppingMall.base;

import android.view.KeyEvent;

import com.cdi.shoppingMall.view.IView;


/**
 * Activity基类
 * Created by jun on 2016/11/30.
 */
public abstract class BaseForbiddenBackActivity extends BaseActivity implements IView {

    private static final String TAG = "BaseForbiddenBackActivi";

    /**
     * 设置不可右键退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onPause();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
