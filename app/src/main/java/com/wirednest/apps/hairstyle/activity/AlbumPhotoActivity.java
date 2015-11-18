package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.adapter.AlbumPhotoAdapter;
import com.wirednest.apps.hairstyle.db.Captures;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AlbumPhotoActivity extends SwipeBackActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeBackLayout mSwipeBackLayout;

    private long albumId;

    @Bind(R.id.addAlbumPhoto)
    FloatingActionButton addAlbumPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_album_photo);
        ButterKnife.bind(this);

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        Intent intent = getIntent();
        albumId = intent.getLongExtra("ALBUMID",0);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AlbumPhotoAdapter(Captures.find(Captures.class, "ALBUM = ?", String.valueOf(albumId)));
        mRecyclerView.setAdapter(mAdapter);

        addAlbumPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbumPhotoActivity.this,CaptureActivity.class);
                startActivity(intent);
            }
        });
    }
}
