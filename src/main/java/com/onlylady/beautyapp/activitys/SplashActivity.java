package com.onlylady.beautyapp.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.reflect.TypeToken;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.beans.onlylady.Ads;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.MFileUtils;
import com.onlylady.beautyapp.utils.SharedPrefeUtils;
import com.onlylady.beautyapp.utils.StartActivityUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class SplashActivity extends BaseActivity implements SurfaceHolder.Callback{
    private boolean firstload = true;
    private Handler handler = new Handler();
    private int delaytime=1000;
    private Ads.DataBean.AdsBean adS;
    private  boolean cancel = false;
    private ImageView iamgead;
//    private ImageView initclosebutton;
    private RelativeLayout adContent;
    private SurfaceView surfaceView;
    private MediaPlayer player;
    private Ads.DataBean.AdsBean nextAD;
    public static String HPL = "hpl";
    public static String VAL = "val";
    private ImageView faceview;

    @Override
    public boolean useEventbus() {
        return false;
    }

    @Override
    public void createview() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_splash);
        this.adContent = (RelativeLayout)findViewById(R.id.ad_content);
        this.surfaceView = (SurfaceView)findViewById(R.id.ad_surfaceview);
        this.faceview = (ImageView) findViewById(R.id.faceview);
    }

    @Override
    public void initlisener() {

    }

    @Override
    public void initData() {
        firstload = SharedPrefeUtils.getSettings(getApplicationContext(), "firstload", true);
        if (firstload) {
            SharedPrefeUtils.saveSettings(getApplicationContext(), "firstload", false);
            loginGuide();
        } else {
            loginHome();

        }
        loadAD();
    }

    private void loginHome() {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
              //  faceview.setVisibility(View.GONE);
                if (adS == null || firstload) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } else if (adS != null) {
                    faceview.setVisibility(View.GONE);
                    if (adS.getHpl().endsWith(".mp4")) {
                        showVideo();
                    } else {
                        showAD();
                    }
                }

            }
        }, delaytime);
    }

    private void loginGuide() {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent1 = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent1);
                Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, delaytime);
    }
    private void loadAD(){
        if(getIntent() == null)
            return;
        if(!(TextUtils.isEmpty(SharedPrefeUtils.getSettings(this, HPL))||TextUtils.isEmpty(SharedPrefeUtils.getSettings(this, VAL)))){
            adS = new Ads.DataBean.AdsBean();
            adS.setHpl(SharedPrefeUtils.getSettings(this, HPL));
            adS.setVal(SharedPrefeUtils.getSettings(this, VAL));
        }
        BaseEngine.getInstance().getTGet(Configs.getDOMAINV110(), UrlsHolder.getInstance().getParams1106("2"), new TypeToken<Ads>() {
        }.getType(), new BaseEngine.CallbackForT<Ads>() {

            @Override
            public void finish(Ads bean) {
                if (bean.getData() == null || bean.getData().getAds().size() == 0) {
                    return;
                }
                if (nextAD == null) {
                    nextAD = bean.getData().getAds().get(0);
                    saveAd2Db();
                }
            }

            @Override
            public void finish(List<Ads> ads) {

            }
        });
        BaseEngine.getInstance().getTGet(Configs.getDOMAINV110(), UrlsHolder.getInstance().getParams1106("4"), new TypeToken<Ads>() {
        }.getType(), new BaseEngine.CallbackForT<Ads>() {

            @Override
            public void finish(Ads bean) {
                if (bean.getData()==null||bean.getData().getAds().size()==0) {
                    return;
                }
                nextAD = bean.getData().getAds().get(0);
                saveVedio();
            }

            @Override
            public void finish(List<Ads> ads) {

            }
        });
//        initclosebutton = (ImageView) findViewById(R.id.initclosebutton);
//        initclosebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//                SplashActivity.this.finish();
//            }
//        });
        ImageView vedioclosebtn = (ImageView)findViewById(R.id.vedioclosebutton);
        vedioclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                SplashActivity.this.finish();
            }
        });
        if (adS == null)
            return;
        if(adS.getHpl().endsWith("mp4")){
            File file = new File(adS.getHpl());
            if(!file.exists()) {
                adS = null;
            }else{
                initAdMedia();
            }
        }
    }

    private void showAD(){
        iamgead = (ImageView) findViewById(R.id.iamgead);
        adContent.setVisibility(View.VISIBLE);
        iamgead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(adS.getVal()))
                    return;
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                cancel=true;
                StartActivityUtils.startADActivity(SplashActivity.this, adS.getVal());
                SplashActivity.this.finish();
            }
        });
        Glide.with(SplashActivity.this).load(adS.getHpl()).crossFade(500).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iamgead);
//        alphaAnim(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!cancel) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    SplashActivity.this.finish();
                }
            }
        }, 4000);
    }

    private void saveVedio(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String[] arrar = nextAD.getHpl().split("/");
            final String filename = arrar[arrar.length-1];
            if(MFileUtils.getInstance().haveFile(filename)){
                nextAD.setHpl(MFileUtils.getInstance().getFilePath(filename));
                saveAd2Db();
                return;
            }
            BaseEngine.getInstance().getResponse(nextAD.getHpl(), new BaseEngine.CallbackForResponse() {
                @Override
                public void finish(byte[] response) {
                    String filepath = MFileUtils.getInstance().saveData2File(response, filename);
                    nextAD.setHpl(filepath);
                    saveAd2Db();
                }
            });

        }
    }
    private void showVideo(){
        surfaceView.setVisibility(View.VISIBLE);
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) {
                    player.stop();
                    player.release();
                    player = null;
                }
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                StartActivityUtils.startADActivity(SplashActivity.this, adS.getVal());
                SplashActivity.this.finish();
            }
        });
        //alphaAnim(false);

    }

    private void initAdMedia(){
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //  holder.setFixedSize(surfaceView.getWidth(),surfaceView.getHeight());
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDisplay(holder);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                SplashActivity.this.finish();
            }
        });
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                surfaceView.setBackgroundColor(Color.TRANSPARENT);
                player.start();
            }
        });
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                SplashActivity.this.finish();
                return false;
            }
        });
        try {
            player.setDataSource(adS.getHpl());
            player.prepare();
        }catch (IOException e){
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            SplashActivity.this.finish();
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
    private void saveAd2Db(){
//        try {
//            BassApp.getInstance().getDaoSession(context).getAdsDao().queryBuilder().buildDelete().executeDeleteWithoutDetachingEntities();
//        }catch (NullPointerException n){}
//        BassApp.getInstance().getDaoSession(context).getAdsDao().insert(nextAD);
        SharedPrefeUtils.saveSettings(this,HPL,nextAD.getHpl());
        SharedPrefeUtils.saveSettings(this,VAL,nextAD.getVal());
    }
}
