package com.mjiayou.trecore.test;

import android.os.Bundle;
import android.widget.ImageView;

import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.image.ImageLoader;

/**
 * Created by xin on 18/10/16.
 */

public class ImageLoaderActivity extends TCActivity {

    private ImageView ivDemo1;
    private ImageView ivDemo2;
    private ImageView ivDemo3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_loader;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        ivDemo1 = (ImageView) findViewById(R.id.ivDemo1);
        ivDemo2 = (ImageView) findViewById(R.id.ivDemo2);
        ivDemo3 = (ImageView) findViewById(R.id.ivDemo3);

        String url1 = "http://pic.90sjimg.com/back_pic/u/00/38/54/05/561a435e8b507.jpg";
        String url2 = "https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/d1160924ab18972b48670bbee0cd7b899e510a60.jpg";
        String url3 = "http://img3.imgtn.bdimg.com/it/u=625751098,3819728741&fm=214&gp=0.jpg";

        ImageLoader.get().load(ivDemo1, url1);
        ImageLoader.get().load(ivDemo2, url2, R.drawable.tc_launcher);
        ImageLoader.get().load(ivDemo3, url3, getResources().getDrawable(R.drawable.tc_launcher));
    }
}
