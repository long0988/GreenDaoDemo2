package com.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;

/**
 * Created by qlshi on 2018/9/14.
 */

public class GildeImageLoader implements ImageLoader{
    @Override
    public void displayImage(Activity activity, String imageUrl, ImageView imageView, int width, int height) {
        GlideUtils.loadGlideImage(activity,imageView,imageUrl,width,height);
    }

    @Override
    public void displayImagePreview(Activity activity, String imageUrl, ImageView imageView, int width, int height) {
        GlideUtils.loadGlideImage(activity,imageView,imageUrl,width,height);
    }

    @Override
    public void clearMemoryCache() {

    }
}
