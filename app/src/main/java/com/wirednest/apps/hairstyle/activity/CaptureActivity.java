package com.wirednest.apps.hairstyle.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.camera.EasyCameraView;

import net.bozho.easycamera.DefaultEasyCamera;
import net.bozho.easycamera.EasyCamera;
import net.bozho.easycamera.EasyCamera.*;

import java.io.IOException;


public class CaptureActivity extends AppCompatActivity {
    private static final String PREF = "Hairstyle";
    private EasyCamera mCamera = null;
    private EasyCameraView mCameraView = null;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    public void captureImage(View v) throws IOException{
        mCameraView.captureImage(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        preference = getSharedPreferences(PREF, Context.MODE_PRIVATE);

        mCamera = DefaultEasyCamera.open(preference.getInt("cameraId",0));
        if (mCamera != null) {
            mCameraView = new EasyCameraView(this, mCamera);//create a SurfaceView to show camera data

            FrameLayout camera_view = (FrameLayout) findViewById(R.id.camera_preview);

            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }
//
//        new CountDownTimer(5000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
//                timer.setText("done!");
//            }
//        }.start();

//        if (camera != null) {
//            mCameraView = new EasyCameraView(this, camera);//create a SurfaceView to show camera data
//
//            FrameLayout camera_view = (FrameLayout) findViewById(R.id.camera_view);
//            camera_view.addView(mCameraView);//add the SurfaceView to the layout
//        }



//        ImageButton capture = (ImageButton) findViewById(R.id.imgCapture);
//        capture.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mCameraView.captureImage();
//            }
//        });
    }

}
