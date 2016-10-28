package xyz.chaisong.newsexp.feed;

import dagger.Component;
import xyz.chaisong.newsexp.util.FragmentScoped;

/**
 * Created by song on 16/10/27.
 */
@FragmentScoped
@Component(modules = {FeedLoaderModule.class, FeedBasePresenterModule.class})
public interface FeedBaseComponent {
    void inject(FeedBaseContainer container);
}
