package com.mjiayou.trecorelib.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.mjiayou.trecorelib.image.impl.GlideImpl;

/**
 * Created by xin on 18/10/16.
 */

public abstract class ImageLoader {

    private static LoaderImpl mLoaderImpl;

    /**
     * set mImageLoader
     */
    public static void set(LoaderImpl loaderImpl) {
        mLoaderImpl = loaderImpl;
    }

    /**
     * get mImageLoader
     */
    public static LoaderImpl get() {
        if (mLoaderImpl == null) {
            mLoaderImpl = new GlideImpl();
        }
        return mLoaderImpl;
    }

    /**
     * LoaderImpl
     */
    public interface LoaderImpl {

        void load(ImageView imageView, String url);

        void load(ImageView imageView, String url, int resourceId);

        void load(ImageView imageView, String url, Drawable drawable);
    }
}
