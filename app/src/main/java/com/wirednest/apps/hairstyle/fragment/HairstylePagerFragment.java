package com.wirednest.apps.hairstyle.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.adapter.HairstylesAdapter;
import com.wirednest.apps.hairstyle.db.Categories;
import com.wirednest.apps.hairstyle.db.Hairstyles;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HairstylePagerFragment extends Fragment {
    public static final String ARG_PAGE = "CATEGORYID";
    private Categories category;

    @Bind(R.id.hairstyleGrid)
    GridView hairstyleGrid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hairstyle_pager, container, false);
        ButterKnife.bind(this, view);
        try {
            hairstyleGrid.setAdapter(
                    new HairstylesAdapter(
                            getContext(),
                            Hairstyles.find(Hairstyles.class, "CATEGORIES = ?", "" + category.getId())
                    )
            );
        }catch (Exception e){
            Log.e("Hairstyle_error",e.getMessage());
        }
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = Categories.findById(Categories.class,getArguments().getLong(ARG_PAGE));
    }
    public static HairstylePagerFragment  newInstance(long categoryId){

        HairstylePagerFragment fragment = new HairstylePagerFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PAGE, categoryId);
        fragment.setArguments(args);
        return fragment;
    }
}
