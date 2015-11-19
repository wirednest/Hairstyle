package com.wirednest.apps.hairstyle.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wirednest.apps.hairstyle.HairStyleCategoriesActivity;
import com.wirednest.apps.hairstyle.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoEditActivity extends Activity implements View.OnTouchListener {
    // these matrices will be used to move and zoom image
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    // we can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;

    //image s
    private int idGambiran=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_edit);

        final String pathImage1 = getIntent().getStringExtra("FILE_AVAGA");
        final String Image1Name = getIntent().getStringExtra("Image1Name");
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageURI(Uri.parse(pathImage1));
        findViewById(R.id.chooseHair).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoEditActivity.this, HairStyleCategoriesActivity.class);
                startActivityForResult(intent, 14045);
            }
        });


        ImageView view = (ImageView) findViewById(R.id.hair);
        view.setOnTouchListener(this);

        findViewById(R.id.savePic2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pictureFileDir = getDir();
                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

                    Log.d("Log", "Can't create directory to save image.");
                    return;
                }

                String filepath = getIntent().getStringExtra("FILE_AVAGA");
                File image = new File(filepath);
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);

                //empty canvas
                Bitmap newImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(newImage);//create canvas
                canvas.drawBitmap(bitmap,new Rect(0,0,newImage.getWidth(),newImage.getHeight()),new Rect(0,0,newImage.getWidth(),newImage.getHeight()),new Paint(Paint.FILTER_BITMAP_FLAG));//gambar background

                //hairstyle overlay
                Drawable overlay = getResources().getDrawable(idGambiran);
                Bitmap ovrlyBitmap = ((BitmapDrawable) overlay).getBitmap();

                //hairstyle with matrix
                Bitmap matrixedOverlay = Bitmap.createBitmap(ovrlyBitmap,0,0,ovrlyBitmap.getWidth(), ovrlyBitmap.getHeight(),matrix,true);
                canvas.drawBitmap(matrixedOverlay,new Rect(0,0,matrixedOverlay.getWidth(),matrixedOverlay.getHeight()),new Rect(0,0,matrixedOverlay.getWidth(),matrixedOverlay.getHeight()),new Paint(Paint.FILTER_BITMAP_FLAG));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                String date = dateFormat.format(new Date());
                String photoFile = "Picture_" + date + ".jpg";

                String filename = pictureFileDir.getPath() + File.separator + photoFile;

                File pictureFile = new File(filename);

                Bitmap saveImage = loadBitmapFromView((FrameLayout)findViewById(R.id.saveImage));

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    saveImage.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                    fos.flush();
                    fos.close();
                    Log.d("Log", "New Image saved:" + filename);

                } catch (Exception error) {
                    Log.d("Log", "File" + filename + "not saved: "
                            + error.getMessage());
                }
                Intent intent = new Intent(PhotoEditActivity.this, FormPicActivity.class);
                intent.putExtra("Image1Name", Image1Name);
                intent.putExtra("Image2Name", photoFile);
                startActivity(intent);
            }
        });
    }
    public static Bitmap loadBitmapFromView(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(true));
        v.setDrawingCacheEnabled(false); // clear drawing cache
        return b;
    }
    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "Hairstyle");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 14045 && resultCode == RESULT_OK) {
             idGambiran = data.getIntExtra("ID_BUKOT", -1);

            if (idGambiran != -1) {
                ImageView imagine = (ImageView) findViewById(R.id.hair);
                Picasso.with(this).load(idGambiran).into(imagine);
                imagine.setImageDrawable(getResources().getDrawable(idGambiran));
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

    public boolean onTouch(View v, MotionEvent event) {
        // handle touch events here
        ImageView view = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    //pointer count means finger
                    if (lastEvent != null && event.getPointerCount() == 2) {
                        newRot = rotation(event);
                        float r = newRot - d;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        float tx = values[2];
                        float ty = values[5];
                        float sx = values[0];
                        float xc = (view.getWidth() / 2) * sx;
                        float yc = (view.getHeight() / 2) * sx;
                        matrix.postRotate(r, tx + xc, ty + yc);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix);
        return true;
    }

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Calculate the degree to be rotated by.
     *
     * @param event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}
