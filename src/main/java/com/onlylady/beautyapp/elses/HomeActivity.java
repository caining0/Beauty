/*
package com.onlylady.beauty.elses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.onlylady.beauty.R;
import com.onlylady.beauty.activitys.DownloadActivity;
import com.onlylady.beauty.activitys.LeShi.PlayActivity;
import com.onlylady.beauty.activitys.LeShi.PlayNoSkinActivity;
import com.onlylady.beauty.elses.utils.LetvParamsUtils;

public class HomeActivity extends Activity implements OnClickListener {

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    // 测试用的id

    String liveId = "201504213000012";// "201504213000012";//"201501193000003";//
    // String actvieId = "A2015110300068";
    // String actvieId = "A2015103000338";
    // String actvieId = "A2015111700482";

//     String uuid = "cd5f283012";
//     String vuid = "5eda29368d";
//     String uuid = "hy07q1az4m";
//     String vuid = "4c219dad00";
    // String uuid = "2b686d84e3";
    // String vuid = "15d2678091";

    // String uuid = "487c884e76";
    // String vuid = "e5a4fb751e";


//     String uuid = "cd5f283012";
//     String vuid = "323dd5d802";
//     String uuid = "7a4f55c18a";
//     String vuid = "769312c218";
     String uuid = "603143efd0";
     String vuid = "2d89a1d5c7";
    // String uuid = "487c884e76";
    // String vuid ="e5a4fb751e";
    // String uuid = "cd5f283012";
    // String vuid = "37eb6a2bb7";
    // 广告
    // String uuid = "487c884e76";
    // String vuid = "e5a4fb751e";
    // String uuid = "2b686d84e3";
    // String vuid = "15d2678091";
    // 长视频
    // String uuid = "hy07q1az4m";
    // String vuid ="4c219dad00";
    // String uuid = "mxcrc5vyuw";
    // String vuid ="6dd089affa";
    // String vuid ="068d0e0c06";
    // String liveId = "201507153000063";//
    // "201504213000012";//"201501193000003";//
    // "201412183000004";
    // private NativeCrashHandler crashHandler;
    // String actvieId = "A2015110300068";
    // String actvieId = "A2015103000338";
    // String actvieId = "A2015110200292";
    // String actvieId = "A2015111600547";
    // String actvieId = "A2015111700482";
    String actvieId = "A2015090900083";
    // String actvieId = "A2015122300313";
    // String actvieId = "A2015122300644";
    // String actvieId = "A2015121400555";
    private String streamId = "152915-live-xxx123";
    // String actvieId = "A2015111800608";
    // String actvieId = "A2015110300122";
    // String actvieId = "A2015110300068";

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    private EditText etUUID;
    private EditText etVUID;
    private EditText etLiveId;
    private EditText etActionId;
    private RadioButton rb1;
    private RadioButton rb2;

    private RadioButton rb3;
    private RadioButton rb4;
    private CheckBox ckHasSkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClickEvent();
    }

    private void initClickEvent() {
        findViewById(R.id.live).setOnClickListener(this);
        findViewById(R.id.vod).setOnClickListener(this);
        findViewById(R.id.downoadList_btn).setOnClickListener(this);
        findViewById(R.id.activity_live_btn).setOnClickListener(this);
    }

    private void initView() {
        etLiveId = (EditText) findViewById(R.id.et_liveID);
        etUUID = (EditText) findViewById(R.id.et_uuid);
        etVUID = (EditText) findViewById(R.id.et_vuid);
        etUUID.setText(uuid);
        etVUID.setText(vuid);
        etLiveId.setText(liveId);
        rb1 = (RadioButton) findViewById(R.id.rb_1);
        rb2 = (RadioButton) findViewById(R.id.rb_2);
        rb1.setChecked(true);

        rb3 = (RadioButton) findViewById(R.id.rb_3);
        rb4 = (RadioButton) findViewById(R.id.rb_4);
        rb3.setChecked(true);
        ckHasSkin = (CheckBox) findViewById(R.id.ck_has_skin);
        etActionId = (EditText) findViewById(R.id.et_actionID);
        etActionId.setText(actvieId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.live:
            startLeCloudLive();
            break;
        case R.id.vod:
            startLecloudVod();
            break;
        case R.id.downoadList_btn:
            Intent intent = new Intent(HomeActivity.this, DownloadActivity.class);
            startActivity(intent);
            break;
        case R.id.activity_live_btn:
            startLeCloudActionLive();
            break;
        default:
            break;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////



}
*/
