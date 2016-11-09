package xyz.chaisong.newsexp.root;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.chaisong.lib.app.BaseActivity;
import xyz.chaisong.newsexp.R;
import xyz.chaisong.newsexp.feed.FeedBaseFragment;
import xyz.chaisong.newsexp.root.model.CategoryEntity;
import xyz.chaisong.newsexp.root.model.CategoryLoader;

public class MainActivity extends BaseActivity implements MainContract.View{

    MainContract.Presenter mPresenter;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tablayout)
    TabLayout mTablayout;

    TabFragmentAdapter mTabFragmentAdapter;

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        mTabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mTabFragmentAdapter);
        mTablayout.setupWithViewPager(mViewPager);
        mTablayout.setTabTextColors(getResources().getColor(R.color.dark_white), Color.WHITE);

        new MainPresenter(this, new CategoryLoader());
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mPresenter.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void config(List<CategoryEntity> response) {
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < response.size(); i++) {
            Fragment fragment = new FeedBaseFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text",response.get(i).name);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        mTabFragmentAdapter.config(fragments, response);
    }

    @Override
    public boolean isViewDestroyed() {
        return false;
    }

}
