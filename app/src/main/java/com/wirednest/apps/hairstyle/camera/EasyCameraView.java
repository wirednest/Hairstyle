package com.wirednest.apps.hairstyle.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.wirednest.apps.hairstyle.R;
import com.wirednest.apps.hairstyle.activity.PhotoEditActivity;
import com.wirednest.apps.hairstyle.activity.PreviewImageActivity;

import net.bozho.easycamera.EasyCamera;
import net.bozho.easycamera.EasyCamera.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EasyCameraView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private EasyCamera mCamera;

    private static final String TAG = "CameraPreview";

    public List<Camera.Size> mSupportedPreviewSizes;
    private Camera.Size mPreviewSize;

    private EasyCamera.CameraActions actions;
    private PictureCallback callback;


    @SuppressWarnings("deprecation")
    public EasyCameraView(Context context, EasyCamera camera) {
        super(context);
        mCamera = camera;
        mCamera.setDisplayOrientation(90);

        mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        for (Camera.Size str : mSupportedPreviewSizes)
            Log.e(TAG, str.width + "/" + str.height);

        //get the holder and set this class as the callback, so we can get camera data here
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        callback = new PictureCallback() {
            public void onPictureTaken(byte[] data, EasyCamera.CameraActions actions) {
                Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
                File pictureFileDir = getDir();
                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

                    Log.d("Log", "Can't create directory to save image.");
                    return;
                }
                Bitmap cameraBitmap = BitmapFactory.decodeByteArray
                        (data, 0, data.length);

                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                Bitmap rotatedBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(), cameraBitmap.getHeight(), matrix, true);

                //set overlay
                int wid = rotatedBitmap.getWidth();
                int hgt = rotatedBitmap.getHeight();
                Bitmap newImage = Bitmap.createBitmap(wid, hgt, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(newImage);

                canvas.drawBitmap(rotatedBitmap, 0f, 0f, null);

                newImage = ThumbnailUtils.extractThumbnail(newImage, mPreviewSize.height , mPreviewSize.width);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                String date = dateFormat.format(new Date());
                String photoFile = "Picture_" + date + ".jpg";

                String filename = pictureFileDir.getPath() + File.separator + photoFile;

                File pictureFile = new File(filename);

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    newImage.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                    fos.write(data);
                    fos.close();
                    newImage.recycle();
                    Log.d("Log", "New Image saved:" + filename);
                } catch (Exception error) {
                    Log.d("Log", "File" + filename + "not saved: "
                            + error.getMessage());
                }

                Intent intent = ((Activity) getContext()).getIntent();
                if (intent.hasExtra("takepicLast")) {
                    Intent i = new Intent();
                    i.putExtra("lastPictImage", photoFile);
                    Log.d("lastPickImageFile", photoFile);
                    ((Activity) getContext()).setResult(Activity.RESULT_OK, i);
                    ((Activity) getContext()).finish();
                } else {
                    Intent i = new Intent();
                    i.putExtra("FILE_AVAGA", filename);
                    i.putExtra("Image1Name", photoFile);
                    ((Activity) getContext()).setResult(Activity.RESULT_OK, i);
                    ((Activity) getContext()).finish();
                }
            }
        };

    }
    public Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }
    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "Hairstyle");
    }

    public void captureImage(View v) throws IOException {
//        new CountDownTimer(10000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                TextView txtView = (TextView) ((Activity)getContext()).findViewById(R.id.cooldown);
//                txtView.setText(""+millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
        actions.takePicture(EasyCamera.Callbacks.create().withJpegCallback(callback).withRestartPreviewAfterCallbacks(true));
//        TakePictureTask takePictureTask = new TakePictureTask();
//        takePictureTask.execute();
//            }
//        }.start();
    }

    private class TakePictureTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void result) {
            // This returns the preview back to the live camera feed
            try {
                //when the surface is created, we can set the camera to draw images in this surfaceholder
                actions = mCamera.startPreview(mHolder);
            } catch (IOException e) {
                Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            actions.takePicture(EasyCamera.Callbacks.create().withJpegCallback(callback).withRestartPreviewAfterCallbacks(true));

            // Sleep for however long, you could store this in a variable and
            // have it updated by a menu item which the user selects.
            try {
                Thread.sleep(3000); // 3 second preview
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            //when the surface is created, we can set the camera to draw images in this surfaceholder
            actions = mCamera.startPreview(mHolder);
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        Log.e("ok", "surfaceChanged => w=" + i2 + ", h=" + i3);
        //before changing the application orientation, you need to stop the preview, rotate and then start it again
        if (surfaceHolder.getSurface() == null)//check if the surface is ready to receive camera data
            return;

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            //this will happen when you are trying the camera if it's not running
        }

        //now, recreate the camera preview
        try {

            Camera.Parameters parameters = mCamera.getParameters();
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            mCamera.setParameters(parameters);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview(mHolder);
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //our app has only one screen, so we'll destroy the camera in the surface
        //if you are unsing with more screens, please move this code your activity
        mCamera.stopPreview();
        mCamera.close();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }

        float ratio;
        if (mPreviewSize.height >= mPreviewSize.width)
            ratio = (float) mPreviewSize.height / (float) mPreviewSize.width;
        else
            ratio = (float) mPreviewSize.width / (float) mPreviewSize.height;

        // One of these methods should be used, second method squishes preview slightly
        setMeasuredDimension(width, (int) (width * ratio));
//        setMeasuredDimension((int) (width * ratio), height);
        float camHeight = (int) (width * ratio);
        float newCamHeight;
        float newHeightRatio;

        if (camHeight < height) {
            newHeightRatio = (float) height / (float) mPreviewSize.height;
            newCamHeight = (newHeightRatio * camHeight);
            Log.e(TAG, camHeight + " " + height + " " + mPreviewSize.height + " " + newHeightRatio + " " + newCamHeight);
            setMeasuredDimension((int) (width * newHeightRatio), (int) newCamHeight);
            Log.e(TAG, mPreviewSize.width + " | " + mPreviewSize.height + " | ratio - " + ratio + " | H_ratio - " + newHeightRatio + " | A_width - " + (width * newHeightRatio) + " | A_height - " + newCamHeight);
        } else {
            newCamHeight = camHeight;
            setMeasuredDimension(width, (int) newCamHeight);
            Log.e(TAG, mPreviewSize.width + " | " + mPreviewSize.height + " | ratio - " + ratio + " | A_width - " + (width) + " | A_height - " + newCamHeight);
        }
    }

    public Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.height / size.width;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;

            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        return optimalSize;
    }
}
