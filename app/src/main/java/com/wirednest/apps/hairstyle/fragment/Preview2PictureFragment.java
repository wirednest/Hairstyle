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

public class  Preview2PictureFragment extends Fragment{

    private final int LAST_PICK_CODE = 21;

    @Bind(R.id.previewImage1)
    ImageView previewImage1;
    @Bind(R.id.previewImage2)
    ImageView previewImage2;
    @Bind(R.id.takeLastPic)
    Button takeLastPic;


    Context ctx;
    private long captureId;
    private Captures capture;
    final File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Hairstyle");

    @SuppressLint("ValidFragment")
    public Preview2PictureFragment(Context context) {
        super();
        ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview_2_image, container, false);
        ButterKnife.bind(this, view);
        captureId = getActivity().getIntent().getLongExtra("captureId", 0);
        capture = Captures.findById(Captures.class, captureId);
        Log.d("captureId", "" + captureId);
        Bitmap image1 = BitmapFactory.decodeFile(imageDir.getPath() + File.separator + capture.image1);
        final String image1path=imageDir.getPath() + File.separator + capture.image1;
        previewImage1.setImageBitmap(image1);

        Bitmap image2 = BitmapFactory.decodeFile(imageDir.getPath() + File.separator + capture.image2);
        final String image2path=imageDir.getPath() + File.separator + capture.image2;
        previewImage2.setImageBitmap(image2);

        previewImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image1 = new Intent(ctx, FullScreenActivity.class);
                image1.putExtra("image", image1path);
                startActivity(image1);
            }
        });

        previewImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image2 = new Intent(ctx, FullScreenActivity.class);
                image2.putExtra("image", image2path);
                startActivity(image2);
            }
        });
        takeLastPic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent takeLastPic = new Intent(ctx, CaptureActivity.class);
                takeLastPic.putExtra("takepicLast", true);
                startActivityForResult(takeLastPic, LAST_PICK_CODE);
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LAST_PICK_CODE){
            if(resultCode == Activity.RESULT_OK){

                capture.image3 = data.getStringExtra("lastPictImage");
                capture.save();

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Preview3PictureFragment dsFragment =  new Preview3PictureFragment(ctx);
                dsFragment.setArguments(getActivity().getIntent().getExtras());
                ft.replace(R.id.fragment_container, dsFragment, "NewFragmentTag");
                ft.commit();
            }
        }
    }
}
