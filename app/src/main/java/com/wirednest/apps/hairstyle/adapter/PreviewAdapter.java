package com.wirednest.apps.hairstyle.adapter;

/**
 * Created by User on 25/11/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wirednest.apps.hairstyle.R;

import java.util.ArrayList;

public class PreviewAdapter extends BaseAdapter {

    private ArrayList<PhotoFileEntity> mData = new ArrayList<>(0);
    private Context mContext;

    public PreviewAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<PhotoFileEntity> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.labelCoverFlow);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.imageCoverFlow);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.image.setImageResource(mData.get(position).imageResId);
        holder.text.setText(mData.get(position).titleResId);

        return rowView;
    }


    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }
}