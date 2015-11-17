package com.wirednest.apps.hairstyle.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.db.Captures;

import java.util.List;

public class AlbumPhotoAdapter extends RecyclerView.Adapter<AlbumPhotoAdapter.ViewHolder>{

    List<Captures> captures;

    public AlbumPhotoAdapter(List<Captures> captures){
        this.captures = captures;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView captureImage;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setPreventCornerOverlap(false);
            captureImage = (ImageView)itemView.findViewById(R.id.album_image);
        }
    }
    @Override
    public int getItemCount() {
        return captures.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_album_card, viewGroup, false);
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
        //personViewHolder.captureImage.setImageResource(captures.get(i).captureImage);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
