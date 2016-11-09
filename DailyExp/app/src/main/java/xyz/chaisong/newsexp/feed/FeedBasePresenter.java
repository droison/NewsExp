package xyz.chaisong.newsexp.feed;

import xyz.chaisong.newsexp.network.base.IFeedLoader;

/**
 * Created by song on 16/10/27.
 */

public class FeedBasePresenter implements FeedBaseContract.Presenter{
    private final IFeedLoader<FeedBaseModel> mFeebBaseLoader;

    private final FeedBaseContract.View mFeebBaseView;

    FeedBasePresenter(IFeedLoader<FeedBaseModel> feedLoader, FeedBaseContract.View view) {
        mFeebBaseLoader = feedLoader;
        mFeebBaseView = view;
        mFeebBaseView.setPresenter(this);

    }

    @Override
    public void start() {

    }
}
