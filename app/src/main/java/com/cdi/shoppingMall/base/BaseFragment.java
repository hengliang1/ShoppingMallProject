package com.cdi.shoppingMall.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cdi.shoppingMall.view.IView;

/**
 * baseFragment
 * Created by jun on 2016/11/30.
 */
public abstract class BaseFragment extends Fragment implements IView {

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
    protected abstract void handleRaiingMessage(Message msg);

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
        handleFindView(rootView);
        mPresenter = initPresenter();
        if (null != mPresenter) {
            mPresenter.baseHandle();
        }
        handleCreate();
        initOnclick();
        //初始化根据当前的网络状态显示异常与否
//        aboutException(NetWorkUtils.checkNetState(getActivity()));//若没网抛异常  全局网络监听可以在这儿做 譬如没网了 弹个隐藏布局
        return rootView;
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
     * 吐司便捷方法
     */
    public void toastToMessage(int resId) {
        if (isAdded()) {
            Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 吐司便捷方法
     */
    public void toastToMessage(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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

//    /**
//     * eventBus相关类
//     *
//     * @param event
//     */
//    public void onEventMainThread(BaseRaiingMessage event) {
//        if (event.mMsg == null) {
////            aboutException(event.mConnect);
//        }
//    }

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
            handleRaiingMessage(msg);
        }
    }
}
