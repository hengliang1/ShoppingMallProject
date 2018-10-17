package soundpooltest.psw.com.http_library;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 底层采用OKHttp进行通信
 * Created by jun on 2016/5/28.
 */
public class OkHttpStack extends HurlStack {

    private OkHttpClient mOkHttpClient;

    /**
     * Create a OkHttpStack with default OkHttpClient.
     */
    public OkHttpStack() {
        this(new OkHttpClient());
    }

    /**
     * Create a OkHttpStack with a custom OkHttpClient
     *
     * @param okHttpClient Custom OkHttpClient, NonNull
     */
    public OkHttpStack(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
        // 目前发现连接超时很容易触发，因此更改默认的超时间隔, 默认的10s,10s，10s
        // 连接的时间间隔为30s
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        // 写入数据的时间间隔20s
        mOkHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);
        // 读取数据的时间间隔20s
        mOkHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        OkUrlFactory okUrlFactory = new OkUrlFactory(mOkHttpClient);
        return okUrlFactory.open(url);
    }


}