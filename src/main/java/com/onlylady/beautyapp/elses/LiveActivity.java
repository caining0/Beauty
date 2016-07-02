/*
package com.onlylady.beauty.elses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.lecloud.common.base.util.LogUtils;
import com.lecloud.common.base.util.Logger;
import com.lecloud.skin.PlayerStateCallback;
import com.lecloud.skin.live.LivePlayCenter;
import com.letvcloud.sdk.log.FetchLogLoader;
import com.onlylady.beauty.R;
import com.onlylady.beauty.engines.BaseEngine;
import com.onlylady.beauty.beans.LiveBean;
import com.onlylady.beauty.urls.UrlHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveActivity extends Activity {
	private RelativeLayout mPlayerLayoutView;
	private LivePlayCenter mPlayerView;
	private EditText testEditText;
//	private Button testButton;

//	private Button mBtExit;
	private Button mBtShowLog;
	private Button fetchLog;

	private static String Defualt_LIVEID = "201501193000003";//"201412083000001";
	private boolean isHLS;
	private boolean isBackgroud = false;
	@Override
	protected void onResume() {
		super.onResume();
		if (this.mPlayerView != null) {
			if (isBackgroud) {
				if(mPlayerView.getCurrentPlayState() == PlayerStateCallback.PLAYER_VIDEO_PAUSE){
	        		this.mPlayerView.resumeVideo();
	        	}else{
	        		Logger.e("LiveActivity", "已回收，重新请求播放");
	        		mPlayerView.playVideo(testEditText.getText() + "", "测试频道");
	        	}
			}
//			
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (this.mPlayerView != null) {
			this.mPlayerView.pauseVideo();
			isBackgroud = true;
		}
	}

*/
/*	public interface Callbackcnn{
		void finish();
	}
	private Callbackcnn callbackcnn;

	public void setCallbackcnn(Callbackcnn callbackcnn) {
		this.callbackcnn = callbackcnn;
	}*//*


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.vedio_layout);
		Intent intent = getIntent();
		Defualt_LIVEID=intent.getStringExtra("liveID");
		isHLS=intent.getBooleanExtra("isHLS", false);
		
		this.mPlayerLayoutView = (RelativeLayout) this
				.findViewById(R.id.layout_player);

		mPlayerView = new LivePlayCenter(this,true,isHLS);
		this.mPlayerLayoutView.addView(this.mPlayerView.getPlayerView());

		this.testEditText = (EditText) this.findViewById(R.id.testET);
		this.testEditText.setText(Defualt_LIVEID);
//		this.testButton = (Button) this.findViewById(R.id.testBtn);
//		this.mBtExit = (Button) this.findViewById(R.id.bt_exit);
		this.mBtShowLog = (Button) this.findViewById(R.id.bt_showlog);
		fetchLog = (Button) this.findViewById(R.id.bt_fetchlog);
		fetchLog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FetchLogLoader.getInstance(LiveActivity.this).fetchLog();
			}
		});
		this.mBtShowLog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startLogActivity();
			}
		});
//		mPlayerView.playVideo(testEditText.getText() + "", "测试频道");
//		mPlayerView.playVideo(testEditText.getText() + "", "测试频道",false);
		
		
		initdatas();
	}
	private void initdatas() {
		Map<String, String> map = new HashMap<String, String>();
		String livevideoList = UrlHolder.getInstance().getLiveVideoList(map);
		 BaseEngine.getInstance().getTGet(livevideoList, map,new TypeToken<List<LiveBean>>(){}.getType(), new BaseEngine.CallbackForT<LiveBean>() {
			@Override
			public void finish(LiveBean bean) {
				Log.i("info", bean.toString());
			}

			@Override
			public void finish(List<LiveBean> listT) {
				LiveBean liveBean = listT.get(1);
				mPlayerView.playVideo(liveBean.getActivityId() + "", liveBean.getActivityName());

				Log.i("info", liveBean.getActivityId());
			}
		} );
	}


	private void startLogActivity() {
		Intent intent = new Intent(this, LogInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("logInfo", LogUtils.getLog(this));
		intent.putExtras(bundle);
		this.startActivity(intent);
	}
	


	@Override
	protected void onDestroy() {
		this.mPlayerView.destroyVideo();
		this.mPlayerLayoutView.removeAllViews();
		super.onDestroy();
		isBackgroud = false;
		LogUtils.clearLog();
		
	}
}
*/
