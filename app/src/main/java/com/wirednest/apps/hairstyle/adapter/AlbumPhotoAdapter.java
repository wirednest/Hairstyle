package com.wirednest.apps.hairstyle.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.activity.PreviewImageActivity;
import com.wirednest.apps.hairstyle.db.Captures;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlbumPhotoAdapter extends RecyclerView.Adapter<AlbumPhotoAdapter.ViewHolder>{
    private Activity context;
    List<Captures> captures;

    public AlbumPhotoAdapter(Activity context,List<Captures> captures){
        this.context = context;
        this.captures = captures;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cv) CardView cv;
        @Bind(R.id.slider)
        SliderLayout sliderShow;
        @Bind(R.id.photoName) TextView photoName;
        @Bind(R.id.photoPerson) TextView photoPerson;
        @Bind(R.id.buttonView) Button buttonView;
        @Bind(R.id.buttonShare) Button buttonShare;
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
    public void onBindViewHolder(final ViewHolder viewHolder,final int i) {

        final File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Hairstyle");

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Album", "Album Clicked " + captures.get(i).captureName);
            }
        });
        viewHolder.buttonView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent previewImage = new Intent(context, PreviewImageActivity.class);
                previewImage.putExtra("captureId", captures.get(i).getId());
                context.startActivity(previewImage);
            }
        });
        viewHolder.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Album", "Facebook" + captures.get(i).captureName);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(imageDir.getPath() + File.separator + captures.get(i).image2, options);
                Photo photo = new Photo.Builder()
                        .setImage(bitmap)
                        .setName("Screenshot from #android_simple_facebook sample application")
                        .setPlace("110619208966868")
                        .build();
                OnPublishListener onPublishListener = new OnPublishListener() {
                    @Override
                    public void onComplete(String id) {
                        Log.i("Hairstyle_", "Published successfully. id = " + id);
                    }
                };
                SimpleFacebook.getInstance().publish(photo,true, onPublishListener);

            }
        });


        DefaultSliderView image1 = new DefaultSliderView(context);
        image1
            .image(new File(imageDir.getPath() + File.separator + captures.get(i).image1))
            .setScaleType(BaseSliderView.ScaleType.CenterCrop);

        viewHolder.sliderShow.addSlider(image1);

        DefaultSliderView image2 = new DefaultSliderView(context);
        image2
            .image(new File(imageDir.getPath() + File.separator + captures.get(i).image2))
            .setScaleType(BaseSliderView.ScaleType.CenterCrop);

        viewHolder.sliderShow.addSlider(image2);

        if(captures.get(i).image3 != null && !captures.get(i).image3.isEmpty()) {
            DefaultSliderView image3 = new DefaultSliderView(context);
            image3
                .image(new File(imageDir.getPath() + File.separator + captures.get(i).image3))
                .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            viewHolder.sliderShow.addSlider(image3);
        }
        viewHolder.photoName.setText(captures.get(i).captureName);
        viewHolder.photoPerson.setText("by "+captures.get(i).person);
        //personViewHolder.captureImage.setImageResource(captures.get(i).captureImage);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
