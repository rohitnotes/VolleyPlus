package ir.siaray.volleyplus.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.*;
import ir.siaray.volleyplus.VolleyPlus;
import ir.siaray.volleyplus.util.VolleyUtils;
import java.util.Map;

/**
 * Created by SIARAY on 9/15/2017.
 */

public class StringRequest extends ir.siaray.volleyplus.request.Request {

    private String mUrl;
    private Context mContext;
    private int mMethod = Request.Method.GET;
    private String mTag;
    private Map<String, String> mHeader;
    private Request.Priority mPriority = Request.Priority.NORMAL;
    private int mTimeoutMs = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    private int mNumberOfRetries = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
    private float mBackoffMultiplier = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
    private Map<String, String> mParams;
    private Listener<String> mListener;
    private ErrorListener mErrorListener;
    private byte[] mBody;
    private boolean mShouldCache = true;

    private StringRequest(Context context, String url) {
        super(context);
        mContext = context;
        mUrl = url;
    }

    public static StringRequest getInstance(Context context, String url) {
        return (new StringRequest(context, url));
    }

    public StringRequest setMethod(int method) {
        mMethod = method;
        return this;
    }

    public StringRequest setTag(String tag) {
        mTag = tag;
        return this;
    }

    public StringRequest setHeader(Map<String, String> header) {
        mHeader = header;
        return this;
    }

    public StringRequest setPriority(Request.Priority priority) {
        mPriority = priority;
        return this;
    }

    public StringRequest setTimeout(int timeoutMs) {
        mTimeoutMs = timeoutMs;
        return this;
    }

    public StringRequest setNumberOfRetries(int numberOfRetries) {
        mNumberOfRetries = numberOfRetries;
        return this;
    }

    public StringRequest setBackoffMultiplier(float backoffMultiplier) {
        mBackoffMultiplier = backoffMultiplier;
        return this;
    }

    public StringRequest setParams(Map<String, String> params) {
        mParams = params;
        return this;
    }

    public StringRequest setListener(Listener<String> listener, ErrorListener errorListener) {
        mListener = listener;
        mErrorListener = errorListener;
        return this;
    }

    public StringRequest setBody(byte[] body) {
        this.mBody = body;
        return this;
    }

    public void send() {
        sendRequest();
    }

    private void sendRequest() {
        addParamsToGetRequest();
        com.android.volley.toolbox.StringRequest stringReq =
                new com.android.volley.toolbox.StringRequest(mMethod
                        , mUrl
                        , mListener
                        , mErrorListener) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //return mParams;
                        return (mParams != null) ? mParams : (super.getParams());
                    }

                    @Override
                    public Priority getPriority() {
                        return mPriority;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return (mHeader != null) ? mHeader : (super.getHeaders()/*new HashMap<String, String>()*/);
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        //return super.getBody();
                        return (mBody != null) ? mBody : (super.getBody());
                    }

                };
        stringReq.setShouldCache(mShouldCache);
        VolleyPlus.setTimeoutRequest(stringReq
                , mTimeoutMs
                , mNumberOfRetries
                , mBackoffMultiplier);
        VolleyPlus.getInstance(mContext).addToRequestQueue(stringReq
                , mTag);
    }

    private void addParamsToGetRequest() {
        if (mMethod == Request.Method.GET) {
            mUrl = VolleyUtils.buildGetRequestUrl(mUrl, mParams);
        }
    }

    public StringRequest setShouldCache(boolean shouldCache) {
        mShouldCache = shouldCache;
        return this;
    }
}
