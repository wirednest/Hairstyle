package com.wirednest.apps.hairstyle.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.rey.material.widget.CheckBox;
import com.wirednest.apps.hairstyle.R;
import com.rey.material.widget.Button;
import com.wirednest.apps.hairstyle.db.Albums;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AlbumAddActivity extends SwipeBackActivity {

    private SwipeBackLayout mSwipeBackLayout;

    @Bind(R.id.saveAlbum)
    Button saveAlbum;
    @Bind(R.id.input_albumName)
    EditText albumName;
    @Bind(R.id.input_albumDescription)
    EditText albumDescription;
    @Bind(R.id.privateAlbum)
    CheckBox privateAlbum;

    @Override public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_album_add);
        ButterKnife.bind(this);

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        saveAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String albumType = Albums.PUBLIC_TYPE;
                Long tsLong = System.currentTimeMillis()/1000;
                if(privateAlbum.isChecked()) albumType = Albums.PRIVATE_TYPE;

                Albums newAlbum = new Albums(
                        albumName.getText().toString(),
                        albumDescription.getText().toString(),
                        tsLong,
                        albumType,
                        ""
                );

                newAlbum.save();

                Log.d("Hairstyle.DB", "New Album Saved");
            }
        });
    }
}
