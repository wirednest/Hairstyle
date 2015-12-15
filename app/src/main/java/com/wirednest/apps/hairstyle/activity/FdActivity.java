package com.wirednest.apps.hairstyle.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.wirednest.apps.hairstyle.R;


public class FdActivity extends AppCompatActivity implements CvCameraViewListener2{

	private static final String    TAG                 = "OCVSample::Activity";
	private static final Scalar    FACE_RECT_COLOR     = new Scalar(0, 255, 0, 255);
	public static final int        JAVA_DETECTOR       = 0;
	public static final int        NATIVE_DETECTOR     = 1;

	private MenuItem               mItemFace50;
	private MenuItem               mItemFace40;
	private MenuItem               mItemFace30;
	private MenuItem               mItemFace20;
	private MenuItem               mItemType;

	private Mat                    mRgba;
	private Mat                    mGray;
	private File                   mCascadeFile;
	private CascadeClassifier      mJavaDetector;
	private DetectionBasedTracker mNativeDetector;

	private int                    mDetectorType       = JAVA_DETECTOR;
	private String[]               mDetectorName;

	private float                  mRelativeFaceSize   = 0.2f;
	private int                    mAbsoluteFaceSize   = 0;

	private CameraBridgeViewBase   mOpenCvCameraView;

	private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
				case LoaderCallbackInterface.SUCCESS:
				{
					Log.i(TAG, "OpenCV loaded successfully");

					// Load native library after(!) OpenCV initialization
					System.loadLibrary("detection_based_tracker");

					try {
						// load cascade file from application resources
						InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
						File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
						mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
						FileOutputStream os = new FileOutputStream(mCascadeFile);

						byte[] buffer = new byte[4096];
						int bytesRead;
						while ((bytesRead = is.read(buffer)) != -1) {
							os.write(buffer, 0, bytesRead);
						}
						is.close();
						os.close();

						mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
						if (mJavaDetector.empty()) {
							Log.e(TAG, "Failed to load cascade classifier");
							mJavaDetector = null;
						} else
							Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

						mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(), 0);

						cascadeDir.delete();

					} catch (IOException e) {
						e.printStackTrace();
						Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
					}

					mOpenCvCameraView.enableView();
				} break;
				default:
				{
					super.onManagerConnected(status);
				} break;
			}
		}
	};

	public FdActivity() {
		mDetectorName = new String[2];
		mDetectorName[JAVA_DETECTOR] = "Java";
		mDetectorName[NATIVE_DETECTOR] = "Native (tracking)";

		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "called onCreate");
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.face_detect_surface_view);

		mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.fd_activity_surface_view);
//        mOpenCvCameraView.setCameraIndex(99);
		mOpenCvCameraView.setCvCameraViewListener(this);

        findViewById(R.id.imgCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pictureFileDir = getDir();
                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

                    Log.d("Log", "Can't create directory to save image.");
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                String date = dateFormat.format(new Date());
                String photoFile = "Picture_" + date + ".jpg";
                String filename = pictureFileDir.getPath() + File.separator + photoFile;
                File pictureFile = new File(filename);

                Bitmap bitmap = mOpenCvCameraView.takePic();

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


            }
        });
	}

    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "Hairstyle");
    }

	@Override
	public void onPause()
	{
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (!OpenCVLoader.initDebug()) {
			Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
			OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
		} else {
			Log.d(TAG, "OpenCV library found inside package. Using it!");
			mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		mOpenCvCameraView.disableView();
	}

	public void onCameraViewStarted(int width, int height) {
		mGray = new Mat();
		mRgba = new Mat();
	}

	public void onCameraViewStopped() {
		mGray.release();
		mRgba.release();
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

		mRgba = inputFrame.rgba();
		mGray = inputFrame.gray();

		if (mAbsoluteFaceSize == 0) {
			int height = mGray.rows();
			if (Math.round(height * mRelativeFaceSize) > 0) {
				mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
			}
			mNativeDetector.setMinFaceSize(mAbsoluteFaceSize);
		}

		MatOfRect faces = new MatOfRect();

		if (mDetectorType == JAVA_DETECTOR) {
			if (mJavaDetector != null)
				mJavaDetector.detectMultiScale(mGray, faces, 1.1, 2, 2, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
						new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
		}
		else if (mDetectorType == NATIVE_DETECTOR) {
			if (mNativeDetector != null)
				mNativeDetector.detect(mGray, faces);
		}
		else {
			Log.e(TAG, "Detection method is not selected!");
		}
        Drawable overlay = getResources().getDrawable(R.drawable.hairstyle_1);
        Bitmap ovrlyBitmap = ((BitmapDrawable) overlay).getBitmap();
        Mat avaga = new Mat();
        Utils.bitmapToMat(ovrlyBitmap,avaga);

		Rect[] facesArray = faces.toArray();
		for (int i = 0; i < facesArray.length; i++){
			Imgproc.rectangle(mRgba, facesArray[i].tl(), facesArray[i].br(), FACE_RECT_COLOR, 3);

            Canvas canvas = mOpenCvCameraView.getHolder().lockCanvas();
            canvas.drawBitmap(ovrlyBitmap,new android.graphics.Rect(0,0,ovrlyBitmap.getWidth(),ovrlyBitmap.getHeight()),new android.graphics.Rect(facesArray[i].x,facesArray[i].y,facesArray[i].width,facesArray[i].height),new Paint(0));
            mOpenCvCameraView.getHolder().unlockCanvasAndPost(canvas);
        }
		return mRgba;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG, "called onCreateOptionsMenu");
		mItemFace50 = menu.add("Face size 50%");
		mItemFace40 = menu.add("Face size 40%");
		mItemFace30 = menu.add("Face size 30%");
		mItemFace20 = menu.add("Face size 20%");
		mItemType   = menu.add(mDetectorName[mDetectorType]);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
		if (item == mItemFace50)
			setMinFaceSize(0.5f);
		else if (item == mItemFace40)
			setMinFaceSize(0.4f);
		else if (item == mItemFace30)
			setMinFaceSize(0.3f);
		else if (item == mItemFace20)
			setMinFaceSize(0.2f);
		else if (item == mItemType) {
			int tmpDetectorType = (mDetectorType + 1) % mDetectorName.length;
			item.setTitle(mDetectorName[tmpDetectorType]);
			setDetectorType(tmpDetectorType);
		}
		return true;
	}

	private void setMinFaceSize(float faceSize) {
		mRelativeFaceSize = faceSize;
		mAbsoluteFaceSize = 0;
	}

	private void setDetectorType(int type) {
		if (mDetectorType != type) {
			mDetectorType = type;

			if (type == NATIVE_DETECTOR) {
				Log.i(TAG, "Detection Based Tracker enabled");
				mNativeDetector.start();
			} else {
				Log.i(TAG, "Cascade detector enabled");
				mNativeDetector.stop();
			}
		}
	}

}
