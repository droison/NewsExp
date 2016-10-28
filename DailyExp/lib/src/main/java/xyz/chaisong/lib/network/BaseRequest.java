package xyz.chaisong.lib.network;

import android.app.Fragment;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xyz.chaisong.lib.log.CLog;
import xyz.chaisong.lib.network.request.ReqPrepare;
import xyz.chaisong.lib.network.response.RespBaseMeta;
import xyz.chaisong.lib.network.response.RespEntity;
import xyz.chaisong.lib.util.JsonUtil;

/**
 * Created by song on 15/11/27.
 * 对volley的封装，和volley紧耦合
 */
public class BaseRequest<T extends RespBaseMeta> extends Request<RespEntity<T>> implements RequestCmd {

    private static final String BOUNDARY = "mumiao-20151127----------"; //数据分隔线
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    private ArrayList<ReqPrepare.FormImage> mListItem;
    private ReqPrepare mPrepare;
    private Class<T> responseClass;
    private Map<String, String> mParams;

    /**
     * Default charset for JSON request.
     */
    protected static final String PROTOCOL_CHARSET = "utf-8";

    private final Response.Listener<RespEntity<T>> mListener;

    public BaseRequest(int method,
                       String url,
                       Map<String, Object> params,
                       ReqPrepare prepare,
                       Response.Listener<RespEntity<T>> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mPrepare = prepare;
        if (params != null && params.size() > 0) {
            mParams = new HashMap<String, String>();
            for (String key : params.keySet()) {
                mParams.put(key, String.valueOf(params.get(key)));
            }
        }
        setShouldCache(method == Method.GET);//只有get有缓存
    }

    public void setResponseClass(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    @Override
    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        CLog.w("BaseRequest", "loadUrl:" + getUrl());
        if (mPrepare != null) {
            if (mListItem == null) {
                mListItem = new ArrayList<>();
            }
            mPrepare.formImageData(mListItem);
        } else {
            mListItem = null;
        }
        if (responseClass == null) {
            throw new RuntimeException("responseClass cannot null");
        }
        return super.setRequestQueue(requestQueue);
    }

    @Override
    protected Response<RespEntity<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            CLog.w("BaseRequest", getUrl() + "\nstateCode=" + response.statusCode + "; networkTimeMs=" + response.networkTimeMs);
            T result = JsonUtil.Json2ObjectThrowExecption(jsonString, responseClass);

            Response<RespEntity<T>> responseHasError = responseHasError(result);
            if (responseHasError != null) {
                return responseHasError;
            }

            RespEntity<T> responseEntity = new RespEntity<T>();
            responseEntity.setResponseMeta(result);
            responseEntity.setHttpResponseMeta(new RespEntity.HttpResponseMeta(response.statusCode, response.headers, response.notModified, response.networkTimeMs));
            responseEntity.setResponseString(jsonString);
            return Response.success(responseEntity, parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            Log.e("BaseRequest", "parseNetworkResponse", e);
            return Response.error(new ParseError(e));
        }
    }

    //template method
    protected Response<RespEntity<T>> responseHasError(T result){
        /**
         if (result.getMeta().getStatus() < 200 || result.getMeta().getStatus() >= 300) { //服务器返回了错误码
         if (result.getMeta().getStatus() == RespError.ErrorType.responseNeedLogin) {

         return Response.error(RespError.responseNeedLogin(result.getMeta().getMsg()));
         }
         return Response.error(RespError.responseNot200Error(result.getMeta().getMsg()));
         }
         */
        return null;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mListItem == null || mListItem.size() == 0) {
            return super.getBody();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        if (mParams != null) {
            for (String key : mParams.keySet()) {
                StringBuilder sb = new StringBuilder("--");
            /*第一行*/
                //`"--" + BOUNDARY + "\r\n"`
                sb.append(BOUNDARY);
                sb.append("\r\n");
            /*第二行*/
                //Content-Disposition: form-data; name="参数的名称"; filename="上传的文件名" + "\r\n"
                sb.append("Content-Disposition: form-data;");
                sb.append(" name=\"");
                sb.append(key);
                sb.append("\"");
                sb.append("\r\n");
                sb.append("\r\n");
                sb.append(mParams.get(key));
                sb.append("\r\n");
                try {
                    bos.write(sb.toString().getBytes(PROTOCOL_CHARSET));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        int N = mListItem.size();
        ReqPrepare.FormImage formImage;
        for (int i = 0; i < N; i++) {
            formImage = mListItem.get(i);
            StringBuilder sb = new StringBuilder("--");
            /*第一行*/
            //`"--" + BOUNDARY + "\r\n"`
            sb.append(BOUNDARY);
            sb.append("\r\n");
            /*第二行*/
            //Content-Disposition: form-data; name="参数的名称"; filename="上传的文件名" + "\r\n"
            sb.append("Content-Disposition: form-data;");
            sb.append(" name=\"");
            sb.append(formImage.getName());
            sb.append("\"");
            sb.append("; filename=\"");
            sb.append(formImage.getFileName());
            sb.append("\"");
            sb.append("\r\n");
            /*第三行*/
            //Content-Type: 文件的 mime 类型 + "\r\n"
            sb.append("Content-Type: ");
            sb.append(formImage.getMime());
            sb.append("\r\n");
            /*第四行*/
            //"\r\n"
            sb.append("\r\n");
            try {
                bos.write(sb.toString().getBytes(PROTOCOL_CHARSET));
                /*第五行*/
                //文件的二进制数据 + "\r\n"
                bos.write(formImage.getValue());
                bos.write("\r\n".getBytes(PROTOCOL_CHARSET));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*结尾行*/
        //`"--" + BOUNDARY + "--" + "\r\n"`
        String endLine = "--" + BOUNDARY + "--" + "\r\n";
        try {
            bos.write(endLine.getBytes(PROTOCOL_CHARSET));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> requestHeaders = new HashMap<>();
        if (!TextUtils.isEmpty(NetUtil.cookie)) {
            requestHeaders.put("Cookie", NetUtil.cookie);
        }
        requestHeaders.putAll(super.getHeaders());
        return requestHeaders;
    }

    @Override
    public String getBodyContentType() {
        if (mListItem != null && mListItem.size() > 0) {
            return MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY;
        }
        return super.getBodyContentType();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected void deliverResponse(RespEntity<T> response) {
        mListener.onResponse(response);
    }

    private Cache.Entry parseCacheHeaders(NetworkResponse response) {
        if (getMethod() != Method.GET) //非GET请求没有缓存
            return null;

        long now = System.currentTimeMillis();

        Map<String, String> headers = response.headers;

        long serverDate = 0;
        long lastModified = 0;
        long serverExpires = 0;
        long softExpire = 0;
        long finalExpire = 0;
        long maxAge = 0;
        long staleWhileRevalidate = 0;
        boolean hasCacheControl = false;
        boolean mustRevalidate = false;

        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        headerValue = headers.get("Cache-Control");
        if (headerValue != null) {
            String[] tokens = headerValue.split(",");
            for (int i = 0; i < tokens.length; i++) {
                String token = tokens[i].trim();
                if (token.equals("no-cache") || token.equals("no-store")) {
                    return null;
                } else if (token.startsWith("max-age=")) {
                    try {
                        maxAge = Long.parseLong(token.substring(8));
                    } catch (Exception e) {
                    }
                } else if (token.startsWith("stale-while-revalidate=")) {
                    try {
                        staleWhileRevalidate = Long.parseLong(token.substring(23));
                    } catch (Exception e) {
                    }
                } else if (token.equals("must-revalidate") || token.equals("proxy-revalidate")) {
                    mustRevalidate = true;
                }
            }
        }

        headerValue = headers.get("Expires");
        if (headerValue != null) {
            serverExpires = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        headerValue = headers.get("Last-Modified");
        if (headerValue != null) {
            lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        // 修改缓存时间，避免重复网络请求
        softExpire = now + 2*1000; //2秒钟的重复请求返回一个值
        finalExpire = now + 3*24*60*60*1000; //缓存保持3天

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = finalExpire;
        entry.serverDate = serverDate;
        entry.lastModified = lastModified;
        entry.responseHeaders = headers;

        return entry;
    }

    @Override
    public RequestCmd setRequestInvoker(Object invoker) {
        if (invoker != null) {
            Context context = null;
            if (invoker instanceof Context) {
                context = (Context) invoker;
            } else if (invoker instanceof View) {
                context = ((View) invoker).getContext();
            } else if (invoker instanceof Fragment) {
                context = ((Fragment) invoker).getActivity();
            } else if (invoker instanceof android.support.v4.app.Fragment) {
                context = ((android.support.v4.app.Fragment) invoker).getActivity();
            }
            if (context != null) {
                setTag(getTagFromContext(context));
            }
        }
        return this;
    }

    public static String getTagFromContext(Context context) {
        return context.getClass().getSimpleName() + context.hashCode();
    }
}
