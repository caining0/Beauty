package com.onlylady.beautyapp.exlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import com.onlylady.beautyapp.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MovieRecorderView extends LinearLayout   {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private MediaRecorder mMediaRecorder;
    private Camera mCamera;

    private OnRecordFinishListener mOnRecordFinishListener;//

    private int mWidth;// 视频分辨率宽度  
    private int mHeight;// 视频分辨率高度  
    private boolean isOpenCamera;// 是否一开始就打开摄像头
    private File mVecordFile = null;// 文件

    public MovieRecorderView(Context context) {
        this(context, null);
    }

    public MovieRecorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MovieRecorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mWidth = 1280;
        mHeight = 720;
        isOpenCamera = true;

        LayoutInflater.from(context).inflate(R.layout.moive_recorder_view, this);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//        mProgressBar.setMax(mRecordMaxTime);// 设置进度条最大量

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new CustomCallBack());
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /**
     * @author cnn
     * @date 2015-2-5
     */
    private class CustomCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (!isOpenCamera)
                return;
            try {
                initCamera();
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (!isOpenCamera)
                return;
            freeCameraResource();
        }

    }

    /**
     * 初始化摄像头
     *
     * @throws IOException
     * @author lip
     * @date 2015-3-16
     */
    private void initCamera() throws IOException {
        if (mCamera != null) {
            freeCameraResource();
        }
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
            freeCameraResource();
        }
        if (mCamera == null)
            return;

        setCameraParams();
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewDisplay(mSurfaceHolder);
        mCamera.startPreview();
        mCamera.unlock();
    }

    /**
     * 设置摄像头为竖屏
     *
     * @author lip
     * @date 2015-3-16
     */
    private void setCameraParams() {
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            params.set("orientation", "portrait");
            mCamera.setParameters(params);
        }
    }

    /**
     * 释放摄像头资源
     *
     * @author cnn
     * @date 2015-2-5
     */
    private void freeCameraResource() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

    private File createRecordFile() {
        // File sampleDir = new File(Environment.getExternalStorageDirectory() + File.separator + "im/video/");
        File sampleDir = new File(Environment.getExternalStorageDirectory() + File.separator + "RecordVideo/");
        //File sampleDir = new File("/video/");  
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }
        File vecordDir = sampleDir;
        try {
            mVecordFile = File.createTempFile("recording", ".mp4", vecordDir);
            return mVecordFile;//mp4格式
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 初始化
     *
     * @throws IOException
     * @author lip
     * @date 2015-3-16
     */
    private void initRecord() throws IOException {

        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.reset();
        if (mCamera != null)
            mMediaRecorder.setCamera(mCamera);
//        mMediaRecorder.setOnErrorListener(this);
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音频源
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 视频输出格式
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 音频格式
        mMediaRecorder.setVideoSize(mWidth, mHeight);// 设置分辨率：  
        // mMediaRecorder.setVideoFrameRate(16);// 这个我把它去掉了，感觉没什么用  
        mMediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);// 设置帧频率，然后就清晰了
        mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制  
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// 视频录制格式
        // mediaRecorder.setMaxDuration(Constant.MAXVEDIOTIME * 1000);

        mMediaRecorder.setOutputFile(createRecordFile().getAbsolutePath());
        mMediaRecorder.prepare();
        try {
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始录制视频
     *
     * @param onRecordFinishListener 达到指定时间之后回调接口
     * @author cnn
     * @date 2015-2-5
     * //     * @param fileName
     * //     *            视频储存位置
     */
    private  int count =0;
    public void record(final OnRecordFinishListener onRecordFinishListener) {
        this.mOnRecordFinishListener = onRecordFinishListener;
      /*  try {

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (count>0){
                    mMediaRecorder.stop();
                    mMediaRecorder.release();
                }
                count++;
                try {
                    if (!isOpenCamera)
                        initCamera();
                    initRecord();
                    mOnRecordFinishListener.onbegining(mVecordFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5000);

    }


    /**
     * 停止拍摄
     *
     * @author cnn
     * @date 2015-2-5
     */
    public void stop() {
        stopRecord();
        releaseRecord();
        freeCameraResource();
    }

    /**
     * 停止录制
     *
     * @author cnn
     * @date 2015-2-5
     */
    public void stopRecord() {
//        mProgressBar.setProgress(0);

        if (mMediaRecorder != null) {
            // 设置后不会崩  
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            try {
                mMediaRecorder.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放资源
     *
     * @author cnn
     * @date 2015-2-5
     */
    private void releaseRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder.setOnErrorListener(null);
            try {
                mMediaRecorder.release();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mMediaRecorder = null;
    }


    /**
     * @return the mVecordFile
     */
    public File getmVecordFile() {
        return mVecordFile;
    }

    /**
     * 录制完成回调接口
     *
     * @author lip
     * @date 2015-3-16
     */
    public interface OnRecordFinishListener {
        public void onbegining(String filePath);


    }

  /*  @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        try {
            if (mr != null)
                mr.reset();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}