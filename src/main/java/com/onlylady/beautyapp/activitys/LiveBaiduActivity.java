//package com.onlylady.beautyapp.activitys;
//
//import android.content.res.Configuration;
//import android.hardware.Camera;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.view.GestureDetectorCompat;
//import android.text.TextUtils;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baidu.recorder.api.LiveSession;
//import com.baidu.recorder.api.SessionStateListener;
//import com.onlylady.beautyapp.R;
//import com.onlylady.beautyapp.configs.Configs;
//import com.onlylady.beautyapp.configs.EventBusC;
//import com.onlylady.beautyapp.engines.BaseEngine;
//import com.onlylady.beautyapp.urls.UrlsHolder;
//import com.onlylady.beautyapp.utils.DialogUtils;
//import com.onlylady.beautyapp.utils.LogUtils;
//import com.onlylady.beautyapp.utils.NetWorkState;
//import com.onlylady.beautyapp.utils.PhoneInfo;
//import com.onlylady.beautyapp.utils.TimeUtils;
//import com.onlylady.beautyapp.utils.ToastUtils;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import de.greenrobot.event.EventBus;
//
//public class LiveBaiduActivity extends BaseActivity
//        implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, SurfaceHolder.Callback {
//    private static final String TAG = "StreamingActivity";
//    private LiveSession mLiveSession = null;
//    Button mRecorderButton;
//    ProgressBar mLoadingAnimation;
//    View mFocusIcon;
//    private ImageView buttonback;
//
//    private boolean isSessionReady = false;
//    private boolean isSessionStarted = false;
//    private boolean isConnecting = false;
//    private boolean needRestartAfterStopped = false;
//
//    private static final int UI_EVENT_RECORDER_CONNECTING = 0;
//    private static final int UI_EVENT_RECORDER_STARTED = 1;
//    private static final int UI_EVENT_RECORDER_STOPPED = 2;
//    private static final int UI_EVENT_SESSION_PREPARED = 3;
//    private static final int UI_EVENT_HIDE_FOCUS_ICON = 4;
//    private static final int UI_EVENT_RESTART_STREAMING = 5;
//    private static final int UI_EVENT_RECONNECT_SERVER = 6;
//    private static final int UI_EVENT_STOP_STREAMING = 7;
//    private static final int UI_EVENT_SHOW_TOAST_MESSAGE = 8;
//    private static final int UI_EVENT_RESIZE_CAMERA_PREVIEW = 9;
//    private static final int UI_NETERROR = 10;
//
//    private Handler mUIEventHandler = null;
//    SurfaceView mCameraView;
//    private SessionStateListener mStateListener = null;
//    private GestureDetectorCompat mDetector = null;
//    private SurfaceHolder mHolder = null;
//    private int mCurrentCamera = -1;
//    private boolean isFlashOn = false;
//
//    private int mVideoWidth = 1280;
//    private int mVideoHeight = 720;
//    private int mFrameRate = 15;
//    private int mBitrate = 1024000;
//    private String code = "201604053000000fw99";
//    private String mStreamingUrl = "rtmp://w.gslb.lecloud.com/live/" + code; // TODO:: Replace it with your streaming url.
//    private String lid;
//    private TextView textTimer;
//    private Timer timer = new Timer();
//    private int time;
////    private boolean serverstop = false;
//
//    private TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    textTimer.setText(TimeUtils.getInstance().secToTime(time++));
//                }
//            });
//        }
//    };
//
//
//    @Override
//    public boolean useEventbus() {
//        return true;
//    }
//
//    public void onEvent(EventBusC c) {
//        if (c.getFrom() == EventBusC.STOPZHIBO) {
//            stopAndRelese();
//            //mLiveSession.stopPreview();
//        }
//
//    }
//
//    @Override
//    public void createview() {
//        Window win = getWindow();
//        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        win.requestFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_livebroadcast);
//
//        initUIElements();
//
//        mCurrentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;
//        isFlashOn = false;
//        initUIEventHandler();
//        initStateListener();
//
//        mDetector = new GestureDetectorCompat(this, this);
//        mDetector.setOnDoubleTapListener(this);
//    }
//
//    @Override
//    public void initlisener() {
//
//    }
//
//    @Override
//    protected void initData() {
//        lid = getIntent().getStringExtra("lid");
//    }
//
//
//    private void initUIElements() {
//        mLoadingAnimation = (ProgressBar) findViewById(R.id.progressBar);
//        mRecorderButton = (Button) findViewById(R.id.connect);
//        textTimer = (TextView) findViewById(R.id.text_timer);
//        mRecorderButton.setEnabled(false);
//        mFocusIcon = (ImageView) findViewById(R.id.iv_focus);
//        mCameraView = (SurfaceView) findViewById(R.id.camera);
//        mCameraView.getHolder().addCallback(this);
//        buttonback = (ImageView) findViewById(R.id.buttonback);
//    }
//
//    private void initUIEventHandler() {
//        mUIEventHandler = new Handler() {
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case UI_EVENT_RECORDER_CONNECTING:
//                        LogUtils.Log("UI_EVENT_RECORDER_CONNECTING");
//                        isConnecting = true;
//                        mRecorderButton.setBackgroundResource(R.mipmap.block);
//                        ToastUtils.show(getApplicationContext(), "连接中...");
//                        mRecorderButton.setEnabled(false);
//                        break;
//                    case UI_EVENT_RECORDER_STARTED:
//                        LogUtils.Log("Starting Streaming succeeded!");
//                        //成功开始
//                        buttonback.setVisibility(View.GONE);
//                        try {
//                            timer.schedule(timerTask, 1000, 1000);  // timeTask
//                        } catch (IllegalStateException e) {
//                            LogUtils.Log("重连");
//                            timer = new Timer();
//                            timer.schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            textTimer.setText(TimeUtils.getInstance().secToTime(time++));
//                                        }
//                                    });
//                                }
//                            }, 1000, 1000);  // timeTask
//                        }
//                        isSessionStarted = true;
//                        needRestartAfterStopped = false;
//                        isConnecting = false;
//                        mRecorderButton.setBackgroundResource(R.mipmap.to_stop);
//                        ToastUtils.show(getApplicationContext(), "已连接");
//                        mRecorderButton.setEnabled(true);
//                        break;
//                    case UI_EVENT_RECORDER_STOPPED:
//                        LogUtils.Log("Stopping Streaming succeeded!");
//                        isSessionStarted = false;
//                        needRestartAfterStopped = false;
//                        isConnecting = false;
//                        mRecorderButton.setBackgroundResource(R.mipmap.to_start);
//                        mRecorderButton.setEnabled(true);
//                        break;
//                    case UI_EVENT_SESSION_PREPARED:
//                        isSessionReady = true;
//                        mLoadingAnimation.setVisibility(View.GONE);
//                        if (!onstop) {
//                            mRecorderButton.setVisibility(View.VISIBLE);
//                            mRecorderButton.setEnabled(true);
//                        }
//                        break;
//                    case UI_EVENT_HIDE_FOCUS_ICON:
//                        mFocusIcon.setVisibility(View.GONE);
//                        break;
//                    case UI_EVENT_RECONNECT_SERVER:
//                        LogUtils.Log("Reconnecting to server...");
//                        timer.cancel();
//                        if (isSessionReady) {
////                            mLiveSession.startRtmpSession(mStreamingUrl);
//
//                            // TODO: 16/4/11 重连
//                            BaseEngine.getInstance().getStringPost(Configs.getDOMAINV110(), UrlsHolder.getInstance().getParams1103(lid), new BaseEngine.CallbackForT<String>() {
//                                @Override
//                                public void finish(String bean) {
//                                    JSONObject jsonObject = null;
//                                    try {
//                                        jsonObject = new JSONObject(bean);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    if (jsonObject.optInt("errcode") == 0) {
//                                        String rtmp = jsonObject.optJSONObject("data").optString("pu") + "/" + jsonObject.optJSONObject("data").optString("ps");
//                                        LogUtils.Log(rtmp);
//                                        mStreamingUrl = rtmp;
//                                        if (mLiveSession != null)
//                                            mLiveSession.startRtmpSession(rtmp);
////                                        if (mLiveSession.startRtmpSession(rtmp)) {//开始
////                                           // mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_CONNECTING);
////                                        }
//
//                                    }
//                                }
//
//                                @Override
//                                public void finish(List<String> listT) {
//
//                                }
//                            });
//                        }
//                        if (mUIEventHandler != null)
//                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_CONNECTING);
//                        break;
//                    case UI_EVENT_STOP_STREAMING:
//                        if (!isConnecting) {
//                            LogUtils.Log("Stopping current session...");
//                            if (isSessionReady) {
//                                mLiveSession.stopRtmpSession();
//                            }
//                            if (mUIEventHandler != null)
//                                mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_STOPPED);
//                        }
//                        break;
//                    case UI_EVENT_RESTART_STREAMING:
//                        if (!isConnecting) {
//                            LogUtils.Log("Restarting session...");
//                            isConnecting = true;
//                            needRestartAfterStopped = true;
//                            if (isSessionReady) {
//                                mLiveSession.stopRtmpSession();
//                            }
//                            if (mUIEventHandler != null)
//                                mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_CONNECTING);
//                        }
//                        break;
//                    case UI_EVENT_SHOW_TOAST_MESSAGE:
//                        String text = (String) msg.obj;
//                        LogUtils.Log(text);
////                        Toast.makeText(LiveBaiduActivity.this, text, Toast.LENGTH_SHORT).show();
//                        break;
//                    case UI_EVENT_RESIZE_CAMERA_PREVIEW:
//                        String hint = String.format("注意：当前摄像头不支持您所选择的分辨率\n实际分辨率为%dx%d", mVideoWidth, mVideoHeight);
//                        Toast.makeText(LiveBaiduActivity.this, hint, Toast.LENGTH_LONG).show();
//                        fitPreviewToParentByResolution(mCameraView.getHolder(), mVideoWidth, mVideoHeight);
//                        break;
//                    case UI_NETERROR:
//                        stopAndRelese();
//                        ToastUtils.show(getApplicationContext(), "直播已暂停，请返回列表，点击继续直播");
//                        break;
//                    default:
//                        break;
//                }
//                super.handleMessage(msg);
//            }
//        };
//    }
//
//    private void initStateListener() {
//        mStateListener = new SessionStateListener() {
//            @Override
//            public void onSessionPrepared(int code) {
//                if (code == SessionStateListener.RESULT_CODE_OF_OPERATION_SUCCEEDED) {
//                    if (mUIEventHandler != null) {
//                        mUIEventHandler.sendEmptyMessage(UI_EVENT_SESSION_PREPARED);
//                    }
//                    int realWidth = mLiveSession.getAdaptedVideoWidth();
//                    int realHeight = mLiveSession.getAdaptedVideoHeight();
////                    Log.d("zhibo", "realHeight" + realHeight + "---------realWidth" + realWidth);
//                    if (realHeight != mVideoHeight || realWidth != mVideoWidth) {
//                        mVideoHeight = realHeight;
//                        mVideoWidth = realWidth;
//                        if (mUIEventHandler != null)
//                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RESIZE_CAMERA_PREVIEW);
//                    }
//                }
//            }
//
//            @Override
//            public void onSessionStarted(int code) {
//                LogUtils.Log("onSessionStarted");
//                if (code == SessionStateListener.RESULT_CODE_OF_OPERATION_SUCCEEDED) {
//                    if (mUIEventHandler != null) {
//                        mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_STARTED);
//                    }
//                } else {
//                    LogUtils.Log("Starting Streaming failed!");
//                }
//            }
//
//            @Override
//            public void onSessionStopped(int code) {
//                if (code == SessionStateListener.RESULT_CODE_OF_OPERATION_SUCCEEDED) {
//                    if (mUIEventHandler != null) {
//                        if (needRestartAfterStopped && isSessionReady) {
//                            mLiveSession.startRtmpSession(mStreamingUrl);
//                        } else {
//                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_STOPPED);
//                        }
//                    }
//                } else {
//                    LogUtils.Log("Stopping Streaming failed!");
//                }
//            }
//
//            @Override
//            public void onSessionError(int code) {
//                switch (code) {
//                    case SessionStateListener.ERROR_CODE_OF_OPEN_MIC_FAILED:
//                        LogUtils.Log("Error occurred while opening MIC!");
//
//                        onOpenDeviceFailed();
//                        break;
//                    case SessionStateListener.ERROR_CODE_OF_OPEN_CAMERA_FAILED:
//                        LogUtils.Log("Error occurred while opening Camera!");
//                        onOpenDeviceFailed();
//                        break;
//                    case SessionStateListener.ERROR_CODE_OF_PREPARE_SESSION_FAILED:
//                        LogUtils.Log("Error occurred while preparing recorder!");
//                        onPrepareFailed();
//                        break;
//                    case SessionStateListener.ERROR_CODE_OF_CONNECT_TO_SERVER_FAILED:
//                        LogUtils.Log("Error occurred while connecting to server!");
//                        if (mUIEventHandler != null) {
////                            ToastUtils.show(getApplicationContext(),"直播连接中...");
////                            Message msg = new Message();
////                            msg.what = UI_EVENT_SHOW_TOAST_MESSAGE;
////                            msg.obj = "连接推流服务器失败，正在重试！";
//////                            LogUtils.Log("");
////                            mUIEventHandler.sendMessage(msg);
//                            if (!NetWorkState.getInstance().isConnect(getApplicationContext())) {
//                                Message msg = new Message();
//                                msg.what = UI_NETERROR;
//                                mUIEventHandler.sendMessage(msg);
//                            }
////                            mUIEventHandler.sendEmptyMessageDelayed(UI_EVENT_RECONNECT_SERVER, 2000);
//                        }
//                        break;
//                    case SessionStateListener.ERROR_CODE_OF_DISCONNECT_FROM_SERVER_FAILED:
//                        LogUtils.Log("Error occurred while disconnecting from server!");
//                        isConnecting = false;
//                        // Although we can not stop session successfully, we still need to take it as stopped
//                        if (mUIEventHandler != null) {
//                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_STOPPED);
//                        }
//                        break;
//                    default:
//                        onStreamingError(code);
//                        break;
//                }
//            }
//        };
//    }
//
//    private void onOpenDeviceFailed() {
//        if (mUIEventHandler != null) {
//            Message msg = new Message();
//            msg.what = UI_EVENT_SHOW_TOAST_MESSAGE;
//            msg.obj = "摄像头或MIC打开失败！请确认您已开启相关硬件使用权限！";
//            mUIEventHandler.sendMessage(msg);
//        }
////        StreamingActivity.this.finish();
//    }
//
//    private void onPrepareFailed() {
//        isSessionReady = false;
//    }
//
//    int mWeakConnectionHintCount = 0;
//
//    private void onStreamingError(int errno) {
//        Message msg = new Message();
//        msg.what = UI_EVENT_SHOW_TOAST_MESSAGE;
//        switch (errno) {
//            case SessionStateListener.ERROR_CODE_OF_PACKET_REFUSED_BY_SERVER:
//            case SessionStateListener.ERROR_CODE_OF_SERVER_INTERNAL_ERROR:
//                msg.obj = "因服务器异常，当前直播已经中断！正在尝试重新推流...";
//                if (mUIEventHandler != null) {
//                    mUIEventHandler.sendMessage(msg);
//                    mUIEventHandler.sendEmptyMessage(UI_EVENT_RESTART_STREAMING);
//                }
//                break;
//            case SessionStateListener.ERROR_CODE_OF_WEAK_CONNECTION:
//                LogUtils.Log("Weak connection...");
//                msg.obj = "当前网络不稳定，请检查网络信号！";
//                mWeakConnectionHintCount++;
//                if (mUIEventHandler != null) {
//                    mUIEventHandler.sendMessage(msg);
//                    if (mWeakConnectionHintCount >= 5) {
//                        mWeakConnectionHintCount = 0;
//                        mUIEventHandler.sendEmptyMessage(UI_EVENT_RESTART_STREAMING);
//                    }
//                }
//                break;
//            case SessionStateListener.ERROR_CODE_OF_CONNECTION_TIMEOUT:
//                LogUtils.Log("Timeout when streaming...");
//                msg.obj = "连接超时，请检查当前网络是否畅通！我们正在努力重连...";
//                if (mUIEventHandler != null) {
//                    mUIEventHandler.sendMessage(msg);
//                    mUIEventHandler.sendEmptyMessage(UI_EVENT_RESTART_STREAMING);
//                }
//                break;
//            default:
//                LogUtils.Log("Unknown error when streaming...");
//                msg.obj = "未知错误，当前直播已经中断！正在重试！";
//                mUIEventHandler.sendMessage(msg);
//                if (mUIEventHandler != null) {
//                    mUIEventHandler.sendMessage(msg);
//                    mUIEventHandler.sendEmptyMessageDelayed(UI_EVENT_RESTART_STREAMING, 1000);
//                }
//                break;
//        }
//    }
//
//    private void initRTMPSession(SurfaceHolder sh) {
//        mLiveSession = new LiveSession(this, mVideoWidth, mVideoHeight, mFrameRate, mBitrate, mCurrentCamera);
//        mLiveSession.setStateListener(mStateListener);
//        mLiveSession.bindPreviewDisplay(sh);
//        mLiveSession.prepareSessionAsync();
//    }
//
//    public void onClickSwitchCamera(View v) {
//        if (mLiveSession.canSwitchCamera()) {
//            if (mCurrentCamera == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                mCurrentCamera = Camera.CameraInfo.CAMERA_FACING_FRONT;
//                mLiveSession.switchCamera(mCurrentCamera);
//
//            } else {
//                mCurrentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;
//                mLiveSession.switchCamera(mCurrentCamera);
//
//            }
//        } else {
//            Toast.makeText(this, "抱歉！该分辨率下不支持切换摄像头！", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void onClickBack(View v) {
//        if (isSessionStarted) {
//            mLiveSession.stopRtmpSession();
//        }
//        finish();
//    }
//
//    public void onClickStreamingButton(View v) {
//        if (!isSessionReady) {
//            return;
//        }
//        if (PhoneInfo.conn != 1) {
//            DialogUtils.getInstance().goOnOrOnt(getApplicationContext(), new DialogUtils.YesNot() {
//                @Override
//                public void positive() {
//                    //继续
//                    beginpush();
//                }
//
//                @Override
//                public void negative() {
//
//                }
//            });
//        } else {
//            beginpush();
//        }
//
//    }
//
//    private void beginpush() {
//        if (!isSessionStarted && !TextUtils.isEmpty(mStreamingUrl)) {
//            BaseEngine.getInstance().getStringPost(Configs.getDOMAINV110(), UrlsHolder.getInstance().getParams1103(lid), new BaseEngine.CallbackForT<String>() {
//                @Override
//                public void finish(String bean) {
//                    JSONObject jsonObject = null;
//                    try {
//                        jsonObject = new JSONObject(bean);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    if (jsonObject.optInt("errcode") == 0) {
//                        String rtmp = jsonObject.optJSONObject("data").optString("pu") + "/" + jsonObject.optJSONObject("data").optString("ps");
//                        LogUtils.Log(rtmp);
//                        mStreamingUrl = rtmp;
//                        if (mLiveSession != null)
//                            if (mLiveSession.startRtmpSession(rtmp)) {//开始
//                                mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_CONNECTING);
//                                EventBus.getDefault().post(EventBusC.getInstance(EventBusC.MINEREFRESS, null));//开始后刷新
//                            }
//
//                    }
//                }
//
//                @Override
//                public void finish(List<String> listT) {
//
//                }
//            });
//
//
//        } else {
//            BaseEngine.getInstance().getStringPost(Configs.getDOMAINV110(), UrlsHolder.getInstance().getParams1104(lid), new BaseEngine.CallbackForT<String>() {
//                @Override
//                public void finish(String bean) {
//                    JSONObject jsonObject = null;
//                    try {
//                        jsonObject = new JSONObject(bean);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    if (jsonObject.optInt("errcode") == 0) {
//                        if (mLiveSession.stopRtmpSession()) {//停止直播
//                            timer.cancel();
////                            mUIEventHandler.sendEmptyMessage(UI_EVENT_RECORDER_CONNECTING);
//                            LiveBaiduActivity.this.finish();
//
//                        }
//                    }
//                }
//
//                @Override
//                public void finish(List<String> listT) {
//
//                }
//            });
//
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (isSessionStarted) {
//            mLiveSession.stopRtmpSession();
//        }
//        finish();
//    }
//
//    @Override
//    public void onStart() {
//        LogUtils.Log("===========> onStart()");
//        super.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (onstop) {
//            ToastUtils.show(getApplicationContext(), "直播已暂停，请返回列表，点击继续直播");
//        }
//    }
//
//    private boolean onstop = false;
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        LogUtils.Log("===========> onPause()");
//        stopAndRelese();
//    }
//
//    private void stopAndRelese() {
//        timer.cancel();
//        mLiveSession.stopPreview();
//        mLiveSession.setStateListener(null);
//        if (isSessionStarted) {
//            onstop = true;
//            mLiveSession.stopRtmpSession();
//        }
//        buttonback.setVisibility(View.VISIBLE);
//        mRecorderButton.setVisibility(View.GONE);
//
//
//    }
//
//    @Override
//    protected void onStop() {
//        LogUtils.Log("===========> onStop()");
//        super.onStop();
////        if (isSessionStarted) {
////            mLiveSession.stopRtmpSession();
////            isSessionStarted = false;
////        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        LogUtils.Log("===========> onDestroy()");
//        EventBus.getDefault().post(EventBusC.getInstance(EventBusC.MINEREFRESS, null));
//        if (mUIEventHandler != null)
//            mUIEventHandler.removeCallbacksAndMessages(null);
//        if (isSessionStarted) {
//            mLiveSession.stopRtmpSession();
//            isSessionStarted = false;
//        }
//        if (isSessionReady) {
//            mLiveSession.destroyRtmpSession();
//            mLiveSession = null;
//            mStateListener = null;
//
//            mUIEventHandler = null;
//            isSessionReady = false;
//        }
//
//        super.onDestroy();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (this.mDetector.onTouchEvent(event)) {
//            return true;
//        }
//        // Be sure to call the superclass implementation
//        return super.onTouchEvent(event);
//    }
//
//    @Override
//    public boolean onDown(MotionEvent arg0) {
//        return false;
//    }
//
//    @Override
//    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent arg0) {
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent arg0) {
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent arg0) {
//        return false;
//    }
//
//    @Override
//    public boolean onDoubleTap(MotionEvent arg0) {
//        return false;
//    }
//
//    @Override
//    public boolean onDoubleTapEvent(MotionEvent arg0) {
//        if (mLiveSession != null && !mLiveSession.zoomInCamera()) {
//            LogUtils.Log("Zooming camera failed!");
//            mLiveSession.cancelZoomCamera();
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onSingleTapConfirmed(MotionEvent arg0) {
//        if (mLiveSession != null) {
//            mLiveSession.focusToPosition((int) arg0.getX(), (int) arg0.getY());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                mFocusIcon.setX(arg0.getX() - mFocusIcon.getWidth() / 2);
//                mFocusIcon.setY(arg0.getY() - mFocusIcon.getHeight() / 2);
//            }
//            mFocusIcon.setVisibility(View.VISIBLE);
//            mUIEventHandler.sendEmptyMessageDelayed(UI_EVENT_HIDE_FOCUS_ICON, 1000);
//        }
//        return true;
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
//        if (isSessionReady) {
//            mLiveSession.startPreview();
//        }
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        if (mLiveSession != null) {
//            mLiveSession.bindPreviewDisplay(holder);
//        } else {
//            mHolder = holder;
//        }
//        fitPreviewToParentByResolution(holder, mVideoWidth, mVideoHeight);
//        initRTMPSession(holder);
//    }
//
//    private void fitPreviewToParentByResolution(SurfaceHolder holder, int width, int height) {
//        // Adjust the size of SurfaceView dynamically
//        int screenHeight = getWindow().getDecorView().getRootView().getHeight();
//        int screenWidth = getWindow().getDecorView().getRootView().getWidth();
//
//        if (getResources().getConfiguration().orientation
//                == Configuration.ORIENTATION_PORTRAIT) { // If portrait, we should swap width and height
//            width = width ^ height;
//            height = width ^ height;
//            width = width ^ height;
//        }
//
//        int adjustedVideoHeight = screenHeight;
//        int adjustedVideoWidth = screenWidth;
//        if (width * screenHeight > height * screenWidth) { // means width/height > screenWidth/screenHeight
//            // Fit width
//            adjustedVideoHeight = height * screenWidth / width;
//            adjustedVideoWidth = screenWidth;
//        } else {
//            // Fit height
//            adjustedVideoHeight = screenHeight;
//            adjustedVideoWidth = width * screenHeight / height;
//        }
//        holder.setFixedSize(adjustedVideoWidth, adjustedVideoHeight);
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder arg0) {
//        if (isSessionReady) {
//            mLiveSession.stopPreview();
//        }
//    }
//
//}
//
