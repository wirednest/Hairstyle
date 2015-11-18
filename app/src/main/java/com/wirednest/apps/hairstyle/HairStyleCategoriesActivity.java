package com.wirednest.apps.hairstyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HairStyleCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_style_categories);
        findViewById(R.id.hairstyle_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("ID_BUKOT", R.drawable.hairstyle_1);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        findViewById(R.id.hairstyle_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("ID_BUKOT", R.drawable.hairstyle_2);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        findViewById(R.id.hairstyle_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("ID_BUKOT", R.drawable.hairstyle_3);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        findViewById(R.id.hairstyle_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("ID_BUKOT", R.drawable.hairstyle_4);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        findViewById(R.id.hairstyle_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("ID_BUKOT", R.drawable.hairstyle_5);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        findViewById(R.id.hairstyle_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("ID_BUKOT", R.drawable.hairstyle_6);
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hair_stle_categories, menu);
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
