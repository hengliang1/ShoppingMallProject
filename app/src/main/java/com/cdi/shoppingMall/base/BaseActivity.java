package com.cdi.shoppingMall.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;

import com.cdi.shoppingMall.view.IView;

/**
 * Activity基类
 * Created by jun on 2016/11/30.
 */
public abstract class BaseActivity extends Activity implements IView {

    /**
     * 本类TAG标识
     */
    private static final String TAG = "BaseActivity";
    /**
     * presenter调度者
     */
    protected BasePresenter mPresenter;

    /**
     * 公用handler
     */
    protected Handler mBasicHandler = new BasicHandler();

    /**
     * 生命周期， 是否走了暂停方法
     */
    protected boolean isStop = false;


    /**
     * 页面点击事件处理方法
     *
     * @param v View视图
     */
    protected abstract void customOnClick(View v);

    /**
     * handler消息处理
     *
     * @param msg 消息内容
     */
    protected abstract void processMessage(Message msg);

    /**
     * 创建方法
     *
     * @param savedInstanceState 系统缓存
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();

        View rootView = initContentView();
        if (rootView != null) {
            setContentView(rootView);
        }
        initView();
        initOnClick();
        if (null != mPresenter) {
            mPresenter.baseHandle();//基础处理
        }
    }

//    /**
//     * 设置不可右键退出
//     *
//     * @param keyCode
//     * @param event
//     * @return
//     */
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            onPause();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    /**
     * 设置是否全屏，如果True则隐藏状态栏
     *
     * @param enable True则隐藏状态栏
     */
    private void setFullScreen(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 布局设置
     *
     * @return 布局View
     */
    protected abstract View initContentView();

    /**
     * 初始化presenter
     *
     * @return Presenter
     */
    public abstract BasePresenter initPresenter();

    /**
     * 初始化View
     */
    public abstract void initView();

    /***
     * 初始化点击事件
     */
    public abstract void initOnClick();

    /***
     * activity onDestroy ，主要做回收操作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.clean();
        }
    }

    @Override
    public void onClick(View v) {
        customOnClick(v);
    }

    /**
     * Handler封装
     */
    private class BasicHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /*子类处理 handle*/
            processMessage(msg);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isStop = false;
    }
}
