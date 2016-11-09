package xyz.chaisong.newsexp.root;

import java.util.List;

import xyz.chaisong.newsexp.base.BasePresenter;
import xyz.chaisong.newsexp.base.BaseView;
import xyz.chaisong.newsexp.root.model.CategoryEntity;

/**
 * Created by song on 16/10/29.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void config(List<CategoryEntity> response);
    }

    interface Presenter extends BasePresenter {

    }
}
