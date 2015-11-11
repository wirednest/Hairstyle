package com.wirednest.apps.hairstyle.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.db.Categories;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.PersonViewHolder>{

    List<Categories> categories;

    public AlbumAdapter(List<Categories> categories){
        this.categories = categories;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView personPhoto;
        TextView personName;
        TextView personAge;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
    @Override
    public int getItemCount() {
        return categories.size();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_album_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(categories.get(i).category_name);
        personViewHolder.personAge.setText(categories.get(i).description);
        personViewHolder.personPhoto.setImageResource(categories.get(i).image);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
