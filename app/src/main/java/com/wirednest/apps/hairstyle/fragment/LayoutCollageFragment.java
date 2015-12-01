package com.wirednest.apps.hairstyle.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.wirednest.apps.hairstyle.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LayoutCollageFragment extends Fragment {
    @Bind(R.id.imageCollage1)
    ImageView image1;
    @Bind(R.id.imageCollage2)
    ImageView image2;
    @Bind(R.id.imageCollage3)
    ImageView image3;
    private Context ctx;


    public LayoutCollageFragment(Context ctx) {
        this.ctx = ctx;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (this.getArguments().containsKey("collage")) {
            int collageLayoutId = this.getArguments().getInt("collage", -1);
             view = inflater.inflate(collageLayoutId, container, false);
        } else {
             view = inflater.inflate(R.layout.collage1, container, false);
        }

        ButterKnife.bind(this, view);

        String pathImg1 = getActivity().getIntent().getStringExtra("image1");
        String pathImg2 = getActivity().getIntent().getStringExtra("image2");
        String pathImg3 = getActivity().getIntent().getStringExtra("image3");

        image1.setImageURI(Uri.parse(pathImg1));
        image2.setImageURI(Uri.parse(pathImg2));
        image3.setImageURI(Uri.parse(pathImg3));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
