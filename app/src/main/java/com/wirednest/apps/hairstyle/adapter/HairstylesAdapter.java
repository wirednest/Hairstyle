package com.wirednest.apps.hairstyle.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.wirednest.apps.hairstyle.HairStyleCategoriesActivity;
import com.wirednest.apps.hairstyle.db.Hairstyles;

import java.io.File;
import java.util.List;

public class HairstylesAdapter extends BaseAdapter {

    private Context context;
    private List<Hairstyles> hairstylesList;

    final File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Hairstyle");
    final File imageDir = new File(appDir,"hairstyles-data");
    public HairstylesAdapter(Context context,List<Hairstyles> hairstylesList) {
        this.context = context;
        this.hairstylesList = hairstylesList;
    }

    @Override
    public int getCount() {
        return hairstylesList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Bitmap image = BitmapFactory.decodeFile(imageDir.getPath() + File.separator + hairstylesList.get(position).image);
        imageView.setImageBitmap(image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("ID_BUKOT", hairstylesList.get(position).image);
                ((HairStyleCategoriesActivity) context).setResult(Activity.RESULT_OK, result);
                ((HairStyleCategoriesActivity) context).finish();
            }
        });
        return imageView;
    }


}
