package xyz.chaisong.lib.network;

import xyz.chaisong.lib.network.request.ReqEntity;
import xyz.chaisong.lib.network.response.RespBaseMeta;
import xyz.chaisong.lib.network.response.RespEntity;
import xyz.chaisong.lib.network.response.RespError;

/**
 * Created by song on 16/3/28.
 */
public interface RespPipe {
    <T extends RespBaseMeta> RespEntity<T> onSuccess(ReqEntity<T> netParams, RespEntity<T> data);
    <T extends RespBaseMeta> RespError onFail(ReqEntity<T> netParams, RespError failData);
}