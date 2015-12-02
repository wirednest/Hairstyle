package com.wirednest.apps.hairstyle.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.wirednest.apps.hairstyle.R;

import java.io.File;

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
        final int windowwidth = view.getWidth();
        final int windowheight = view.getHeight();

        String pathImg1 = getActivity().getIntent().getStringExtra("image1");
        String pathImg2 = getActivity().getIntent().getStringExtra("image2");
        String pathImg3 = getActivity().getIntent().getStringExtra("image3");

        Picasso.with(getActivity()).load(Uri.fromFile(new File(pathImg1))).into(image1);
        Picasso.with(getActivity()).load(Uri.fromFile(new File(pathImg2))).into(image2);
        Picasso.with(getActivity()).load(Uri.fromFile(new File(pathImg3))).into(image3);

//        image1.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v
//                        .getLayoutParams();
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        int x_cord = (int) event.getRawX();
//                        int y_cord = (int) event.getRawY();
//
//                        if (x_cord > windowwidth) {
//                            x_cord = windowwidth;
//                        }
//                        if (y_cord > windowheight) {
//                            y_cord = windowheight;
//                        }
//
//                        layoutParams.leftMargin = x_cord - 25;
//                        layoutParams.topMargin = y_cord - 75;
//
//                        v.setLayoutParams(layoutParams);
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
//        image2.setImageURI(Uri.parse(pathImg2));
//        image1.setImageURI(Uri.parse(pathImg1));
//        image3.setImageURI(Uri.parse(pathImg3));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife.unbind(this);
    }
}
