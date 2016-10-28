package xyz.chaisong.newsexp.feed;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link FeedBasePresenter}.
 */
@Module
public class FeedBasePresenterModule {
    private final FeedBaseContract.View mView;

    public FeedBasePresenterModule(FeedBaseContract.View view) {
        mView = view;
    }

    @Provides
    FeedBaseContract.View provideFeedBaseContractView() {
        return mView;
    }
}
