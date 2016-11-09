package xyz.chaisong.newsexp.root;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import xyz.chaisong.newsexp.root.model.CategoryEntity;

/**
 * Created by lt on 2015/12/14.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter{

    private Context context;
    private List<Fragment> mFragments;
    private List<CategoryEntity> mCategories;


    TabFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        mFragments = new ArrayList<>();
        mCategories = new ArrayList<>();
    }

    void config(List<Fragment> fragments, List<CategoryEntity> categoryEntities){
        this.mFragments = fragments;
        this.mCategories = categoryEntities;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategories.get(position).name;
    }
}
