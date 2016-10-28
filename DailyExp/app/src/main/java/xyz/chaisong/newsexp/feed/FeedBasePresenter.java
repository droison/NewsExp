package xyz.chaisong.newsexp.feed;

import javax.inject.Inject;

import xyz.chaisong.newsexp.network.base.IFeedLoader;

/**
 * Created by song on 16/10/27.
 */

public class FeedBasePresenter implements FeedBaseContract.Presenter{
    private final IFeedLoader<FeedBaseModel> mFeebBaseLoader;

    private final FeedBaseContract.View mFeebBaseView;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    FeedBasePresenter(IFeedLoader<FeedBaseModel> feedLoader, FeedBaseContract.View view) {
        mFeebBaseLoader = feedLoader;
        mFeebBaseView = view;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mFeebBaseView.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
