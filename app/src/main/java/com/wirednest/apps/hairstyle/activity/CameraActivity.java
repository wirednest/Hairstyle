/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wirednest.apps.hairstyle.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.camera.CameraView;
import com.wirednest.apps.hairstyle.camera.PhotoHandler;

public class CameraActivity extends Activity {
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private Camera.PictureCallback mPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        try {
            mCamera = Camera.open();//you can use open(int) to use different cameras
        } catch (Exception e) {
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if (mCamera != null) {
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            FrameLayout camera_view = (FrameLayout) findViewById(R.id.camera_view);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }

        //btn to close the application
//        ImageButton imgClose = (ImageButton) findViewById(R.id.imgClose);
//        imgClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.exit(0);
//            }
//        });

        ImageButton capture = (ImageButton) findViewById(R.id.imgCapture);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCamera.takePicture(null, null,new PhotoHandler(getApplicationContext()));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.release();
        Log.d("CAMERA", "Destroy");
    }

}
