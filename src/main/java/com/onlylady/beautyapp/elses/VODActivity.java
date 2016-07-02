/*
package com.onlylady.beauty.elses;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.lecloud.common.base.util.LogUtils;
import com.lecloud.common.base.util.Logger;
import com.lecloud.download.control.DownloadCenter;
import com.lecloud.skin.OnClickCallback;
import com.lecloud.skin.PlayerStateCallback;
import com.lecloud.skin.vod.VODPlayCenter;
import com.letvcloud.sdk.log.FetchLogLoader;
import com.onlylady.beauty.R;
import com.onlylady.beauty.beans.VideoListBean;
import com.onlylady.beauty.configs.Configs;
import com.onlylady.beauty.engines.BaseEngine;
import com.onlylady.beauty.urls.UrlHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VODActivity extends Activity {

	private RelativeLayout mPlayerLayoutView;
	private VODPlayCenter mPlayerView;
	private EditText testEditText;
	private Button testButton;
	private Button bt_showlog, fetchLog;
	private boolean isBackgroud = false;
//	private ListView listView;
*/
/*
	String uu = "";
	String vu = "";*//*

	private VideoListBean.DataEntity data ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Logger.e("VODActivity", "onCreate");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.vedio_layout);
		Intent intent = getIntent();
		data = (VideoListBean.DataEntity) intent.getSerializableExtra("data");
//		vu = intent.getStringExtra("vu");
		this.mPlayerLayoutView = (RelativeLayout) this.findViewById(R.id.layout_player);
//		this.listView = (ListView) findViewById(R.id.listview);

		// this.mPlayerView = VODPlayCenter.getInstance(this, true);
		this.mPlayerView = new VODPlayCenter(this, true);
		this.mPlayerLayoutView.addView(this.mPlayerView.getPlayerView());

		this.testEditText = (EditText) this.findViewById(R.id.testET);
		this.testButton = (Button) this.findViewById(R.id.testBtn);
		this.bt_showlog = (Button) this.findViewById(R.id.bt_showlog);
		this.fetchLog = (Button) this.findViewById(R.id.bt_fetchlog);
		this.fetchLog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FetchLogLoader.getInstance(VODActivity.this).fetchLog();
			}
		});

		this.bt_showlog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(VODActivity.this, LogInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("logInfo", LogUtils.getLog(VODActivity.this));
				intent.putExtras(bundle);
				VODActivity.this.startActivity(intent);
			}
		});
		findViewById(R.id.cutimage).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("video_id",data.getVideo_id());
				String url = UrlHolder.getInstance().getImageGet(map);
				Log.i("info", url);
				BaseEngine.getInstance().getStringPost(url, map, new BaseEngine.CallbackForT<String>() {
					@Override
					public void finish(String bean) {
						Log.i("info",bean);
					}

					@Override
					public void finish(List<String> listT) {
						Log.i("info",listT.toString());
					}
				});
			}
		});
		
		*/
/**
		 * 监听全屏半屏切换
		 *//*

		this.mPlayerView.setOnClickCallback(new OnClickCallback() {
			
			@Override
			public void onClick(int state) {
				if(state==OnClickCallback.ONCLICK_HALFSCREEN_IV){
					Log.i("VODActivity", "切换全屏");
				}else if(state==OnClickCallback.ONCLICK_FULLSCREEN_IV){
					Log.i("VODActivity", "切换半屏");
				}
				
			}
		});
		this.mPlayerView.setPlayerStateCallback(new PlayerStateCallback() {
			boolean lastSeek = true;

			@Override
			public void onStateChange(int state, Object... extra) {
				if (state == PlayerStateCallback.PLAYER_VIDEO_PAUSE) {
					Logger.e("onStateChange", "PLAYER_VIDEO_PAUSE position:" + mPlayerView.getCurrentPosition());
				} else if (state == PlayerStateCallback.PLAYER_VIDEO_PLAY) {
					Logger.e("onStateChange", "PLAYER_VIDEO_PLAY");
					// if(lastSeek){
					// mPlayerView.seekTo(12);
					// lastSeek=false;
					// }
				} else if (state == PlayerStateCallback.PLAYER_VIDEO_RESUME) {
					Logger.e("onStateChange", "PLAYER_VIDEO_RESUME");
				} else if (state == PlayerStateCallback.PLAYER_STOP) {
					Logger.e("onStateChange", "PLAYER_STOP position:" + mPlayerView.getCurrentPosition());
				}else if(state == PlayerStateCallback.PLAYER_ERROR){
					Logger.e("onStateChange", "PLAYER_ERROR" );
				}
			}
		});
		mPlayerView.bindDownload(DownloadCenter.getInstances(this));
		DownloadCenter.getInstances(this).allowShowMsg(false);
		this.mPlayerView.playVideo(Configs.UUID, data.getVideo_unique(), Configs.UID, Configs.KEY, data.getVideo_name(), true);// c34f821becb64978216a8765ccfff24e
//		mPlayerView.changeOrientation(Configuration.ORIENTATION_LANDSCAPE);

//		initdatas();
	}

	private void initdatas() {


		Map<String, String> map = new HashMap<String, String>();
		String videoList = UrlHolder.getInstance().getVideoList(map,1);
		Log.i("info", videoList);
		 BaseEngine.getInstance().getTPost(videoList, map,new TypeToken<VideoListBean>(){}.getType(), new BaseEngine.CallbackForT<VideoListBean>() {
			@Override
			public void finish(VideoListBean bean) {
				Log.i("info", bean.toString());
			}

			@Override
			public void finish(List<VideoListBean> listT) {
				Log.i("info", listT.toString());
			}
		});
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Logger.e("VODActivity", "onKeyUp:"+keyCode);
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (this.mPlayerView != null) {
			if (isBackgroud) {
				if (mPlayerView.getCurrentPlayState() == PlayerStateCallback.PLAYER_VIDEO_PAUSE) {
					this.mPlayerView.resumeVideo();
				} else {
					Logger.e("VODActivity", "已回收，重新请求播放");
					mPlayerView.playVideo(Configs.UUID, data.getVideo_unique(), "", "", "测试节目");
				}
			}
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

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		this.mPlayerView.destroyVideo();
		this.mPlayerLayoutView.removeAllViews();
		this.mPlayerView = null;
		Logger.e("VODActivity", "onDestroy");
		super.onDestroy();
		isBackgroud = false;
		LogUtils.clearLog();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
			//Log.i("VODActivity", "半屏");
		}else if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
			//Log.i("VODActivity", "全屏");
		}
	}
}
*/
