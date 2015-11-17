package com.wirednest.apps.hairstyle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class PhotoEditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_edit);
        String filename = getIntent().getStringExtra("FILE_AVAGA");
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageURI(Uri.parse(filename));
        findViewById(R.id.chooseHair).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoEditActivity.this, HairStyleCategoriesActivity.class);
                startActivityForResult(intent, 14045);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 14045 && resultCode == RESULT_OK){
            int idGambiran = data.getIntExtra("ID_BUKOT",-1);
            if(idGambiran != -1) {
                ImageView imagine = (ImageView) findViewById(R.id.hair);
                imagine.setImageResource(idGambiran);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_edit, menu);
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
