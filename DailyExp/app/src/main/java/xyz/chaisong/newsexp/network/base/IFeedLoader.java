package xyz.chaisong.newsexp.network.base;

/**
 * Created by song on 16/10/27.
 */

public interface IFeedLoader<T> {
    /**
     * 表示无脑往下翻，无缓存
     */
    void loadMore();

    /**
     * 表示是第一页，有缓存
     */
    void load();

    /**
     * 表示是第一页，无缓存
     */
    void loadWithNoCache();

    /**
     * 回调
     */
    void setCallBack(IFeedCallback<T> callBack);
}