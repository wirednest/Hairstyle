package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.rey.material.widget.Button;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.wirednest.apps.hairstyle.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PreviewCollageActivity extends AppCompatActivity {

    private SimpleFacebook mFacebook;

    @Bind(R.id.framePic)
    ImageView imageview;
    @Bind(R.id.shareFrame)
    Button shareFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_collage);
        ButterKnife.bind(this);
        mFacebook = SimpleFacebook.getInstance();
        final String image= getIntent().getStringExtra("image");
        imageview.setImageURI(Uri.parse(image));

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviewCollageActivity.this, FullScreenActivity.class);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });
        shareFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                Bitmap bitmap = BitmapFactory.decodeFile(image, options);
                Photo photo = new Photo.Builder()
                        .setImage(bitmap)
                        .build();
                OnPublishListener onPublishListener = new OnPublishListener() {
                    @Override
                    public void onComplete(String id) {
                        Log.i("Hairstyle_", "Published successfully. id = " + id);
                    }
                };
                SimpleFacebook.getInstance().publish(photo,true, onPublishListener);
                bitmap.recycle();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebook.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preview_collage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
