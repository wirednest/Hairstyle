package com.wirednest.apps.hairstyle;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.TabPageIndicator;
import com.rey.material.util.ViewUtil;
import com.wirednest.apps.hairstyle.adapter.HairStylePagerAdapter;
import com.wirednest.apps.hairstyle.db.Categories;
import com.wirednest.apps.hairstyle.util.CustomViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HairStyleCategoriesActivity extends AppCompatActivity {

    @Bind(R.id.tabPageCategory)
    TabPageIndicator tabCategory;
    @Bind(R.id.hairstyleViewPager)
    CustomViewPager hairstyleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_style_categories);
        ButterKnife.bind(this);

        hairstyleView.setAdapter(
                new HairStylePagerAdapter(
                        getSupportFragmentManager(),
                        this,
                        Categories.listAll(Categories.class)
                )
        );

        tabCategory.setViewPager(hairstyleView);




    }

}
