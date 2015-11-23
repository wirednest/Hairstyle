package com.wirednest.apps.hairstyle.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.ViewPhotoActivity;
import com.wirednest.apps.hairstyle.activity.AlbumPhotoActivity;
import com.wirednest.apps.hairstyle.db.Albums;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fragments.FullImageFragment;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.PersonViewHolder>{
    private Context context;
    List<Albums> albums;

    public AlbumAdapter(Context context,List<Albums> albums){
        this.context = context;
        this.albums = albums;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cv) CardView cv;
        @Bind(R.id.slider)
        SliderLayout sliderShow;
        @Bind(R.id.album_name) TextView albumName;

        PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cv.setPreventCornerOverlap(false);
            sliderShow.setPresetTransformer(SliderLayout.Transformer.Fade);

        }
    }
    @Override
    public int getItemCount() {
        return albums.size();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_album_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {
        personViewHolder.albumName.setText(albums.get(i).albumName);
        DefaultSliderView textSliderView = new DefaultSliderView(context);
        textSliderView
                .image(R.drawable.bob1)
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView baseSliderView) {
                        Intent intent = new Intent(context, ViewPhotoActivity.class);
                        intent.putExtra("ALBUMID", albums.get(i).getId());
                        context.startActivity(intent);
                        Log.d("Album", "Album Clicked " + albums.get(i).albumName);
                    }
                });

        personViewHolder.sliderShow.addSlider(textSliderView);
        //personViewHolder.albumImage.setImageResource(albums.get(i).image);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
