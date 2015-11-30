package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.wirednest.apps.hairstyle.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollageMenuActivity extends AppCompatActivity {

    @Bind(R.id.collage1)
    ImageButton collage1;
    @Bind(R.id.collage2)
    ImageButton collage2;
    @Bind(R.id.collage3)
    ImageButton collage3;
    @Bind(R.id.collage4)
    ImageButton collage4;
    @Bind(R.id.collage5)
    ImageButton collage5;
    @Bind(R.id.collage6)
    ImageButton collage6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_menu);
        ButterKnife.bind(this);

        final Intent intent = new Intent(CollageMenuActivity.this, CollageActivity.class);
        String image1 = getIntent().getStringExtra("image1");
        String image2 = getIntent().getStringExtra("image2");
        String image3 = getIntent().getStringExtra("image3");

        intent.putExtra("image1",image1);
        intent.putExtra("image2",image2);
        intent.putExtra("image3",image3);

        collage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("menu",R.layout.collage1);
                startActivity(intent);
            }
        });

        collage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("menu",R.layout.collage2);
                startActivity(intent);
            }
        });

        collage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("menu",R.layout.collage3);
                startActivity(intent);
            }
        });

        collage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("menu",R.layout.collage4);
                startActivity(intent);
            }
        });

        collage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("menu",R.layout.collage5);
                startActivity(intent);
            }
        });

        collage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("menu",R.layout.collage6);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collage_menu, menu);
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
