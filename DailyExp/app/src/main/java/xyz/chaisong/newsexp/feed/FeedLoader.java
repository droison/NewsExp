package xyz.chaisong.newsexp.feed;

import xyz.chaisong.newsexp.network.base.IFeedCallback;
import xyz.chaisong.newsexp.network.base.IFeedLoader;

/**
 * Created by song on 16/10/28.
 */
public class FeedLoader implements IFeedLoader<FeedBaseModel>{

    private FeedApi mApi;

    public FeedLoader(FeedApi api) {
        this.mApi = api;
    }

    @Override
    public void load() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void loadWithNoCache() {

    }

    @Override
    public void setCallBack(IFeedCallback<FeedBaseModel> callBack) {

    }
}
