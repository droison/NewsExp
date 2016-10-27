package xyz.chaisong.newsexp.root;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import xyz.chaisong.newsexp.R;
import xyz.chaisong.newsexp.base.BaseActivity;
import xyz.chaisong.newsexp.feed.FeedBaseFragment;

public class MainActivity extends BaseActivity {
    private String[] titles = new String[]{"推荐", "热点", "赣州", "社会","订阅", "娱乐", "科技", "汽车","体育", "财经", "美女"};

    @Override
    protected void initViews() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < titles.length; i++) {
            Fragment fragment = new FeedBaseFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text",titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        viewPager.setAdapter(new TabFragmentAdapter(fragments, titles, getSupportFragmentManager(), this));
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabTextColors(getResources().getColor(R.color.dark_white), Color.WHITE);
        viewPager.setCurrentItem(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
