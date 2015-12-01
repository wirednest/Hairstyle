package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.facebook.share.widget.ShareDialog;
import com.melnykov.fab.FloatingActionButton;
import com.sromku.simple.fb.SimpleFacebook;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.adapter.AlbumPhotoAdapter;
import com.wirednest.apps.hairstyle.db.Captures;
import com.wirednest.apps.hairstyle.util.HidingScrollListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AlbumPhotoActivity extends SwipeBackActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeBackLayout mSwipeBackLayout;
    public ShareDialog shareDialog;
    private long albumId;
    SimpleFacebook mSimpleFacebook;

    @Bind(R.id.addAlbumPhoto)
    FloatingActionButton addAlbumPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_album_photo);
        ButterKnife.bind(this);
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        Intent intent = getIntent();
        albumId = intent.getLongExtra("ALBUMID",0);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AlbumPhotoAdapter(this,Captures.find(Captures.class, "ALBUM = ?", String.valueOf(albumId)));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
        addAlbumPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbumPhotoActivity.this,CaptureActivity.class);
                startActivity(intent);
            }
        });
    }
    private void hideViews() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) addAlbumPhoto.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        addAlbumPhoto.animate().translationY(addAlbumPhoto.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        addAlbumPhoto.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
    }
}
