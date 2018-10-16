package com.mjiayou.trecorelib.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.mjiayou.trecorelib.image.impl.GlideImpl;

/**
 * Created by xin on 18/10/16.
 */

public abstract class ImageLoader {

    private static ImageLoader mImageLoader;

    /**
     * set mImageLoader
     */
    public static void set(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    /**
     * get mImageLoader
     */
    public static ImageLoader get() {
        if (mImageLoader == null) {
            mImageLoader = new GlideImpl();
        }
        return mImageLoader;
    }

    public abstract void load(ImageView imageView, String url);

    public abstract void load(ImageView imageView, String url, Drawable drawable);
}
