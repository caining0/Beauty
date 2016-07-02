package com.onlylady.beautyapp.exlib;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends ActionBarActivity {
    private MovieRecorderView movieRV;
    private Button startBtn;
    private Button stopBtn;
    private MediaPlayer player;
    int position;

    public CameraActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initViews();
        initEvents();
        init();

    }

    private void init() {
        player = new MediaPlayer();

    }

    private void initViews() {
        movieRV = (MovieRecorderView) findViewById(R.id.moive_rv);
        //录制  
        startBtn = (Button) findViewById(R.id.start_btn);
        stopBtn = (Button) findViewById(R.id.stop_btn);
    }

    private List<String> list = new ArrayList<>();
    private int count = 0;

    private void initEvents() {
        //开始录制  
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieRV.record(new MovieRecorderView.OnRecordFinishListener() {
                    @Override
                    public void onbegining(final String filePath) {
                        list.add(filePath);
                        if (list.size() > 3) {
                            handler.sendEmptyMessage(1);
                        }
                    }
                });
            }
        });
        //停止录制  
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieRV.stop();
            }
        });


    }

    @Override
    protected void onPause() {
        //先判断是否正在播放  
        if (player.isPlaying()) {
            //如果正在播放我们就先保存这个播放位置  
            position = player.getCurrentPosition();
            player.stop();
        }
        super.onPause();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
//                RtmpPush.push(list.get(list.size() - 2), "rtmp://w.gslb.lecloud.com/live/20160113300072999?sign=8b1d7fb651c75c4be9d49dfa31838ba5&tm=20160113152549");
            }
        }
    };


}