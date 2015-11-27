package com.wirednest.apps.hairstyle.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rey.material.widget.Button;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.activity.CaptureActivity;
import com.wirednest.apps.hairstyle.activity.FullScreenActivity;
import com.wirednest.apps.hairstyle.db.Captures;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Preview3PictureFragment extends Fragment{

    private final int LAST_PICK_CODE = 21;

    @Bind(R.id.previewImage1)
    ImageView previewImage1;
    @Bind(R.id.previewImage2)
    ImageView previewImage2;
    @Bind(R.id.previewImage3)
    ImageView previewImage3;


    Context ctx;
    private long captureId;
    private Captures capture;
    final File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Hairstyle");

    @SuppressLint("ValidFragment")
    public Preview3PictureFragment(Context context) {
        super();
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview_3_image, container, false);
        ButterKnife.bind(this, view);
        captureId = getActivity().getIntent().getLongExtra("captureId", 0);
        capture = Captures.findById(Captures.class, captureId);
        Log.d("captureId", "" + captureId);
        Bitmap image1 = BitmapFactory.decodeFile(imageDir.getPath() + File.separator + capture.image1);
        previewImage1.setImageBitmap(image1);

        Bitmap image2 = BitmapFactory.decodeFile(imageDir.getPath() + File.separator + capture.image2);
        previewImage2.setImageBitmap(image2);

        Bitmap image3 = BitmapFactory.decodeFile(imageDir.getPath() + File.separator + capture.image3);
        previewImage3.setImageBitmap(image3);

        previewImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image1 = new Intent(ctx, FullScreenActivity.class);
                image1.putExtra("image", R.drawable.sample1);
                startActivity(image1);
            }
        });

        previewImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image2 = new Intent(ctx, FullScreenActivity.class);
                image2.putExtra("image", R.drawable.sample2);
                startActivity(image2);
            }
        });
        previewImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image3 = new Intent(ctx, FullScreenActivity.class);
                image3.putExtra("image", R.drawable.sample2);
                startActivity(image3);
            }
        });
        return view;
    }

}
