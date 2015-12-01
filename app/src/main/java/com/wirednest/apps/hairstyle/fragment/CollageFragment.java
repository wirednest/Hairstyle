package com.wirednest.apps.hairstyle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.wirednest.apps.hairstyle.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollageFragment extends Fragment{
    @Bind(R.id.collage1)
    ImageButton collage1;
    @Bind(R.id.collage2)
    ImageButton collage2;
    @Bind(R.id.collage3)
    ImageButton collage3;
    @Bind(R.id.collage4)
    ImageButton collage4;
    @Bind(R.id.collage5)
    ImageButton collage5;
    @Bind(R.id.collage6)
    ImageButton collage6;
    private Context ctx;


    public CollageFragment(Context ctx) {
        this.ctx = ctx;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collage_menu, container, false);
        ButterKnife.bind(this, view);


        collage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                LayoutCollageFragment dsFragment = new LayoutCollageFragment(ctx);
                Bundle bundle = getActivity().getIntent().getExtras();
                bundle.putInt("collage", R.layout.collage1);
                dsFragment.setArguments(bundle);
                ft.replace(R.id.fragment_container_collage, dsFragment, "NewFragmentTag");
                ft.commit();
            }
        });

        collage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                LayoutCollageFragment dsFragment = new LayoutCollageFragment(ctx);
                Bundle bundle = getActivity().getIntent().getExtras();
                bundle.putInt("collage", R.layout.collage2);
                dsFragment.setArguments(bundle);
                ft.replace(R.id.fragment_container_collage, dsFragment, "NewFragmentTag");
                ft.commit();
            }
        });

        collage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                LayoutCollageFragment dsFragment = new LayoutCollageFragment(ctx);
                Bundle bundle = getActivity().getIntent().getExtras();
                bundle.putInt("collage", R.layout.collage3);
                dsFragment.setArguments(bundle);
                ft.replace(R.id.fragment_container_collage, dsFragment, "NewFragmentTag");
                ft.commit();
            }
        });

        collage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                LayoutCollageFragment dsFragment = new LayoutCollageFragment(ctx);
                Bundle bundle = getActivity().getIntent().getExtras();
                bundle.putInt("collage", R.layout.collage4);
                dsFragment.setArguments(bundle);
                ft.replace(R.id.fragment_container_collage, dsFragment, "NewFragmentTag");
                ft.commit();
            }
        });

        collage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                LayoutCollageFragment dsFragment = new LayoutCollageFragment(ctx);
                Bundle bundle = getActivity().getIntent().getExtras();
                bundle.putInt("collage", R.layout.collage5);
                dsFragment.setArguments(bundle);
                ft.replace(R.id.fragment_container_collage, dsFragment, "NewFragmentTag");
                ft.commit();
            }
        });

        collage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                LayoutCollageFragment dsFragment = new LayoutCollageFragment(ctx);
                Bundle bundle = getActivity().getIntent().getExtras();
                bundle.putInt("collage", R.layout.collage6);
                dsFragment.setArguments(bundle);
                ft.replace(R.id.fragment_container_collage, dsFragment, "NewFragmentTag");
                ft.commit();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
