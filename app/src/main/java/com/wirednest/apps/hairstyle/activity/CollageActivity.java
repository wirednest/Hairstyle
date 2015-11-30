package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wirednest.apps.hairstyle.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CollageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int collageLayoutId= getIntent().getIntExtra("menu", -1);
        setContentView(collageLayoutId);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.collageFrame);

        String pathImg1 = getIntent().getStringExtra("image1");
        String pathImg2 = getIntent().getStringExtra("image2");
        String pathImg3 = getIntent().getStringExtra("image3");

        ImageView image1 = (ImageView) findViewById(R.id.imageCollage1);
        ImageView image2 = (ImageView) findViewById(R.id.imageCollage2);
        ImageView image3 = (ImageView) findViewById(R.id.imageCollage3);

        image1.setImageURI(Uri.parse(pathImg1));
        image2.setImageURI(Uri.parse(pathImg2));
        image3.setImageURI(Uri.parse(pathImg3));

        findViewById(R.id.saveFrame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pictureFileDir = getDir();
                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

                    Log.d("Log", "Can't create directory to save image.");
                    return;
                }
                layout.setDrawingCacheEnabled(true);
                layout.buildDrawingCache(true);
                Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());
                layout.setDrawingCacheEnabled(false);

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

//                Intent intent = new Intent(CollageMenuActivity.this , )
            }
        });

    }

    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "Hairstyle");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collage, menu);
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
