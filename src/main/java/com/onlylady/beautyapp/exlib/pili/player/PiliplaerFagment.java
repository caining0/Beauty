package com.onlylady.beautyapp.exlib.pili.player;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.configs.EventBusC;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PlayerCode;
import com.pili.pldroid.player.widget.VideoView;

import de.greenrobot.event.EventBus;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * A simple {@link Fragment} subclass.
 */
public class PiliplaerFagment extends Fragment implements
        IjkMediaPlayer.OnCompletionListener,
        IjkMediaPlayer.OnInfoListener,
        IjkMediaPlayer.OnErrorListener,
        IjkMediaPlayer.OnVideoSizeChangedListener,
        IjkMediaPlayer.OnPreparedListener{
    private static final String TAG = "VideoPlayerActivity";
    private static final int REQ_DELAY_MILLS = 3000;

    private VideoView mVideoView;
//    private View mBufferingIndicator;
//    private MediaController mMediaController;
    private RelativeLayout mAspectLayout;
    private ViewGroup.LayoutParams mLayoutParams;
    private Pair<Integer, Integer> mScreenSize;

    private String mVideoPath;
//    private Button mBackBtn;
    private long mLastPosition = 0;
    private boolean mIsLiveStream = false;

    private int mReqDelayMills = REQ_DELAY_MILLS;
    private boolean mIsCompleted = false;
    private Runnable mVideoReconnect;


    private ImageView fullscrean;
    public  boolean isfullscreen = false;
    private int[] wAndH = new int[2];
    private TextView zhibozhuantai;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_player, null, false);
        init(v);
        return v;
    }

    private void init(View v) {
        mVideoPath = getActivity().getIntent().getStringExtra("videoPath");

        Intent intent = getActivity().getIntent();
        String intentAction = intent.getAction();
        if (!TextUtils.isEmpty(intentAction) && intentAction.equals(Intent.ACTION_VIEW)) {
            mVideoPath = intent.getDataString();
        }

        mAspectLayout = (RelativeLayout)v.findViewById(R.id.aspect_layout);

//        mBackBtn = (Button) v.findViewById(R.id.back_btn);
//        mBackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mVideoView.stopPlayback();
//                getActivity().onBackPressed();
//                getActivity().finish();
//            }
//        });
//        mBufferingIndicator =v. findViewById(R.id.buffering_indicator);
        boolean useFastForward = true;
        boolean disableProgressBar = false;

        Log.i(TAG, "mVideoPath:" + mVideoPath);
        // Tip: you can custom the variable depending on your situation
        mIsLiveStream = true;
        if (mIsLiveStream) {
            disableProgressBar = true;
            useFastForward = false;
        }
//        mMediaController = new MediaController(  getActivity(), useFastForward, disableProgressBar);

//        SharedLibraryNameHelper.getInstance().renameSharedLibrary("pldroidplayer_v7a");

//        mVideoView = (VideoTextureView) findViewById(R.id.video_view);
        mVideoView = (VideoView) v.findViewById(R.id.video_view);
        fullscrean = (ImageView) v.findViewById(R.id.fullscreen);
        fullscrean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreen();
            }
        });
        zhibozhuantai= (TextView) v.findViewById(R.id.text_zhibozhuangtai);
        String text = getActivity().getIntent().getStringExtra("text");
        if (!TextUtils.isEmpty(text)) {
            zhibozhuantai.setText(text);//直播结束或其他状态
//            zhibo = false;
        }

//        mVideoView.setVideoPath(mVideoPath);
//        mVideoView.start();
//        mMediaController.setMediaPlayer(mVideoView);
//        mVideoView.setMediaController(mMediaController);
//        mVideoView.setMediaBufferingIndicator(mBufferingIndicator);

        AVOptions options = new AVOptions();
        options.setInteger(AVOptions.KEY_MEDIACODEC, 0); // 1 -> enable, 0 -> disable

        Log.i(TAG, "mIsLiveStream:" + mIsLiveStream);
        if (mIsLiveStream) {
            options.setInteger(AVOptions.KEY_BUFFER_TIME, 1000); // the unit of buffer time is ms
            options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000); // the unit of timeout is ms
            options.setString(AVOptions.KEY_FFLAGS, AVOptions.VALUE_FFLAGS_NOBUFFER); // "nobuffer"
            options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        }

        mVideoView.setAVOptions(options);

        mVideoView.setVideoPath(mVideoPath);

        mVideoView.setOnErrorListener(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnVideoSizeChangedListener(this);

        mVideoView.requestFocus();
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

        int screenWidth = wm.getDefaultDisplay().getWidth();
        resize(screenWidth);
//        mVideoView.start();
//        mBufferingIndicator.setVisibility(View.VISIBLE);
    }


    @Override
    public void onCompletion(IMediaPlayer mp) {
        Log.d(TAG, "onCompletion");
        mIsCompleted = true;
//        mBufferingIndicator.setVisibility(View.GONE);
        mVideoView.start();
    }

    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onError what=" + what + ", extra=" + extra);
        if (what == -10000) {
            if (extra == PlayerCode.EXTRA_CODE_INVALID_URI || extra == PlayerCode.EXTRA_CODE_EOF) {
//                if (mBufferingIndicator != null)
//                    mBufferingIndicator.setVisibility(View.GONE);

                return true;
            }
            if (mIsCompleted && extra == PlayerCode.EXTRA_CODE_EMPTY_PLAYLIST) {
                Log.d(TAG, "mVideoView reconnect!!!");
                mVideoView.removeCallbacks(mVideoReconnect);
                mVideoReconnect = new Runnable() {
                    @Override
                    public void run() {
                        mVideoView.setVideoPath(mVideoPath);
                    }
                };
                mVideoView.postDelayed(mVideoReconnect, mReqDelayMills);
                mReqDelayMills += 200;
            } else if (extra == PlayerCode.EXTRA_CODE_404_NOT_FOUND) {
                // NO ts exist
//                if (mBufferingIndicator != null)
//                    mBufferingIndicator.setVisibility(View.GONE);
            } else if (extra == PlayerCode.EXTRA_CODE_IO_ERROR) {
                zhibozhuantai.setText("直播未开始");
                // NO rtmp stream exist
//                if (mBufferingIndicator != null)
//                    mBufferingIndicator.setVisibility(View.GONE);
            }
        }
        // return true means you handle the onError, hence System wouldn't handle it again(popup a dialog).
        return true;
    }

    @Override
    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onInfo what=" + what + ", extra=" + extra);


        if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
            Log.i(TAG, "onInfo: (MEDIA_INFO_BUFFERING_START)");
//            if (mBufferingIndicator != null)
//                mBufferingIndicator.setVisibility(View.VISIBLE);
        } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
            Log.i(TAG, "onInfo: (MEDIA_INFO_BUFFERING_END)");
//            if (mBufferingIndicator != null)
//                mBufferingIndicator.setVisibility(View.GONE);
        } else if (what == IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START) {
            Toast.makeText(  getActivity(), "Audio Start", Toast.LENGTH_LONG).show();
            Log.i(TAG, "duration:" + mVideoView.getDuration());
        } else if (what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
            Toast.makeText(  getActivity(), "Video Start", Toast.LENGTH_LONG).show();
            Log.i(TAG, "duration:" + mVideoView.getDuration());
        }
        return true;
    }

    @Override
    public void onPrepared(IMediaPlayer mp) {
        Log.d(TAG, "onPrepared");
        int videoHeight = mp.getVideoHeight();
        int videoWidth = mp.getVideoWidth();

//        mBufferingIndicator.setVisibility(View.GONE);
        mReqDelayMills = REQ_DELAY_MILLS;
    }

    @Override
    public void onResume() {
        super.onResume();
        mReqDelayMills = REQ_DELAY_MILLS;
        if (mVideoView != null && !mIsLiveStream && mLastPosition != 0) {
            mVideoView.seekTo(mLastPosition);
            mVideoView.start();
        }
    }

    @Override
    public void onPause() {
        if (mVideoView != null) {
            mLastPosition = mVideoView.getCurrentPosition();
            mVideoView.pause();
        }
        super.onPause();
        getActivity(). getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int width, int height, int sarNum, int sarDen) {
        Log.i(TAG, "onVideoSizeChanged " + iMediaPlayer.getVideoWidth() + "x" + iMediaPlayer.getVideoHeight() + ",width:" + width + ",height:" + height + ",sarDen:" + sarDen + ",sarNum:" + sarNum);


        int localWight = height;
//        if (width > height) {
//            // land video
//            getActivity(). setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//            mScreenSize = Util.getResolution(getActivity());
//        } else {
            // port video
           // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
          //  mScreenSize = Util.getResolution(getActivity());
//        }

//        if (width < mScreenSize.first) {
//            height = mScreenSize.first * height / width;
//            width = mScreenSize.first;
//        }
//
//        if (width * height < mScreenSize.first * mScreenSize.second) {
//            width = mScreenSize.second * width / height;
//            height = mScreenSize.second;
//        }

//        resize(localWight);

    }

    private void resize(int localWight) {
        int height;
        height  = localWight *9/16;
        wAndH[0] = localWight;
        wAndH[1] = height;

        mLayoutParams = mAspectLayout.getLayoutParams();
        mLayoutParams.width = localWight;
        mLayoutParams.height = height;
        mAspectLayout.setLayoutParams(mLayoutParams);
        mVideoView.setLayoutParams(mLayoutParams);
        fullscrean.setVisibility(View.VISIBLE);
    }


    public void fullScreen() {
        //设置无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        Bundle bundle = new Bundle();
        bundle.putBoolean("isfullscreen", isfullscreen);
        EventBus.getDefault().post(EventBusC.getInstance(EventBusC.ZHIBOFULLSCREEN, bundle));
        if (!isfullscreen) {
            isfullscreen = true;

            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mAspectLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            mVideoView.setLayoutParams(layoutParams);
        } else {
            isfullscreen = false;
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            mAspectLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, wAndH[1]));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, wAndH[1]);
//            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mVideoView.setLayoutParams(layoutParams);

        }

    }


}
