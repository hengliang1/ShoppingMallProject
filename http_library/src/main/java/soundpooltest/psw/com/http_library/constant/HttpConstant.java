package soundpooltest.psw.com.http_library.constant;


public class HttpConstant {

    /**
     * app id
     */
    public static final String APP_ID = "THR2117649409";
    /**
     * secret
     */
    public static final String SECRET = "2OHEXcWp3OCOjq0wfOqgEaYvlIDtO9nRHBtI5n09BU28vAHUQTBKPp5u8IRdDIWE";


    /**
     * volley中所有的log输出都会有此tag
     */
    public static final String TAG_HTTP = "http-->>";

    /**
     * 该volley框架的版本号
     */
    public static final String VERSION = "1.0.0";

    /**
     * 数据请求列队,数据请求的默认tag
     */
    public static final String TAG_REQUEST_DEFAULT = "request_default";
    /**
     * 数据请求列队,周期性同步的tag
     */
    public static final String TAG_REQUEST_PERIOD = "request_period";
    /**
     * 用户的会话标识
     */
    public static final String ACCESS_TOKEN = "access_token";

    /**
     * 解析服务器响应码用key值
     */
    public static final String CODE = "code";
    /**
     * 接口返回正常
     */
    public static final int NORMAL_CODE = 0;

    /**
     * access token error.	access_token 错误
     * access_token expired.	access_token过期
     */
    public static final int CODE_ACCESS_TOKEN_ERROR = 60124003;
    public static final int CODE_ACCESS_TOKEN_EXPIRED = 60124004;
    /**
     * http身份认证的异常
     */
    public static final int EXCEPTION_AUTHFILURE = 1001;

    /**
     * Socket关闭，服务器宕机，DNS错误都会产生这个错误
     */
    public static final int EXCEPTION_NETWORK = 1002;

    /**
     * 客户端没有网络连接(获取不到statusCode时)
     */
    public static final int EXCEPTION_NOCONNECTION = 1003;

    /**
     * 对服务器返回值的解析异常
     */
    public static final int EXCEPTION_PARSE = 1004;

    /**
     * 服务器的响应的一个错误，只有5xx才会报此错误 HTTP状态代码。
     */
    public static final int EXCEPTION_SERVER = 1005;

    /**
     * Socket超时，服务器太忙或网络延迟会产生这个异常。
     */
    public static final int EXCEPTION_TIMEOUT = 1006;
    /**
     * 其它异常
     */
    public static final int EXCEPTION_OTHER = 1007;

}
