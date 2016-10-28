package xyz.chaisong.newsexp.network.base;

import xyz.chaisong.lib.network.response.RespError;

/**
 * Created by song on 16/10/27.
 */

public interface IFeedCallback<T> {
    void onFeedLoadSuccess(T responseString, boolean isFirstPage, boolean hasMore, boolean isCache);

    void onFeedLoadFail(RespError failData);
}
