package xyz.chaisong.lib.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.filedownload.FileBasedCache;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.io.File;
import java.util.Map;
import java.util.Set;

import xyz.chaisong.lib.disk.DiskFileUtils;
import xyz.chaisong.lib.log.CLog;
import xyz.chaisong.lib.network.request.ReqConfig;
import xyz.chaisong.lib.network.request.ReqEntity;
import xyz.chaisong.lib.network.request.ReqPrepare;
import xyz.chaisong.lib.network.response.RespBaseMeta;
import xyz.chaisong.lib.network.response.RespEntity;
import xyz.chaisong.lib.network.response.RespError;

/**
 * Created by song on 15/11/27.
 * 中间层，分割volley和上层逻辑
 */
public class NetUtil {
    private static NetUtil mNetUtil;

    public static String cookie;
    private Context mContext;
    private final String LOG_TAG = "NetUtil";
    private final int cacheSeconds = 60 * 60 * 24 * 30;

    private RespPipe respPipe;

    private RequestQueue mQueue;
    private RequestQueue mFileDownloadQueue;

    private NetUtil(Context context) {
        this.mContext = context;
        respPipe = new RespPipe() {
            @Override
            public <T extends RespBaseMeta> RespEntity<T> onSuccess(ReqEntity<T> netParams, RespEntity<T> data) {
                return data;
            }

            @Override
            public <T extends RespBaseMeta> RespError onFail(ReqEntity<T> netParams, RespError failData) {
                return failData;
            }
        };
    }

    public RequestQueue getRequestQueue(){
        if (mQueue == null) {
            Network network = new BasicNetwork(new HurlStack());
            mQueue = new RequestQueue(new DiskBasedCache(getRequestCacheDir()), network, 4);
            mQueue.start();
        }
        return mQueue;
    }

    public RequestQueue getFileDownloadQueue(){
        if (mFileDownloadQueue == null) {
            Network network = new BasicNetwork(new HurlStack());
            mFileDownloadQueue = new RequestQueue(new FileBasedCache(getFileCacheDir()), network, 3);
            mFileDownloadQueue.start();
        }
        return mFileDownloadQueue;
    }

    //app初始化后修复缓存目录不能正确生成的问题. 因为处于cache子目录下,过早mkdir,可能会被系统清掉
    public void fixCacheDir() {
        File fileDir = getRequestCacheDir();
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        fileDir = getFileCacheDir();
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    private File getRequestCacheDir(){
        File cacheDir = null;
        if (DiskFileUtils.hasSDCardMounted()) {
            cacheDir = DiskFileUtils.getExternalCacheDir(mContext);
        }
        if (cacheDir == null) {
            cacheDir = mContext.getCacheDir();
        }
        return new File(cacheDir, "request");
    }

    private File getFileCacheDir() {
        File cacheDir = null;
        if (DiskFileUtils.hasSDCardMounted()) {
            cacheDir = DiskFileUtils.getExternalCacheDir(mContext);
        }
        if (cacheDir == null) {
            cacheDir = mContext.getCacheDir();
        }
        return new File(cacheDir, "source");
    }

    public static void init(Context context) {
        if (mNetUtil == null) {
            mNetUtil = new NetUtil(context);
        }
    }

    public static NetUtil getInstance() {
        if (mNetUtil == null) {
            throw new RuntimeException("mNetUtil == null, you need use getInstance() after init(Context context)");
        }
        return mNetUtil;
    }

    public static NetUtil getInstance(Context context) { //仅仅用于兼容接口
        if (mNetUtil == null) {
            throw new RuntimeException("mNetUtil == null, you need use getInstance() after init(Context context)");
        }
        return mNetUtil;
    }

    public void setRespPipe(RespPipe respPipe) {
        this.respPipe = respPipe;
    }

    public <T extends RespBaseMeta> BaseRequest<T> get(final ReqEntity<T> requestEntity, final ICallBack<T> ICallBack) {
        if (null == requestEntity || TextUtils.isEmpty(requestEntity.getUrl())) {
            throw new RuntimeException("the request is broken, method=get");
        }

        final String requestUrl = convertGetUrl(requestEntity.getUrl(), requestEntity.getParams());
        BaseRequest<T> request = new BaseRequest<T>(Request.Method.GET, requestUrl, null, null, new Response.Listener<RespEntity<T>>() {
            @Override
            public void onResponse(RespEntity<T> response) {
                //先回包，再缓存。
                callBackServerResponse(ICallBack, requestEntity, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackFailResponse(ICallBack, requestEntity, RespError.convertError(error));
            }
        });
        request.setResponseClass(requestEntity.getResponseClass());

        if (requestEntity.getRequestConfig().getRequestCache() != ReqConfig.RequestCache.NotUseCache) {
            Cache.Entry entry = getRequestQueue().getCache().get(request.getCacheKey());
            if (entry != null) { //有缓存
                Response<RespEntity<T>> response = request.parseNetworkResponse(
                        new NetworkResponse(entry.data, entry.responseHeaders));

                if (!entry.isExpired()) { //未过3天的有效期
                    // Completely unexpired cache hit. Just deliver the response.
                    RespEntity<T> cacheResponse = response.result;
                    cacheResponse.setCache(true);
                    callBackCacheResponse(ICallBack, requestEntity, cacheResponse);
                }

                if (requestEntity.getRequestConfig().getRequestCache() == ReqConfig.RequestCache.UseCachePrimary) {
                    return request;
                }
            }
            if (requestEntity.getRequestConfig().getRequestCache() == ReqConfig.RequestCache.OnlyUseCache)
                return request;
        } else {
            request.setShouldCache(false);
        }

        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
        return request;
    }

    //私有方法，PUT DELETE POST走这里
    private <T extends RespBaseMeta> BaseRequest<T> request(int method, final ReqEntity<T> requestEntity, ReqPrepare prepare, final ICallBack<T> ICallBack) {
        if (null == requestEntity || TextUtils.isEmpty(requestEntity.getUrl())) {
            throw new RuntimeException("the request is broken, method=" + method);
        }

        BaseRequest<T> request = new BaseRequest<T>(method, requestEntity.getUrl(), requestEntity.getParams(), prepare, new Response.Listener<RespEntity<T>>() {
            @Override
            public void onResponse(RespEntity<T> response) {
                callBackServerResponse(ICallBack, requestEntity, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackFailResponse(ICallBack, requestEntity, RespError.convertError(error));
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 0, 0));
        request.setResponseClass(requestEntity.getResponseClass());
        getRequestQueue().add(request);
        return request;
    }

    public <T extends RespBaseMeta> BaseRequest<T> postFile(ReqEntity<T> requestEntity, ReqPrepare prepare, ICallBack<T> ICallBack) {
        return request(Request.Method.POST, requestEntity, prepare, ICallBack);
    }

    public <T extends RespBaseMeta> BaseRequest<T> post(ReqEntity<T> requestEntity, ICallBack<T> ICallBack) {
        return postFile(requestEntity, null, ICallBack);
    }

    public <T extends RespBaseMeta> BaseRequest<T> putFile(ReqEntity<T> requestEntity, ReqPrepare prepare, ICallBack<T> ICallBack) {
        return request(Request.Method.PUT, requestEntity, prepare, ICallBack);
    }

    public <T extends RespBaseMeta> BaseRequest<T> put(ReqEntity<T> requestEntity, ICallBack<T> ICallBack) {
        return putFile(requestEntity, null, ICallBack);
    }

    public <T extends RespBaseMeta> BaseRequest<T> delete(ReqEntity<T> requestEntity, ICallBack<T> ICallBack) {
        return request(Request.Method.DELETE, requestEntity, null, ICallBack);
    }

    //logout由于特殊性,cookie必须先传入
    public <T extends RespBaseMeta> void logout(ReqEntity<T> requestEntity, ICallBack<T> ICallBack) {
        BaseRequest<T> request = request(Request.Method.DELETE, requestEntity, null, ICallBack);
        if (request != null) {
            request.addHeader("Cookie", cookie);
        }
    }

    public void cancelRequest(Context context){
        if (context != null) {
            final String targetTag = BaseRequest.getTagFromContext(context);
            getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    Object tag = request.getTag();
                    return tag != null && targetTag.equals(tag);
                }
            });
        }
    }

    // TODO: 15/11/27 参数拼装
    private String convertGetUrl(@NonNull String url, Map<String, Object> params) {
        if (params == null || params.size() == 0)
            return url;

        StringBuilder sb = new StringBuilder(url);
        Set<String> keys = params.keySet();
        int i = 0;
        for (String key : keys) {
            sb.append(i > 0 ? "&" : "?");
            sb.append(key).append("=").append(params.get(key));
            i++;
        }

        return sb.toString();
    }

    private <T extends RespBaseMeta> void callBackServerResponse(ICallBack<T> callBack, ReqEntity<T> netParams, RespEntity<T> data) {
        RespEntity<T> respEntity = respPipe.onSuccess(netParams, data);
        if (callBack != null) {
            callBack.onSuccess(netParams, respEntity);
        }
    }

    //cache会在子线程返回
    private <T extends RespBaseMeta> void callBackCacheResponse(final ICallBack<T> callBack, final ReqEntity<T> netParams, final RespEntity<T> data) {
        DeliveryExecutor.getInstance().postMainThread(new Runnable() {
            @Override
            public void run() {
                RespEntity<T> respEntity = respPipe.onSuccess(netParams, data);
                if (callBack != null) {
                    callBack.onSuccess(netParams, respEntity);
                }
            }
        });
    }

    private <T extends RespBaseMeta> void callBackFailResponse(ICallBack<T> callBack, ReqEntity<T> netParams, RespError failData) {
        RespError respError = respPipe.onFail(netParams, failData);
        if (callBack != null) {
            callBack.onFail(netParams, respError);
        }
        CLog.d("NetUtil",""+netParams.toString()+"\r\r\r"+failData.toString());
    }
}