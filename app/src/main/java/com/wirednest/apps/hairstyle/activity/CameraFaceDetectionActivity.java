package com.wirednest.apps.hairstyle.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wirednest.apps.hairstyle.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class CameraFaceDetectionActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    LayoutInflater controlInflater = null;

    Button buttonTakePicture;
    TextView prompt;

    DrawingView drawingView;
    Camera.Face[] detectedFaces;

    final int RESULT_SAVEIMAGE = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_face_detection);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.camerapreview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        drawingView = new DrawingView(this);
        ViewGroup.LayoutParams layoutParamsDrawing
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        this.addContentView(drawingView, layoutParamsDrawing);

        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.control_face_detection, null);
        ViewGroup.LayoutParams layoutParamsControl
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);

        buttonTakePicture = (Button)findViewById(R.id.takepicture);
        buttonTakePicture.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                camera.takePicture(myShutterCallback,
                        myPictureCallback_RAW, myPictureCallback_JPG);
            }});

        LinearLayout layoutBackground = (LinearLayout)findViewById(R.id.background);
        layoutBackground.setOnClickListener(new LinearLayout.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                buttonTakePicture.setEnabled(false);
                camera.autoFocus(myAutoFocusCallback);
            }});

        prompt = (TextView)findViewById(R.id.prompt);
    }

    Camera.FaceDetectionListener faceDetectionListener
            = new Camera.FaceDetectionListener(){

        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {

            if (faces.length == 0){
                prompt.setText(" No Face Detected! ");
                drawingView.setHaveFace(false);
            }else{
                prompt.setText(String.valueOf(faces.length) + " Face Detected :) ");
                drawingView.setHaveFace(true);
                detectedFaces = faces;
            }

            drawingView.invalidate();

        }};

    Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback(){

        @Override
        public void onAutoFocus(boolean arg0, Camera arg1) {
            // TODO Auto-generated method stub
            buttonTakePicture.setEnabled(true);
        }};

    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback(){

        @Override
        public void onShutter() {
            // TODO Auto-generated method stub

        }};

    Camera.PictureCallback myPictureCallback_RAW = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub

        }};

    Camera.PictureCallback myPictureCallback_JPG = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
			/*Bitmap bitmapPicture
				= BitmapFactory.decodeByteArray(arg0, 0, arg0.length);	*/

            Uri uriTarget = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());

            OutputStream imageFileOS;
            try {
                imageFileOS = getContentResolver().openOutputStream(uriTarget);
                imageFileOS.write(arg0);
                imageFileOS.flush();
                imageFileOS.close();

                prompt.setText("Image saved: " + uriTarget.toString());

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            camera.startPreview();
            camera.startFaceDetection();
        }};

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        if(previewing){
            camera.stopFaceDetection();
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null){
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();

                prompt.setText(String.valueOf(
                        "Max Face: " + camera.getParameters().getMaxNumDetectedFaces()));
                camera.startFaceDetection();
                previewing = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera = Camera.open(1);
        camera.setFaceDetectionListener(faceDetectionListener);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera.stopFaceDetection();
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }

    private class DrawingView extends View{

        boolean haveFace;
        Paint drawingPaint;

        public DrawingView(Context context) {
            super(context);
            haveFace = false;
            drawingPaint = new Paint();
            drawingPaint.setColor(Color.GREEN);
            drawingPaint.setStyle(Paint.Style.STROKE);
            drawingPaint.setStrokeWidth(2);
        }

        public void setHaveFace(boolean h){
            haveFace = h;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            if(haveFace){

                // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
                // UI coordinates range from (0, 0) to (width, height).

                int vWidth = getWidth();
                int vHeight = getHeight();

                for(int i=0; i<detectedFaces.length; i++){

                    int l = detectedFaces[i].rect.left;
                    int t = detectedFaces[i].rect.top;
                    int r = detectedFaces[i].rect.right;
                    int b = detectedFaces[i].rect.bottom;
                    int left	= (l+1000) * vWidth/2000;
                    int top		= (t+1000) * vHeight/2000;
                    int right	= (r+1000) * vWidth/2000;
                    int bottom	= (b+1000) * vHeight/2000;
                    canvas.drawRect(
                            left, top, right, bottom,
                            drawingPaint);
                }
            }else{
                canvas.drawColor(Color.TRANSPARENT);
            }
        }

    }
}
