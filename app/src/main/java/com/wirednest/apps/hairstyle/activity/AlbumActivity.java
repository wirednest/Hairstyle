package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.ViewPhotoActivity;

public class AlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album);
        findViewById(R.id.addAlbum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbumActivity.this,ViewPhotoActivity.class);
                startActivity(intent);
            }
        });
    }


}
