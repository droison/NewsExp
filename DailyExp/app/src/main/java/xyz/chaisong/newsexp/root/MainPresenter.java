package xyz.chaisong.newsexp.root;

/**
 * Created by song on 16/10/29.
 */

public class MainPresenter implements MainContract.Presenter{

    private int mApiGenre;
    private MainContract.View mView;

    MainPresenter(int apiGenre, MainContract.View view) {
        this.mApiGenre = apiGenre;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
