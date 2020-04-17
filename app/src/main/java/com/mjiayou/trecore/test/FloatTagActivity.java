package com.mjiayou.trecore.test;

import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjiayou.trecore.R;
import com.mjiayou.trecore.bean.FloatTagBean;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.util.DipUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * FloatTagActivity
 */
public class FloatTagActivity extends TCActivity {

    private final String LONG_TEXT = "测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字测试文字";

    @BindView(R.id.rl_container)
    RelativeLayout mRlContainer;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.btn_add_new_tag)
    Button mBtnAddNewTag;
    @BindView(R.id.btn_remove_last_tag)
    Button mBtnRemoveLastTag;
    @BindView(R.id.btn_remove_all_tag)
    Button mBtnRemoveAllTag;

    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.tv_line_count)
    TextView mTvLineCount;
    @BindView(R.id.btn_reset_text)
    TextView mBtnResetText;

    private int mImageWidth;
    private int mImageHeight;
    private FloatTagBean mFloatTagBean;
    private int mIndex = 0;

    @OnClick({R.id.btn_add_new_tag, R.id.btn_remove_last_tag, R.id.btn_remove_all_tag})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_new_tag:
                addNewTag();
                break;
            case R.id.btn_remove_last_tag:
                removeLastTag();
                break;
            case R.id.btn_remove_all_tag:
                removeAllTag();
                break;
        }
    }

    @OnClick({R.id.btn_reset_text})
    void onClick2(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_text:
                resetText();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_float_tag;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        // mTitleBar
        getTitleBar().setTitle(TAG);

        // 通过onGlobalLayout方式获取宽高
        mIvBg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mImageWidth = mIvBg.getMeasuredWidth();
                mImageHeight = mIvBg.getMeasuredHeight();
                mIvBg.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                // mIvBg 宽高 1366*768
                mImageHeight = mImageWidth * 768 / 1366;
                int margin = DipUtils.getDimensionPixelSize(R.dimen.tc_margin_small);

                // mIvBg
                RelativeLayout.LayoutParams rightLayoutParam = new RelativeLayout.LayoutParams(mImageWidth, mImageHeight);
                rightLayoutParam.setMargins(margin, margin, margin, margin);
                mIvBg.setLayoutParams(rightLayoutParam);

                // 增加一个标签
                addNewTag();
            }
        });

        resetText();
    }

    /**
     * 增加一个标签
     */
    private void addNewTag() {
        mFloatTagBean = new FloatTagBean();
        mFloatTagBean.setPercentX(new Random().nextFloat());
        mFloatTagBean.setPercentY(new Random().nextFloat());
        mFloatTagBean.setText("标签" + (mIndex++));
        mFloatTagBean.setToLeft(new Random().nextBoolean());
        addFloatTag(mRlContainer, mImageWidth, mImageHeight, mFloatTagBean);
    }

    /**
     * 移除最后一个标签
     */
    private void removeLastTag() {
        int childCount = mRlContainer.getChildCount();
        if (childCount > 1 && childCount > 2) {
            mRlContainer.removeViews(childCount - 2, 2);
        }
    }

    /**
     * 移除全部标签
     */
    private void removeAllTag() {
        int childCount = mRlContainer.getChildCount();
        if (childCount > 1) {
            mRlContainer.removeViews(1, childCount - 1);
        }
    }

    /**
     * 添加标签
     */
    private void addFloatTag(ViewGroup container, int imageWidth, int imageHeight, FloatTagBean floatTagBean) {
        // 计算瑕疵点标签的xy坐标
        float positionX = imageWidth * floatTagBean.getPercentX();
        float positionY = imageHeight * floatTagBean.getPercentY();

        // ImageView
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.drawable.ic_float_tag_circle);
        // LayoutParams
        RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int imageViewWidth = getViewWidth(imageView);
        int imageViewHeight = getViewHeight(imageView);
        imageViewParams.leftMargin = (int) positionX - imageViewWidth / 2;
        imageViewParams.topMargin = (int) positionY - imageViewHeight / 2;

        // TextView
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(floatTagBean.getText());
        textView.setTextColor(mContext.getResources().getColor(R.color.tc_white));
        textView.setTextSize(11);
        textView.setBackgroundResource(R.drawable.ic_float_tag_to_left);
        textView.setGravity(Gravity.CENTER);
        // LayoutParams
        RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int textViewWidth = getViewWidth(textView);
        int textViewHeight = getViewHeight(textView);
        int spaceWidth = 8;
        if (floatTagBean.isToLeft()) { // 箭头方向朝左，即文本区域在圆点右面
            textView.setBackgroundResource(R.drawable.ic_float_tag_to_left);
            textViewParams.leftMargin = (int) positionX + imageViewWidth / 2 + spaceWidth;
            textViewParams.topMargin = (int) positionY - textViewHeight / 2;
        } else { // 箭头方向朝右，即文本区域在圆点左边
            textView.setBackgroundResource(R.drawable.ic_float_tag_to_right);
            textViewParams.leftMargin = (int) positionX - imageViewWidth / 2 - textViewWidth - spaceWidth;
            textViewParams.topMargin = (int) positionY - textViewHeight / 2;
        }

        // addView
        container.addView(imageView, imageViewParams);
        container.addView(textView, textViewParams);
    }

    /**
     * 计算控件宽
     */
    private int getViewWidth(View view) {
        view.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        return view.getMeasuredWidth();
    }

    /**
     * 计算控件宽
     */
    private int getViewHeight(View view) {
        view.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        return view.getMeasuredHeight();
    }

    private int mCount = 20;

    /**
     * 重置文本长度
     */
    private void resetText() {
//        final String text = LONG_TEXT.substring(0, new Random().nextInt(100));
        final String text = LONG_TEXT.substring(0, mCount++);
        mTvText.setText(text);

        if (!TextUtils.isEmpty(text)) {
            mTvText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // 控件宽度
                    int textViewWidth = mTvText.getWidth();
                    mTvText.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    // 文本宽度
                    TextPaint mTextPaint = mTvText.getPaint();
                    mTextPaint.setTextSize(mTvText.getTextSize());
                    int textContentWidth = (int) mTextPaint.measureText(text);

                    float lineCount = textContentWidth * 1.0f / textViewWidth;

                    StringBuilder builder = new StringBuilder();
                    builder.append("text.length() -> ").append(text.length());
                    builder.append("\n");
                    builder.append("textViewWidth -> ").append(textViewWidth);
                    builder.append("\n");
                    builder.append("textContentWidth -> ").append(textContentWidth);
                    builder.append("\n");
                    builder.append("lineCount -> ").append(lineCount);
                    mTvLineCount.setText(builder.toString());
                }
            });
        }
    }
}
