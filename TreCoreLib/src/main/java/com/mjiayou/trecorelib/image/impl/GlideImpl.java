package com.mjiayou.trecorelib.image.impl;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mjiayou.trecorelib.base.TCApp;
import com.mjiayou.trecorelib.image.ImageLoader;

/**
 * Created by xin on 18/10/16.
 */

public class GlideImpl implements ImageLoader.LoaderImpl {

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(TCApp.get())
                .load(url)
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, String url, int resourceId) {
        Glide.with(TCApp.get())
                .load(url)
                .placeholder(resourceId)
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, String url, Drawable drawable) {
        Glide.with(TCApp.get())
                .load(url)
                .placeholder(drawable)
                .into(imageView);
    }
}
