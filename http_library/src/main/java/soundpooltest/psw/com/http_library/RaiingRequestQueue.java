package soundpooltest.psw.com.http_library;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

import java.io.File;

import soundpooltest.psw.com.http_library.constant.HttpConstant;


public class RaiingRequestQueue {

    private static final String TAG = "RaiingRequestQueue";

    /**
     * Default on-disk cache directory.
     */
    private static final String DEFAULT_CACHE_DIR = "volley";

    /**
     * Number of network request dispatcher threads to start.
     */
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 20;

    /**
     * 1.单例模式
     * 2.当前设计的是一个app里面所有的数据请求都加入一个数据请求列队
     * 3.通过filter来取消部分或全部的数据请求
     * 4.在application中类中初始化
     */

    private static RaiingRequestQueue sRaiingRequestQueue;
    private static Context sContext;

    private RequestQueue mRequestQueue;


    private RaiingRequestQueue(Context context) {
        // mRequestQueue = Volley.newRequestQueue(context);
        // 底层采用OKHttp进行网络请求
//        mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack());
        //        mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack());
        mRequestQueue = newRequestQueue(context, new OkHttpStack());
    }

    /**
     * Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
     *
     * @param context A {@link Context} to use for creating the cache dir.
     * @param stack   An {@link HttpStack} to use for the network, or null for default.
     * @return A started {@link RequestQueue} instance.
     */
    private static RequestQueue newRequestQueue(Context context, HttpStack stack) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        if (stack == null) {
            stack = new HurlStack();
        }

        Network network = new BasicNetwork(stack);
        // 执行请求的线程个数,框架内默认为4, 调整为20,满足高频率的网络请求
        // 目前实测, 14min17s， 发送1542次请求
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network, DEFAULT_NETWORK_THREAD_POOL_SIZE);
        queue.start();

        return queue;
    }

    /**
     * 初始化
     *
     * @param context 全局的context，在application中进行初始化
     */
    public static void initialize(Context context) {
        sContext = context;
        // todo 调试，开启volley内部的log输出
        VolleyLog.DEBUG = false;
    }

    /**
     * 单例模式
     */
    public static RaiingRequestQueue getInstance() {
        if ((sContext == null) /*|| !(sContext instanceof Application)*/) {
            throw new IllegalArgumentException(TAG + "必须先调用initialize方法初始化为全局的Context对象");
        }
        if (sRaiingRequestQueue == null) {
            synchronized (RaiingRequestQueue.class) {
                if (sRaiingRequestQueue == null) {
                    sRaiingRequestQueue = new RaiingRequestQueue(sContext);
                }
            }
        }
        return sRaiingRequestQueue;
    }

    /**
     * 添加数据请求到列队中,使用默认自定义tag
     *
     * @param req 网络请求
     * @param <T> 请求类型
     * @param tag 请求的tag
     */
    <T> void addToRequestQueue(Request<T> req, String tag) {
        if (mRequestQueue == null) {
            //这里主要强调的是调用此方法前必须实例化该对象
            throw new NullPointerException(HttpConstant.TAG_HTTP + "加入列队是mRequestQueue不能为空");
        }
        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException(HttpConstant.TAG_HTTP + "加入列队是tag不能为空");
        }
        if (req == null) {
            throw new IllegalArgumentException(HttpConstant.TAG_HTTP + "加入列队是request不能为空");
        }

//        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        req.setTag(tag);
        mRequestQueue.add(req);
    }

//    /**
//     * 添加数据请求到列队中,使用默认tag
//     *
//     * @param req 网络请求
//     * @param <T> 请求类型
//     */
//    <T> void addToRequestQueue(Request<T> req) {
//        addToRequestQueue(req, HttpConstant.TAG_REQUEST_DEFAULT);
//    }

    /**
     * 通过tag取消列队中等待的请求
     *
     * @param tag 请求的tag标签
     */
    public void cancelPendingRequests(String tag) {
        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException(HttpConstant.TAG_HTTP + "取消请求时传入的tag为空");
        }
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        } else {
            VolleyLog.d(HttpConstant.TAG_HTTP + "mRequestQueue为空");
        }
    }
}
