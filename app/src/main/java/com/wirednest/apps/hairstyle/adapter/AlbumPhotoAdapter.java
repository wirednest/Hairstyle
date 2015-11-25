package com.wirednest.apps.hairstyle.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.ViewPhotoActivity;
import com.wirednest.apps.hairstyle.db.Captures;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlbumPhotoAdapter extends RecyclerView.Adapter<AlbumPhotoAdapter.ViewHolder>{
    private Context context;
    List<Captures> captures;

    public AlbumPhotoAdapter(Context context,List<Captures> captures){
        this.context = context;
        this.captures = captures;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cv) CardView cv;
        @Bind(R.id.slider)
        SliderLayout sliderShow;
        //@Bind(R.id.album_name) TextView albumName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cv.setPreventCornerOverlap(false);
            sliderShow.setPresetTransformer(SliderLayout.Transformer.Fade);
        }
    }
    @Override
    public int getItemCount() {
        return captures.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_card_album_photo, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(final ViewHolder personViewHolder,final int i) {
        personViewHolder.cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Album","Album Clicked "+captures.get(i).captureName);
            }
        });
        DefaultSliderView textSliderView = new DefaultSliderView(context);
        File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Hairstyle");
        textSliderView
                .image(new File(imageDir.getPath() + File.separator + captures.get(i).image2))
                .setScaleType(BaseSliderView.ScaleType.CenterCrop);

        personViewHolder.sliderShow.addSlider(textSliderView);
        //personViewHolder.captureImage.setImageResource(captures.get(i).captureImage);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
