package xyz.chaisong.newsexp.feed;


import dagger.Module;
import dagger.Provides;
import xyz.chaisong.newsexp.network.base.IFeedLoader;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link FeedLoader}.
 */
@Module
public class FeedLoaderModule {
    private FeedLoader mLoader;
    public FeedLoaderModule(FeedLoader loader) {
        mLoader = loader;
    }

    @Provides
    IFeedLoader<FeedBaseModel> provideFeedLoader() {
        return mLoader;
    }
}
