package xyz.chaisong.newsexp.feed;

import xyz.chaisong.newsexp.base.BasePresenter;
import xyz.chaisong.newsexp.base.BaseView;

/**
 * Created by song on 16/10/27.
 */

public interface FeedBaseContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
