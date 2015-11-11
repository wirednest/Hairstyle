package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.ViewPhotoActivity;
import com.wirednest.apps.hairstyle.adapter.AlbumAdapter;
import com.wirednest.apps.hairstyle.db.Categories;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Categories data = new Categories(3,"Emma Wilson", "23 years old", R.drawable.bob);
        //data.save();
        // specify an adapter (see also next example)
        mAdapter = new AlbumAdapter(Categories.listAll(Categories.class));
        //mAdapter = new AlbumAdapter(persons);
        mRecyclerView.setAdapter(mAdapter);
    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        findViewById(R.id.addAlbum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbumActivity.this,ViewPhotoActivity.class);
                startActivity(intent);
            }
        });
    }*/


}
