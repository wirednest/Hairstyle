package com.wirednest.apps.hairstyle.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.rey.material.widget.Button;
import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.db.Albums;
import com.wirednest.apps.hairstyle.db.Captures;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FormPicActivity extends AppCompatActivity {

    @Bind(R.id.savePicForm)
    Button savePic;
    @Bind(R.id.input_picName)
    EditText picName;
    @Bind(R.id.input_picDescription)
    EditText picDescription;
    @Bind(R.id.input_picPerson)
    EditText picPersonname;
    @Bind(R.id.input_picPassword)
    EditText picPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pic);
        ButterKnife.bind(this);
        final Albums album = new Albums();
        album.setId(1L);
        savePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Image1Name= getIntent().getStringExtra("Image1Name");
                String Image2Name= getIntent().getStringExtra("Image2Name");
                try{
                Captures captures = new Captures(
                        picName.getText().toString(),
                        Image1Name,
                        Image2Name,
                        picDescription.getText().toString(),
                        picPersonname.getText().toString(),
                        picPassword.getText().toString(),
                        album);
                captures.save();

                    Intent intent = new Intent(FormPicActivity.this, AlbumActivity.class);
                    startActivity(intent);

                Log.d("Hairstyle.DB", "New Captured 1 Saved");}
                catch (Exception error){
                    Log.d("Log", error.getMessage());
                }
            }
        });

    }

}
