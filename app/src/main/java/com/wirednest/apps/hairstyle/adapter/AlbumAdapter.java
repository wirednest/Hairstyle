package com.wirednest.apps.hairstyle.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.db.Albums;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.PersonViewHolder>{

    List<Albums> albums;

    public AlbumAdapter(List<Albums> albums){
        this.albums = albums;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView albumImage;
        TextView albumName;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setPreventCornerOverlap(false);
            albumName = (TextView)itemView.findViewById(R.id.album_name);
            albumImage = (ImageView)itemView.findViewById(R.id.album_image);
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
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.albumName.setText(albums.get(i).albumName);
        //personViewHolder.albumImage.setImageResource(albums.get(i).image);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
