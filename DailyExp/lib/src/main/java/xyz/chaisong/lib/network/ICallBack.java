package xyz.chaisong.lib.network;

import xyz.chaisong.lib.network.request.ReqEntity;
import xyz.chaisong.lib.network.response.RespBaseMeta;
import xyz.chaisong.lib.network.response.RespEntity;
import xyz.chaisong.lib.network.response.RespError;

/**
 * 网络回调基类
 */
public interface ICallBack<T extends RespBaseMeta> {
    void onSuccess(ReqEntity<T> netParams, RespEntity<T> data);
    void onFail(ReqEntity<T> netParams, RespError failData);
}