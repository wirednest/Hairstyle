package com.wirednest.apps.hairstyle.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wirednest.apps.hairstyle.db.Categories;
import com.wirednest.apps.hairstyle.fragment.HairstylePagerFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HairStylePagerAdapter extends FragmentPagerAdapter {

    private Context context;
    Fragment[] mFragments;
    List<Categories> mTabs;

    public HairStylePagerAdapter(FragmentManager fm, Context context, List<Categories> tabs) {
        super(fm);
        this.context = context;
        mTabs = tabs;
        mFragments = new Fragment[tabs.size()];

    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        return HairstylePagerFragment.newInstance(mTabs.get(position).getId());
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return mTabs.get(position).categoryName;
    }
}
