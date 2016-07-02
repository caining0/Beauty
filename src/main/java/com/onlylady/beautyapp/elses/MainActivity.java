/*
package com.onlylady.beauty.elses;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.gson.reflect.TypeToken;
import com.lecloud.common.base.util.Logger;
import com.lecloud.common.cde.LeCloud;
import com.onlylady.beauty.R;
import com.onlylady.beauty.beans.ProgressBean;
import com.onlylady.beauty.beans.UpdateBean;
import com.onlylady.beauty.beans.VideoListBean;
import com.onlylady.beauty.engines.BaseEngine;
import com.onlylady.beauty.urls.UrlHolder;
import com.onlylady.beauty.utils.ToastUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.container)
    LinearLayout container;
    private EditText etUUID;
    private EditText etVUID;
    private EditText etLiveId;
    private EditText etActiveId;
    private RadioButton rb1;
    private RadioButton rb2;

    private String muuid;
    private String mvuid;


    String liveId = "201507153000063";//"201504213000012";//"201501193000003";// "201412183000004";
    // private NativeCrashHandler crashHandler;

    String actvieId = "A2015110300122";
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("MainActivity", "onCreate");
        LeCloud.init(getApplicationContext());
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        muuid = getString(R.string.uuid);
        mvuid = getString(R.string.vuid);
        initView();
        Button live = (Button) findViewById(R.id.live);
        Button vod = (Button) findViewById(R.id.vod);
        Button downlaodList = (Button) findViewById(R.id.downoadList_btn);
        live.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LiveActivity.class);
//				Intent intent = new Intent(MainActivity.this, MutlLiveActivity.class);
                intent.putExtra("liveID", etLiveId.getText().toString().trim());
                if (rb2.isChecked()) {
                    intent.putExtra("isHLS", true);
                } else {
                    intent.putExtra("isHLS", false);
                }
                startActivity(intent);
            }
        });

        vod.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VODActivity.class);
                intent.putExtra("uu", etUUID.getText().toString().trim());
                intent.putExtra("vu", etVUID.getText().toString().trim());
                startActivity(intent);
            }
        });
        downlaodList.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);
            }
        });

        Button activeLive = (Button) findViewById(R.id.activity_live_btn);
        activeLive.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MutlLiveActivity.class);
                intent.putExtra("activityId", etActiveId.getText().toString().trim());
                startActivity(intent);
            }
        });
        findViewById(R.id.lushipin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GetVideoActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.shangchuan).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mIntent.setType("file*/
/*");

                startActivityForResult(mIntent, 10000);
            }
        });
        findViewById(R.id.rili).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(mIntent);
            }
        });
        initdatas();
    }

    private void initdatas() {


        Map<String, String> map = new HashMap<String, String>();
        String videoList = UrlHolder.getInstance().getVideoList(map,1);
        Log.i("info", videoList);
        BaseEngine.getInstance().getTPost(videoList, map, new TypeToken<VideoListBean>() {
        }.getType(), new BaseEngine.CallbackForT<VideoListBean>() {
            @Override
            public void finish(VideoListBean bean) {
                Log.i("info", bean.toString());
                ToastUtils.show(getApplicationContext(), bean.getMessage());
            }

            @Override
            public void finish(List<VideoListBean> listT) {
                Log.i("info", listT.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10000) {

                Uri uri = data.getData();
                try {
                    file = new File(new URI(uri.toString()));
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("video_name", file.getName());
//                    map.put("file_size", file.length()+"");
//                    map.put("client_ip", PhoneInfo.getIp(getApplicationContext()));
                    String url = UrlHolder.getInstance().getvideoUploadInit(map);
                    BaseEngine.getInstance().getTPost(url, map, new TypeToken<UpdateBean>() {
                    }.getType(), new BaseEngine.CallbackForT<UpdateBean>() {
                        @Override
                        public void finish(UpdateBean bean) {

                            getprogress(bean);
                        }

                        private void getprogress(final UpdateBean bean) {
                            final Timer timer = new Timer();
                            updatefile(bean, timer);
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {      // UI thread
                                        @Override
                                        public void run() {
                                            BaseEngine.getInstance().getTPost(bean.getData().getProgress_url(), null, new TypeToken<ProgressBean>() {
                                            }.getType(), new BaseEngine.CallbackForT<ProgressBean>() {
                                                @Override
                                                public void finish(ProgressBean bean) {
                                                    Log.i("info", "进度-------------》" + bean.getData().getUpload_size() + "--------" + bean.getData().getTotal_size());
                                                    if (bean.getData().getTotal_size() == 0) {
                                                        timer.cancel();
                                                    }

                                                }

                                                @Override
                                                public void finish(List<ProgressBean> listT) {

                                                }
                                            });
                                        }
                                    });
                                }
                            }, 1000, 1000);

                        }

                        private void updatefile(UpdateBean bean, final Timer timer) {
                            BaseEngine.getInstance().setFilePost(bean.getData().getUpload_url(), file, new BaseEngine.CallbackForT<String>() {
                                @Override
                                public void finish(String bean) {
                                    Log.i("info", bean + "timercalcel");
                                    timer.cancel();
                                }

                                @Override
                                public void finish(List<String> listT) {

                                }
                            });

                        }

                        @Override
                        public void finish(List<UpdateBean> listT) {

                        }
                    });

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private void initView() {
        etLiveId = (EditText) findViewById(R.id.et_liveID);
        etUUID = (EditText) findViewById(R.id.et_uuid);
        etVUID = (EditText) findViewById(R.id.et_vuid);
        etUUID.setText(muuid);
        etVUID.setText(mvuid);
        etLiveId.setText(liveId);
        rb1 = (RadioButton) findViewById(R.id.rb_1);
        rb2 = (RadioButton) findViewById(R.id.rb_2);
        rb1.setChecked(true);
        etActiveId = (EditText) findViewById(R.id.et_activityID);
        etActiveId.setText(actvieId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Logger.e("MainActivity", "onNewIntent");
        super.onNewIntent(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//		DownloadCenter.destoryDownloadService(this);
        LeCloud.destory();
    }

}
*/
