package com.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.greendaodemo2.R;

/**
 * Created by qlshi on 2018/9/14.
 */

public class GlideUtils {
    public static void loadGlideImage(Context context, ImageView imageView,String imageUrl,int width, int height){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        requestOptions.placeholder(R.mipmap.corner_mark_default);
        requestOptions.error(R.mipmap.ic_loading);
        requestOptions.override(width,height);
        Glide.with(context).load(imageUrl).apply(requestOptions).into(imageView);
    }
    public static void loadGlideImage(Context context, ImageView imageView,String imageUrl){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        requestOptions.placeholder(R.mipmap.corner_mark_default);
        requestOptions.error(R.mipmap.ic_loading);
        Glide.with(context).load(imageUrl).apply(requestOptions).into(imageView);
    }
}
