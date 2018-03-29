package com.mjiayou.trecore.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjiayou.trecore.R;

/**
 * Created by neo on 18/2/7.
 */
public class UxinBounceView extends RelativeLayout {
    private Context mContext;
    private boolean onPlayOnce = true;
    private Thread mThread;
    private Runnable mRunnable;
    private volatile boolean isShutDown;
    private volatile boolean executeOnce = true;

    private View viewBackground, viewMask;
    private TextView tvVehicleNum;
    private ImageView imgAnimation;
    private RelativeLayout rlBounceContainer;
    private Handler handler = new Handler();
    private int duration;
    private boolean isAnimationEnd = false;
    private boolean isReset = false;

    private boolean fromLoadAgain = true;
    private boolean readyForBounce = true;

    public UxinBounceView(Context context) {
        this(context, null);
    }

    public UxinBounceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UxinBounceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.view_uxin_bounce, this);
        imgAnimation = (ImageView) view.findViewById(R.id.imgAnimation);
        tvVehicleNum = (TextView) view.findViewById(R.id.tvVehicleNum);
        viewBackground = view.findViewById(R.id.viewBackground);
        viewMask = view.findViewById(R.id.viewMask);
        rlBounceContainer = (RelativeLayout) view.findViewById(R.id.rlBounceContainer);

        viewBackground.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnViewClickListener != null) {
                    mOnViewClickListener.onclick();
                }
            }
        });

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                if (fromLoadAgain) {
                    startAnimation();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

    }

    private void processAnimation(String totalNum) {
        //Log.i("neo","processAnimation ");
        onPlayOnce = false;
        fromLoadAgain = false;
        if (Integer.parseInt(totalNum) == 0) {
            tvVehicleNum.setText("查看全部车源");
        } else {
            tvVehicleNum.setText("查看全部" + totalNum + "辆车源");
        }
        final AnimationDrawable drawable = (AnimationDrawable) imgAnimation.getDrawable();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                while (!isShutDown) {
                    if (executeOnce && drawable.getCurrent() == drawable.getFrame(55)) {
                        executeOnce = false;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ObjectAnimator translationX = ObjectAnimator.ofFloat(viewMask, "translationX", 0, tvVehicleNum.getWidth());
                                translationX.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        viewMask.setVisibility(View.GONE);
                                        viewMask.setTranslationX(0);
                                        mRunnable = null;
                                        mThread = null;
                                    }
                                });
                                translationX.setDuration((long) (1.002445 * tvVehicleNum.getWidth())).start();
                            }

                        });
                    }
                    if (drawable.getCurrent() == drawable.getFrame(69)) {
                        isShutDown = true;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                final Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.uxin_bounce_view_bg);
                                animation.setInterpolator(new MyBounceInterpolator(0.55, 10.5));
                                rlBounceContainer.removeView(imgAnimation);
                                viewBackground.setVisibility(View.VISIBLE);
                                tvVehicleNum.setTextColor(Color.WHITE);
                                viewBackground.startAnimation(animation);
                                viewMask.setVisibility(GONE);
                                isAnimationEnd = true;
                            }
                        });
                    }
                }
            }
        };
        mThread = new Thread(mRunnable);
        mThread.start();
        drawable.start();
    }

    private String totalNum;

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
        //Log.i("neo","setTotalNum totalNum = "+totalNum+"   readyForBounce  = "+HomeFragmentTmp.readyForBounce);
        if (TextUtils.isEmpty(totalNum)) {
            setVisibility(VISIBLE);
            viewBackground.setVisibility(VISIBLE);
            tvVehicleNum.setTextColor(Color.WHITE);
            imgAnimation.setVisibility(GONE);
            viewMask.setVisibility(GONE);
            tvVehicleNum.setText("查看全部车源");
        } else {
            if (Integer.parseInt(totalNum) == 0) {
                tvVehicleNum.setText("查看全部车源");
            } else {
                tvVehicleNum.setText("查看全部" + totalNum + "辆车源");
            }
            if (readyForBounce) {
                startAnimation();
            }
        }
    }

    public void startAnimation() {
        //Log.i("neo","startAnimation totalNum "+totalNum+" onPlayOnce = "+onPlayOnce +" isFocusChange = "+isFocusChange);
        if (TextUtils.isEmpty(totalNum)) return;
        if (onPlayOnce) {
            setVisibility(VISIBLE);
            try {
                if (!TextUtils.isEmpty(totalNum)) {
                    readyForBounce = false;
                    processAnimation(totalNum);
                } else {
                    viewBackground.setVisibility(VISIBLE);
                    tvVehicleNum.setTextColor(Color.WHITE);
                    imgAnimation.setVisibility(GONE);
                    viewMask.setVisibility(GONE);
                    tvVehicleNum.setText("查看全部车源");
                }
            } catch (Exception e) {
                e.printStackTrace();
                viewBackground.setVisibility(VISIBLE);
                tvVehicleNum.setTextColor(Color.WHITE);
                imgAnimation.setVisibility(GONE);
                viewMask.setVisibility(GONE);
                tvVehicleNum.setText("查看全部车源");
            }
        }
    }

    static class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1; // 振幅
        private double mFrequency = 10; // 频率

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) * Math.cos(mFrequency * time) + 1);
        }
    }

    /**
     * OnViewClickListener
     */
    public interface OnViewClickListener {
        void onclick();
    }

    private OnViewClickListener mOnViewClickListener;

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.mOnViewClickListener = onViewClickListener;
    }


    public boolean isAnimationEnd() {
        return isAnimationEnd;
    }

    public void reset() {
        onPlayOnce = true;
        executeOnce = true;
        isShutDown = false;
    }
}
