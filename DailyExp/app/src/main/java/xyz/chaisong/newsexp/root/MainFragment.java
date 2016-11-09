package xyz.chaisong.newsexp.root;

import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.chaisong.lib.app.BaseFragment;
import xyz.chaisong.newsexp.R;

/**
 * Created by song on 16/10/29.
 */

public class MainFragment extends BaseFragment implements MainContract.View{

    private MainContract.Presenter mPresenter;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fg_viewpager;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public boolean isViewDestroyed() {
        return mViewPager == null || getActivity().isDestroyed();
    }
}
