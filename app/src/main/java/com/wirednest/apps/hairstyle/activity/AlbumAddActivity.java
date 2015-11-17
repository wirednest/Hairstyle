package com.wirednest.apps.hairstyle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.ppamorim.dragger.DraggerActivity;
import com.github.ppamorim.dragger.DraggerPosition;
import com.github.ppamorim.dragger.DraggerView;
import com.wirednest.apps.hairstyle.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlbumAddActivity extends AppCompatActivity {
    @Bind(R.id.dragger_view)
    DraggerView draggerView;

    @Override public void onCreate(Bundle savedInstanceState) {
        //overridePendingTransition(0, 0);

        super.onCreate(savedInstanceState);

        //setFriction(8);
        setContentView(R.layout.activity_album_add);
        ButterKnife.bind(this);
        draggerView.setFriction(10);
        draggerView.setDraggerPosition(DraggerPosition.RIGHT);
    }
}
