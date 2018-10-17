package soundpooltest.psw.com.http_library;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import soundpooltest.psw.com.http_library.constant.HttpConstant;


public class RaiingJSONObjectRequest extends JsonRequest<JSONObject> {

    private static final String TAG = "RaiingJSONObjectRequest";

    /**
     * Creates a new request.
     *
     * @param method        the HTTP method to use
     * @param url           URL to fetch the JSON from
     * @param requestBody   A {@link String} to post with the request. Null is allowed and
     *                      indicates no parameters will be posted along with request.
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     * @param tag           request 的tag
     */
    public RaiingJSONObjectRequest(int method, String url, JSONObject requestBody, String tag,
                                   Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody.toString(), listener,
                errorListener);
        RaiingRequestQueue.getInstance().addToRequestQueue(this, tag);
    }

    public RaiingJSONObjectRequest(int method, String url, JSONObject requestBody,
                                   Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        this(method, url, requestBody, HttpConstant.TAG_REQUEST_DEFAULT, listener, errorListener);
    }


    public RaiingJSONObjectRequest(String url, JSONObject requestBody,
                                   Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        this(Method.POST, url, requestBody, HttpConstant.TAG_REQUEST_DEFAULT, listener, errorListener);
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
