package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.support.annotation.Nullable;


import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.fragment.CollageFragment;
import com.wirednest.apps.hairstyle.fragment.LayoutCollageFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollageActivity extends FragmentActivity {

    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Bind(R.id.fragment_container_collage)
    FrameLayout fragmentContaineCollager;
    @Bind(R.id.saveFrame)
    Button saveFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        ButterKnife.bind(this);
        if(fragmentContaineCollager != null){
            if (savedInstanceState != null) {
                return;
            }
            LayoutCollageFragment dsFragment =  new LayoutCollageFragment(getBaseContext());
            dsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_collage, dsFragment).commit();
        }

        if(fragmentContainer != null){
            if (savedInstanceState != null) {
                return;
            }
            CollageFragment dsFragment =  new CollageFragment(getBaseContext());
            dsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, dsFragment).commit();
        }

        //Saving
        saveFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pictureFileDir = getDir();
                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

                    Log.d("Log", "Can't create directory to save image.");
                    return;
                }
                fragmentContaineCollager.setDrawingCacheEnabled(true);
                fragmentContaineCollager.buildDrawingCache(true);
                Bitmap bitmap = Bitmap.createBitmap(fragmentContaineCollager.getDrawingCache());
                fragmentContaineCollager.setDrawingCacheEnabled(false);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                String date = dateFormat.format(new Date());
                String photoFile = "Picture_" + date + ".jpg";

                String filename = pictureFileDir.getPath() + File.separator + photoFile;

                File pictureFile = new File(filename);

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                    fos.flush();
                    fos.close();
                    Log.d("Log", "New Image saved:" + filename);
                } catch (Exception error) {
                    Log.d("Log", "File" + filename + "not saved: "
                            + error.getMessage());
                }

                Intent intent = new Intent(CollageActivity.this , PreviewCollageActivity.class);
                intent.putExtra("image",filename);
                startActivity(intent);
            }
        });

    }
    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "Hairstyle");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
