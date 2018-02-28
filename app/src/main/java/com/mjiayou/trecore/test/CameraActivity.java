package com.mjiayou.trecore.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.base.TCActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by treason on 2017/11/9.
 */

public class CameraActivity extends TCActivity {

    public static final int PICTURE_WIDTH = 1600;
    public static final int PICTURE_HEIGHT = 1066;

    private SurfaceView mSurfaceView;
    private Button mBtnTake;

    private SurfaceHolder mSurfaceHolder = null;
    private Camera mCamera = null;
    private boolean mPreviewing = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        // findViewById
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mBtnTake = (Button) findViewById(R.id.btn_take);

        // mySurfaceView & mSurfaceHolder
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceHolderCallback);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // mBtnTake
        mBtnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCamera != null) {
                    mCamera.autoFocus(mAutoFocusCallback);
                }
            }
        });
    }

    /**
     * mSurfaceHolderCallback
     */
    private SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mCamera = Camera.open(); //取第一个摄像头
            mCamera.setDisplayOrientation(90);

            Camera.Parameters param = mCamera.getParameters();
            setSize(param, mSurfaceView.getWidth(), mSurfaceView.getHeight(), PICTURE_WIDTH, PICTURE_HEIGHT);
            param.setPictureFormat(PixelFormat.JPEG);
            param.set("jpeg-quality", 100);
            mCamera.setParameters(param);
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera.startPreview(); // 预览
            mPreviewing = true;
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mCamera != null) {
                if (mPreviewing) {
                    mCamera.stopPreview();
                    mPreviewing = false;
                }
                mCamera.release();//释放摄像头
            }
        }
    };

    /**
     * mAutoFocusCallback
     */
    private Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                mCamera.takePicture(null, null, mPictureCallback);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "对焦失败，请重试", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    };

    /**
     * mPictureCallback
     */
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "拍照成功，正在保存...", Toast.LENGTH_LONG).show();
                }
            });

            // 保存图片
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            String fileName = Environment.getExternalStorageDirectory().toString()
                    + File.separator
                    + "CameraDemo"
                    + File.separator
                    + "pic_" + System.currentTimeMillis() + ".jpg";

            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();//创建文件夹
            }

            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos); // 向缓冲区压缩图片
                bos.flush();
                bos.close();
                Toast.makeText(mContext, "保存成功，照片存储在" + fileName, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(mContext, "保存失败！" + e.toString(), Toast.LENGTH_LONG).show();
            }

            mCamera.stopPreview();
            mCamera.startPreview();
        }
    };

    /**
     * 设置一个preview和picture的最合适大小，预览和拍照比例相等，最小是指定的四个参数
     *
     * @param parameters    相机参数
     * @param previewWidth  期望预览宽高，传入surfaceView的大小就好了
     * @param previewHeight
     */
    private void setSize(Camera.Parameters parameters, int previewWidth, int previewHeight, int picWidth, int picHeight) {
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();

        Camera.Size previewSize = null;
        Camera.Size pictureSize = null;

        float radioSurface = (float) previewWidth / previewHeight;

        // 从大到小排序
        Collections.sort(previewSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                return lhs.width - rhs.width;
            }
        });
        Collections.sort(pictureSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                return rhs.width - lhs.width;
            }
        });

        float facur = 0.05f;
        int count = 0;
        do {
            for (int i = 0; i < previewSizes.size(); i++) {// 获得一个宽高都大于Surfaceiew的size，并且比例和屏幕接近
                Camera.Size size = previewSizes.get(i);
                if (size.width > previewWidth / 2 && size.height > previewHeight / 2 && Math.abs((float) size.width / size.height - radioSurface) < facur) {
                    previewSize = size;
                    break;
                }
                count++;
            }
            facur = facur + 0.05f;
        } while (previewSize == null);

        facur = 0.01f;
        do {// 应该只执行一次
            for (int i = 0; i < pictureSizes.size(); i++) { // 取得一个和预览宽高比最相近的拍照尺寸
                if (pictureSizes.get(i).width > picWidth && pictureSizes.get(i).height > picHeight) {
                    if (Math.abs((float) pictureSizes.get(i).width / pictureSizes.get(i).height - (float) previewSize.width / previewSize.height) <= facur) {
                        pictureSize = pictureSizes.get(i);
                        break;
                    }
                } else {
                    break;
                }
            }
            facur = facur + 0.05f;
        } while (pictureSize == null);

        parameters.setPreviewSize(previewSize.width, previewSize.height);
        parameters.setPictureSize(pictureSize.width, pictureSize.height); // 设置照片的大小
    }
}
