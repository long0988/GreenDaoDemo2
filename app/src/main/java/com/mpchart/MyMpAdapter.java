package com.mpchart;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greendaodemo2.R;

import java.util.List;

/**
 * Created by qlshi on 2018/10/12.
 */

public class MyMpAdapter extends BaseAdapter {
    private Typeface mTypeFaceRegular, mTypeFaceLight;
    private List<ContentItem> mObjects;
    private Context mContext;

    public MyMpAdapter(Context context, List<ContentItem> objects) {
        this.mContext = context;
        this.mObjects = objects;
        mTypeFaceLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
        mTypeFaceRegular = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getCount() {
        return mObjects.size() > 0 ? mObjects.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvDesc = convertView.findViewById(R.id.tvDesc);
            holder.tvNew = convertView.findViewById(R.id.tvNew);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ContentItem contentItem = mObjects.get(position);
        holder.tvNew.setTypeface(mTypeFaceRegular);
        holder.tvName.setTypeface(mTypeFaceLight);
        holder.tvDesc.setTypeface(mTypeFaceLight);

        holder.tvName.setText(contentItem.name);
        holder.tvDesc.setText(contentItem.desc);

        if (contentItem.isNew)
            holder.tvNew.setVisibility(View.VISIBLE);
        else
            holder.tvNew.setVisibility(View.GONE);

        return convertView;
    }

    private class ViewHolder {
        TextView tvName, tvDesc;
        TextView tvNew;
    }
}
