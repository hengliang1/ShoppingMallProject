package com.cdi.shoppingMall.base;

/**
 * basePresenter
 * Created by jun on 2016/11/30.
 */
public abstract class BasePresenter {

    /**
     * 资源释放
     */
    public abstract void clean();

    /**
     * 基础处理
     */
    public abstract void baseHandle();
}
