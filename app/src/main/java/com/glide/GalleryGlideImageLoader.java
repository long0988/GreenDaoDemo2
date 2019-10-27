package com.glide;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.greendaodemo2.R;

import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by qlshi on 2018/9/18.
 */

public class GalleryGlideImageLoader implements ImageLoader{
    @Override
    public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
        //GlideUtils.loadGlideImage(activity,imageView,path,width,height);
        RequestOptions requestOptions = new RequestOptions();
        //requestOptions.centerCrop();
        requestOptions.placeholder(R.mipmap.corner_mark_default);
        requestOptions.error(R.mipmap.ic_loading);
        requestOptions.override(width,height);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);
        Glide.with(activity)
                .load(path)
                .apply(requestOptions)
                .into(new ImageViewTarget<Drawable>(imageView) {

//                    @Override
//                    protected void setResource(@Nullable GlideDrawable resource) {
//                        imageView.setImageDrawable(resource);
//                    }

                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void setRequest(Request request) {
                        imageView.setTag(R.id.adapter_item_tag_key,request);
                    }

                    @Override
                    public Request getRequest() {
                        return (Request) imageView.getTag(R.id.adapter_item_tag_key);
                    }
                });
    }

    @Override
    public void clearMemoryCache() {

    }
}
