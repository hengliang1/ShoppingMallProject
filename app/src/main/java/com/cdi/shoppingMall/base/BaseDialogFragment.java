package com.cdi.shoppingMall.base;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cdi.shoppingMall.view.IView;
/**
 * Created by jiao.zhu on 2016/12/14.
 * DialogFragment基类
 */
public abstract class BaseDialogFragment extends DialogFragment implements IView {
    /**
     * 当前fragment是否可见，若以后有和viewpager结合使用时可能会用到。
     */
    protected boolean isVisible;
    /**
     * 载体Activity
     */
    protected Activity mActivity;

    protected BasePresenter mPresenter;
    protected Handler mBasicHandler = new BasicHandler();
    private View rootView = null;

    /**
     * handler消息处理
     *
     * @param msg 消息
     */
    protected abstract void processMessage(Message msg);

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * fragment被设置为可见时调用
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 该方法在onVisible里面调用，主要解决ViewPager预加载问题，没有此场景可以不用处理。
     */
    protected abstract void lazyLoad();

    /**
     * fragment被设置为不可见时调用
     */
    protected void onInvisible() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    /**
     * 多个fragment  需要初始化一次的操作放到 onCreate 处理
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /***
     * 生命周期方法
     *
     * @param inflater           布局填充器
     * @param container          载体容器
     * @param savedInstanceState 数据缓存对象
     * @return
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        rootView = initContentView(inflater, container, savedInstanceState);
        mPresenter = initPresenter();
        handleFindView(rootView);
        handleCreate();
        if (mPresenter != null) {
            mPresenter.baseHandle();
        }

        setUnableClick();

        //初始化根据当前的网络状态显示异常与否
//        aboutException(NetWorkUtils.checkNetState(getActivity()));//若没网抛异常  全局网络监听可以在这儿做 譬如没网了 弹个隐藏布局
        return rootView;
    }

    /**
     * 初始化点击事件
     */
    private void setUnableClick() {
        initOnclick();
    }

    /**
     * 处理findViewById
     *
     * @param rootView 根布局
     */
    protected abstract void handleFindView(View rootView);

    /**
     * 初始化presenter
     *
     * @return 返回presenter
     */
    protected abstract BasePresenter initPresenter();

    /**
     * 初始化view
     *
     * @param inflater           布局填充器
     * @param container          载体容器
     * @param savedInstanceState 缓存数据对象
     * @return
     */
    public abstract View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 处理业务逻辑
     */
    protected abstract void handleCreate();

    /**
     * 初始化点击事件逻辑
     */
    protected abstract void initOnclick();

    /**
     * 点击事件
     *
     * @param v 传入的View对象
     */
    @Override
    public void onClick(View v) {
        customOnClick(v);
    }

    /**
     * 页面点击事件处理方法
     *
     * @param v
     */
    protected abstract void customOnClick(View v);

    /**
     * 右击返回的回调（用于处理一些需要监听右击的操作）
     */
    protected abstract void tapBackPressure();

    /**
     * 去掉标题栏
     */
    public void hideTitleBar() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    /**
     * 去某个activity便捷方法
     *
     * @param clazz
     * @param bundle
     */
    public void startActvity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 头部联网异常的显示
     *
     * @param connect
     */
    protected void aboutException(boolean connect) {
    }

    /**
     * 生命周期方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
        if (null != mPresenter) {
            mPresenter.clean();
        }
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

    /**
     * 监听物理返回键（右击）
     *
     * @param isBan 是否禁止右击返回
     */
    public void monitorBackPressure(final boolean isBan) {
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    tapBackPressure();
                    return isBan;
                }
                return false;
            }
        });
    }
}
