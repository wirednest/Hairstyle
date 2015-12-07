package com.wirednest.apps.hairstyle.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.squareup.picasso.Picasso;
import com.wirednest.apps.hairstyle.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LayoutCollageFragment extends Fragment {
    @Bind(R.id.imageCollage1)
    GestureImageView image1;
    @Bind(R.id.imageCollage2)
    GestureImageView image2;
    @Bind(R.id.imageCollage3)
    GestureImageView image3;
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

        image1.getController().getSettings()
                .setRotationEnabled(true)
                .setFillViewport(true)
                .setFitMethod(Settings.Fit.OUTSIDE);
        image2.getController().getSettings()
                .setRotationEnabled(true)
                .setFillViewport(true)
                .setFitMethod(Settings.Fit.OUTSIDE);
        image3.getController().getSettings()
                .setRotationEnabled(true)
                .setFillViewport(true)
                .setFitMethod(Settings.Fit.OUTSIDE);

        String pathImg1 = getActivity().getIntent().getStringExtra("image1");
        String pathImg2 = getActivity().getIntent().getStringExtra("image2");
        String pathImg3 = getActivity().getIntent().getStringExtra("image3");

        Picasso.with(getActivity()).load(Uri.fromFile(new File(pathImg1))).into(image1);
        Picasso.with(getActivity()).load(Uri.fromFile(new File(pathImg2))).into(image2);
        Picasso.with(getActivity()).load(Uri.fromFile(new File(pathImg3))).into(image3);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife.unbind(this);
    }
}
