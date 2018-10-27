package com.mjiayou.trecorelib.base;

import android.view.View;

/**
 * Created by xin on 18/9/25.
 */

public abstract class TCViewHolder<T> {

    protected abstract void findView(View view);

    protected abstract void initView(T bean, int position);
}
