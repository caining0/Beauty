package com.HBuilder.integrate.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.H5PlusPlugin.PGPlugintest;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.activitys.BaseActivity;
import com.onlylady.beautyapp.activitys.LoginActivity;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.elses.onDoubleClick;
import com.onlylady.beautyapp.exlib.pili.player.PiliplaerFagment;
import com.onlylady.beautyapp.utils.AndroidBug5497Workaround;
import com.onlylady.beautyapp.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import io.dcloud.EntryProxy;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IApp.IAppStatusListener;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.ICore.ICoreStatusListener;
import io.dcloud.common.DHInterface.IOnCreateSplashView;
import io.dcloud.common.DHInterface.ISysEventListener.SysEventType;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.IWebviewStateListener;
import io.dcloud.common.util.JSUtil;
import io.dcloud.feature.internal.sdk.SDK;

/**
 * 本demo为以WebApp方式集成5+ sdk，
 */
public class SDK_WebApp extends BaseActivity {
    //    public static   int loadcount = 0;
    boolean doHardAcc = true;
    EntryProxy mEntryProxy = null;
    public static FrameLayout layout;
    public static Activity context;
    private View detailGoBack;
    private View doubleClickView;
    private PowerManager.WakeLock wakeLock;
    public static  boolean isFullscreen;
    private WebappModeListener wm;
    private FrameLayout frameLayout;
//    public static boolean newHgith =true;
    private   AndroidBug5497Workaround assistActivity;
    private PiliplaerFagment plagerFragment = null;

    public void fullScreen(boolean isfullscreen) {
        //设置全屏
        isFullscreen = !isfullscreen;
        doubleClickView.setVisibility(View.GONE);
        if (!isfullscreen) {
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//                int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//                SDK_WebApp.this.getWindow().setFlags(flag, flag);
                assistActivity.setBeginornot(false);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                //横屏

                Toast.makeText(getApplicationContext(), "双击可退出全屏", Toast.LENGTH_LONG).show();
                doubleClickView.setVisibility(View.VISIBLE);
                detailGoBack.setVisibility(View.GONE);
            }
        } else {
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
//                int flag = WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
//                SDK_WebApp.this.getWindow().setFlags(flag, flag);
                detailGoBack.setVisibility(View.VISIBLE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }


    public void onEvent(EventBusC c) {
        if (c.getFrom() == EventBusC.FULLSCREEN) {
            fullScreen(c.getBundle().getBoolean("fullscreen"));
        } else if (c.getFrom() == EventBusC.ZHIBOFULLSCREEN) {
            if (c.getBundle().getBoolean("isfullscreen")) {
                detailGoBack.setVisibility(View.VISIBLE);
            } else {
                detailGoBack.setVisibility(View.GONE);
            }
        } else if (c.getFrom() == EventBusC.JSLOGIN) {//插件调用
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setAction("main");
            startActivityForResult(intent, Configs.JSLOGIN);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "MyTag");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        SDK_WebApp.this.getWindow().setFlags(flag, flag);
        EventBus.getDefault().register(this);//并不能所有界面注册
        if (mEntryProxy == null) {
            setContentView(R.layout.activity_web);
            layout = (FrameLayout) findViewById(R.id.framelayout);
            assistActivity = AndroidBug5497Workaround.assistActivity(this);

            frameLayout = (FrameLayout) findViewById(R.id.commentcontainner);
            initview();
            if (getIntent().getBooleanExtra("live", false)) {
                initFragment();
            }
            // 创建5+内核运行事件监听
            wm = new WebappModeListener(this, frameLayout, (ProgressBar) findViewById(R.id.my_progress));
            // 初始化5+内核
            mEntryProxy = EntryProxy.init(this, wm);
            // 启动5+内核
            mEntryProxy.onCreate(this, savedInstanceState, SDK.IntegratedMode.WEBAPP, null);
            context = this;
            setonDoubleClicker();
        }
    }

    private void setonDoubleClicker() {
        doubleClickView = findViewById(R.id.dobleclick);
        doubleClickView.setOnClickListener(new onDoubleClick() {
            @Override
            public void DoubleClick() {
                LogUtils.Log("double");
                detailGoBack.performClick();
            }
        });
    }

    private void initview() {
//        ClickUtils.getInstance().setClosedWindowLisener(this, R.id.detailgoback);

        detailGoBack = findViewById(R.id.detailgoback);
        detailGoBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // LogUtils.Log(wm.getWebview().canGoBack());
                if (isFullscreen) {
                    onBackPressed();
                 //   wm.getWebview().executeScript("javascript:requestExitVideoFullscreen()");
//                    if (Build.VERSION.SDK_INT != Build.VERSION_CODES.KITKAT) {
//                        wm.getWebview().executeScript("javascript:requestExitVideoFullscreen()");
//                    LogUtils.Log("javascript:requestExitVideoFullscreen()");
//                    } else {//4.4 有问题，视频会卡住
//                        SDK_WebApp.this.finish();
//                    }
                } else {
                    SDK_WebApp.this.finish();
                }
            }
        });

    }

    @Override
    public boolean useEventbus() {
        return false;
    }

    @Override
    public void createview() {

    }

    @Override
    public void initlisener() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        findViewById(R.id.fragmentcontainner).setVisibility(View.VISIBLE);
         plagerFragment = new PiliplaerFagment();//new PlagerFragment();从乐视转
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentcontainner, plagerFragment).commit();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return mEntryProxy.onActivityExecute(this, SysEventType.onCreateOptionMenu, menu);
//    }

    @Override
    public void onPause() {
//        ToastUtils.show(getApplicationContext(), "onPause");
        super.onPause();
        if (getIntent().getBooleanExtra("live", false)) {
            wakeLock.release();
        }

        mEntryProxy.onPause(this);
    }

    @Override
    public void onResume() {
        if (getIntent().getBooleanExtra("live", false)) {
            wakeLock.acquire();
        }
        super.onResume();
        mEntryProxy.onResume(this);
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getFlags() != 0x10600000) {// 非点击icon调用activity时才调用newintent事件
            mEntryProxy.onNewIntent(this, intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ToastUtils.show(getApplicationContext(),"fuck");
        EventBus.getDefault().unregister(this);
        mEntryProxy.onStop(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.Log("onkeydown");
        boolean _ret = mEntryProxy.onActivityExecute(this, SysEventType.onKeyDown, new Object[]{keyCode, event});
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            if (plagerFragment!=null&&plagerFragment.isfullscreen){
//                plagerFragment.fullScreen();
//            }else {
                finish();//cnn add
//            }
        }
        return _ret ? _ret : super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean _ret = mEntryProxy.onActivityExecute(this, SysEventType.onKeyUp, new Object[]{keyCode, event});
        return _ret ? _ret : super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        boolean _ret = mEntryProxy.onActivityExecute(this, SysEventType.onKeyLongPress, new Object[]{keyCode, event});
        return _ret ? _ret : super.onKeyLongPress(keyCode, event);
    }

    public void onConfigurationChanged(Configuration newConfig) {
//        ToastUtils.show(getApplicationContext(), "onConfigurationChanged");
        try {
            int temp = this.getResources().getConfiguration().orientation;
            if (mEntryProxy != null) {
                mEntryProxy.onConfigurationChanged(this, temp);
            }
            super.onConfigurationChanged(newConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ToastUtils.show(getApplicationContext(), "onActivityResult");
        if (resultCode == Activity.RESULT_OK && requestCode == Configs.JSLOGIN) {
            JSONObject retJSONObj = new JSONObject();
            PGPlugintest.getJsonUserInfo(retJSONObj);
            JSUtil.execCallback(PGPlugintest.pWebview, PGPlugintest.CallBackID, retJSONObj, JSUtil.OK, false);
            // TODO: 16/3/18 回调给js层，登陆成功
        } else {
            mEntryProxy.onActivityExecute(this, SysEventType.onActivityResult, new Object[]{requestCode, resultCode, data});
        }

    }
}

class WebappModeListener implements ICoreStatusListener, IOnCreateSplashView {
    Activity activity;
    View splashView = null;
    ViewGroup rootView;
    public static IApp app = null;
    ProgressBar pd = null;
    private IWebview mwebview;

    public IWebview getWebview() {
        return mwebview;
    }

    public WebappModeListener(Activity activity, ViewGroup rootView, ProgressBar progressBar) {
        this.activity = activity;
        this.rootView = rootView;
        this.pd = progressBar;
    }


    /**
     * 5+内核初始化完成时触发
     */
    @Override
    public void onCoreInitEnd(ICore coreHandler) {
//        ToastUtils.show(activity, "onCoreInitEnd"+SDK_WebApp.loadcount);
        // 表示Webapp的路径在 file:///android_asset/apps/HelloH5
        String appBasePath = "/apps/H5Plugin";
        JSONObject json = new JSONObject();
        try {
            json.put("id", "" + activity.getIntent().getStringExtra("id"));
            json.put("type", "" + activity.getIntent().getStringExtra("type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String args = json.toString();
        app = SDK.startWebApp(activity, appBasePath, args, new IWebviewStateListener() {
            // 设置Webview事件监听，可在监监听内获取WebIvew加载内容的进度
            @Override
            public Object onCallBack(int pType, Object pArgs) {
                switch (pType) {
                    case IWebviewStateListener.ON_WEBVIEW_READY:
                        IWebview webview = (IWebview) pArgs;
                        mwebview = webview;
                        View view = (webview).obtainApp().obtainWebAppRootView().obtainMainView();
                        view.setVisibility(View.INVISIBLE);
                        rootView.addView(view, 0);
                        break;
                    case IWebviewStateListener.ON_PAGE_STARTED:
                        break;
                    case IWebviewStateListener.ON_PROGRESS_CHANGED:

                        if (pd != null) {
                            pd.setProgress((Integer) pArgs);
                        }
                        break;
                    case IWebviewStateListener.ON_PAGE_FINISHED:
                        if (pd != null) {
                            pd.setVisibility(View.GONE);
                            pd = null;
                        }
                        app.obtainWebAppRootView().obtainMainView().setVisibility(View.VISIBLE);

                        break;
                }
                return null;
            }
        }, this);
//        webview = (IWebview) app.obtainWebAppRootView().obtainMainView();


        app.setIAppStatusListener(new IAppStatusListener() {
            // 设置APP运行事件监听
            @Override
            public boolean onStop() {
//                ToastUtils.show(activity, "oIAppStatusListenernStop");
                LogUtils.Log("onstop");
                rootView.removeView(app.obtainWebAppRootView().obtainMainView());
                return false;
            }

            @Override
            public void onStart() {
                LogUtils.Log("start");
            }

            @Override
            public void onPause(IApp arg0, IApp arg1) {
                LogUtils.Log("onpause");
//                ToastUtils.show(activity, "IAppStatusListeneronPause");
            }
        });
    }

    @Override
    public void onCoreReady(ICore coreHandler) {
        // 初始化5+ SDK，
        // 5+SDK的其他接口需要在SDK初始化后才能調用
        SDK.initSDK(coreHandler);
        // 设置当前应用可使用的5+ API
        SDK.requestAllFeature();
    }

    @Override
    public boolean onCoreStop() {
        // 当返回false时候回关闭activity
//        ToastUtils.show(activity, "onCoreStop");
        return false;
    }

    // 在Widget集成时如果不需要显示splash页面可按照如下步骤操作
    // 1 删除onCreateSplash方法内的代码
    // 2 将5+mainView添加到rootview时将页面设置为不可见
    // 3 在onCloseSplash方法中将5+mainView设置为可见
    // 4 修改androidmanifest.xml文件 将SDK_WebApp的主题设置为透明
    // 注意！
    // 如果不显示splash页面会造成用户点击后页面短时间内会没有变化，
    // 可能会给用户造成程序没响应的错觉，
    // 所以开发者需要对5+内核启动到5+应用页面显示之间的这段事件进行处理

    @Override
    public Object onCreateSplash(Context pContextWrapper) {
        splashView = new FrameLayout(activity);
        splashView.setBackgroundResource(R.mipmap.banner_750_1334);
        rootView.addView(splashView);
        return null;
    }

    @Override
    public void onCloseSplash() {
        rootView.removeView(splashView);
    }


}
