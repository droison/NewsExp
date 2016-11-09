package xyz.chaisong.newsexp.root;

import android.support.annotation.NonNull;

import java.util.List;

import xyz.chaisong.lib.network.response.RespError;
import xyz.chaisong.newsexp.root.model.CategoryEntity;
import xyz.chaisong.newsexp.root.model.CategoryLoader;

/**
 * Created by song on 16/10/29.
 */

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mView;
    private CategoryLoader mCategoryLoader;

    MainPresenter(MainContract.View view, CategoryLoader categoryLoader) {
        this.mCategoryLoader = categoryLoader;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mCategoryLoader.load(new CategoryLoader.CallBack() {
            @Override
            public void onGetSuccess(@NonNull List<CategoryEntity> response) {
                mView.config(response);
            }

            @Override
            public void onGetFail(RespError failData) {

            }
        });
    }
}
