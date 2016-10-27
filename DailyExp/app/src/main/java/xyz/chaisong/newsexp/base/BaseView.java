package xyz.chaisong.newsexp.base;

/**
 * Created by song on 16/10/27.
 */

public interface BaseView<T> {

    /**
     * set the presenter of mvp
     * @param presenter
     */
    void setPresenter(T presenter);
    boolean isViewDestroyed();
}
