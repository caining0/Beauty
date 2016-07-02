//package com.onlylady.beauty.elses.utils;
//
//import android.content.Context;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.Surface;
//import android.view.SurfaceHolder;
//import android.view.SurfaceHolder.Callback;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.RelativeLayout;
//
//import com.lecloud.download.control.DownloadCenter;
//import com.lecloud.entity.ActionInfo;
//import com.lecloud.entity.LiveInfo;
//import com.lecloud.entity.LiveStatus;
//import com.lecloud.entity.LiveStatusCallback;
//import com.lecloud.leutils.LeLog;
//import com.lecloud.log.KLog;
//import com.lecloud.volley.VolleyError;
//import com.letv.controller.LetvPlayer;
//import com.letv.controller.PlayContext;
//import com.letv.controller.PlayProxy;
//import com.letv.controller.imp.LetvPlayerControllerImp;
//import com.letv.controller.interfacev1.ILetvPlayerController;
//import com.letv.skin.interfacev1.IActionCallback;
//import com.letv.skin.utils.UIPlayContext;
//import com.letv.skin.v4.V4PlaySkin;
//import com.letv.universal.iplay.EventPlayProxy;
//import com.letv.universal.iplay.ISplayer;
//import com.letv.universal.iplay.OnPlayStateListener;
//import com.letv.universal.play.util.PlayerParamsHelper;
//import com.letv.universal.widget.ILeVideoView;
//import com.letv.universal.widget.ReSurfaceView;
//
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//
//public class LetvSimplePlayBoard {
//
//    protected static final String TAG = "LetvSimpleVideoView";
//    private static final boolean USE_PLAYER_PROXY = true;
//    //////////////////////////////////////
//    //////////////////////////////////////
//    private Context mContext;
//    private String path = "";
//    private Bundle mBundle;
//    private boolean isContinue = true;
//    //////////////////////////////////////
//
//    private int skinBuildType;
//    private UIPlayContext uicontext;
//    private V4PlaySkin skin;
//    //////////////////////////////////////
//
//    private int playMode;
//    private long lastPosition;
//    private ISplayer player;
//    private PlayContext playContext;
//    //////////////////////////////////////
//
//    private ILeVideoView videoView;
//
//    private ILetvPlayerController playerController;
//    /**
//     * 处理播放器的回调事件
//     */
//    private OnPlayStateListener playStateListener = new OnPlayStateListener() {
//        @Override
//        public void videoState(int state, Bundle bundle) {
//            handleADState(state, bundle);// 处理广告类事件
//            handleVideoInfoState(state, bundle);// 处理视频信息事件
//            handlePlayState(state, bundle);// 处理播放器类事件
//            handleLiveState(state, bundle);// 处理直播类事件
//        }
//    };
//
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // surfaceView生命周期
//    private Callback surfaceCallback = new Callback() {
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            KLog.d();
//            stopAndRelease();
//            KLog.d("ok");
//        }
//
//        @Override
//        public void surfaceCreated(final SurfaceHolder holder) {
//            KLog.d();
//            createOnePlayer(holder.getSurface());
//            KLog.d("ok");
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            if (player != null) {
//                PlayerParamsHelper.setViewSizeChange(player, width, height);
//            }
//        }
//    };
//
//    public void onResume() {
//        resume();
//    }
//
//    public void onPause() {
//        pause();
//    }
//
//    public void onDestroy() {
//        destroy();
//    }
//
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//
//    public void onConfigurationChanged(Configuration newConfig) {
//        if (videoView != null) {
//            videoView.setVideoLayout(-1, 0);
//        }
//    }
//
//    /**
//     * 初始化下载模块
//     */
//    private void initDownload() {
//        final String uuid = mBundle.getString(PlayProxy.PLAY_UUID);
//        final String vuid = mBundle.getString(PlayProxy.PLAY_VUID);
//        final DownloadCenter downloadCenter = DownloadCenter.getInstances(mContext);
//        if (downloadCenter != null && downloadCenter.isDownloadCompleted(vuid)) {
//            path = downloadCenter.getDownloadFilePath(vuid);
//        }
//        skin.setOnDownloadClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                downloadCenter.allowShowMsg(false);
//                downloadCenter.setDownloadRateText(playContext.getDefinationIdByType(uicontext.getCurrentRateType()));
//                downloadCenter.downloadVideo("", uuid, vuid);
//            }
//        });
//    }
//
//    public void init(Context mContext, Bundle mBundle, V4PlaySkin skin) {
//        this.mContext = mContext;
//        this.skin = skin;
//        this.mBundle = mBundle;
//        playMode = mBundle.getInt(PlayProxy.PLAY_MODE, -1);
//        switch (playMode) {
//            case EventPlayProxy.PLAYER_VOD:
//                skinBuildType = V4PlaySkin.SKIN_TYPE_A;
//                initDownload();
//                break;
//            case EventPlayProxy.PLAYER_LIVE:
//                skinBuildType = V4PlaySkin.SKIN_TYPE_B;
//                break;
//            case EventPlayProxy.PLAYER_ACTION_LIVE:
//                skinBuildType = V4PlaySkin.SKIN_TYPE_C;
//                break;
//            default:
//                break;
//        }
//        initVideoView();
//        initPlayContext();
//        initPlayerSkin();
//        PlayerAssistant.loadLastPosition(playContext, mBundle.getString(PlayProxy.PLAY_UUID), mBundle.getString(PlayProxy.PLAY_VUID));
//    }
//
//    private void initPlayContext() {
//        playContext = new PlayContext(mContext);
//        playContext.setVideoContainer(skin);
//        playContext.setUsePlayerProxy(USE_PLAYER_PROXY);
//        playContext.setVideoContentView(videoView.getMysef());
//        playerController = new LetvPlayerControllerImp();
//        playerController.setPlayContext(playContext);
//
//    }
//
//    private void resume() {
//        if (skin != null) {
//            skin.onResume();
//        }
//        if (player != null) {
//            player.start();
//        }
//    }
//
//    private void pause() {
//        if (isContinue && skinBuildType == V4PlaySkin.SKIN_TYPE_A && player != null && (int) player.getCurrentPosition() > 0) {
//            PlayerAssistant.saveLastPosition(mContext, mBundle.getString(PlayProxy.PLAY_UUID), mBundle.getString(PlayProxy.PLAY_VUID), (int) (player.getCurrentPosition()),
//                    playContext.getCurrentDefinationType());
//        }
//
//        if (skin != null) {
//            skin.onPause();
//        }
//        if (player != null) {
//            player.pause();
//        }
//    }
//
//    private void destroy() {
//        if (skin != null) {
//            skin.onDestroy();
//        }
//        if (playContext != null) {
//            playContext.destory();
//        }
//    }
//
//    /**
//     * 初始化播放器皮肤
//     */
//    private void initPlayerSkin() {
//
//        if (playMode == EventPlayProxy.PLAYER_ACTION_LIVE) {// 活动直播
//            PlayerSkinFactory.initActionLivePlaySkin(skin, V4PlaySkin.SKIN_TYPE_C, mBundle.getString(PlayProxy.PLAY_ACTIONID), new IActionCallback() {
//                @Override
//                public void switchMultLive(String liveId) {
//                    LeLog.d(TAG, "switchMultLive : " + liveId);
//                    ((LetvPlayer) player).switchMultLive(liveId);
//                }
//
//                @Override
//                public ISplayer createPlayerCallback(SurfaceHolder holder, String path, OnPlayStateListener playStateListener) {
//                    return PlayerAssistant.createActionLivePlayer(mContext, holder, path, playStateListener);
//                }
//            });
//        } else {
//            PlayerSkinFactory.initPlaySkin(skin, skinBuildType);
//        }
//        /**
//         * 获取皮肤的上下文
//         */
//        uicontext = skin.getUIPlayContext();
//    }
//
//    private void initVideoView() {
//        videoView = new ReSurfaceView(mContext);
//        videoView.getHolder().addCallback(surfaceCallback);
//        videoView.setVideoContainer(null);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        skin.addView(videoView.getMysef(), params);
//    }
//
//    /**
//     * 停止播放，并且记录最后播放时间
//     */
//    private void stopAndRelease() {
//        if (player != null) {
//            KLog.d();
//            lastPosition = player.getCurrentPosition();
//            player.stop();
//            player.reset();
//            player.release();
//            player = null;
//            KLog.d("release ok!");
//        }
//    }
//
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//    // ////////////////////////////////////////////////////////////////////////////////////////////
//
//    /**
//     * 创建一个新的播放器
//     *
//     * @param surface
//     */
//    private void createOnePlayer(Surface surface) {
//        player = PlayerFactory.createOnePlayer(playContext, mBundle, playStateListener, surface);
//        if (!TextUtils.isEmpty(path)) {
//            playContext.setUsePlayerProxy(false);
//        }
//        player.setDataSource(path);
//        if (lastPosition > 0 && mBundle.getInt(PlayProxy.PLAY_MODE) == EventPlayProxy.PLAYER_VOD) {
//            player.seekTo(lastPosition);
//        }
//        player.prepareAsync();
//        /**
//         * 皮肤关联播放器
//         */
//        playerController.setPlayer(player);
//        skin.registerController(playerController);
//    }
//
//    /**
//     * 处理直播相关信息
//     *
//     * @param state
//     * @param bundle
//     */
//    private void handleLiveState(int state, Bundle bundle) {
//        switch (state) {
//            case EventPlayProxy.PROXY_WATING_SELECT_ACTION_LIVE_PLAY:
//                /**
//                 * 活动直播
//                 */
//                PlayContext playContextAction = (PlayContext) player.getPlayContext();
//                ActionInfo actionInfo = playContextAction.getActionInfo();
//                if (actionInfo != null) {
//                    // 皮肤层设置活动信息
//                    uicontext.setActionInfo(actionInfo);
//                    /**
//                     * 如果当前活动直播是多个直播窗口， 选择一路可用的活动直播
//                     */
//                    LiveInfo liveInfo = actionInfo.getFirstCanPlayLiveInfo();
//                    if (liveInfo != null) {
//                        playContextAction.setLiveId(liveInfo.getLiveId());
//                    }
//                    // 活动直播中如果包含多个live信息 播放途中调用 LetvPlayer 中
//                    // switchMultLive（liveId）切换播放
//                }
//                break;
//            case EventPlayProxy.PROXY_SET_ACTION_LIVE_CURRENT_LIVE_ID:
//                // 当前播放的活动直播的liveID
//                uicontext.setMultCurrentLiveId(bundle.getString("liveId"));
//                break;
//
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 处理视频信息
//     *
//     * @param state
//     * @param bundle
//     */
//    private void handleVideoInfoState(int state, Bundle bundle) {
//        switch (state) {
//            case EventPlayProxy.PROXY_WAITING_SELECT_DEFNITION_PLAY:
//                /**
//                 * 皮肤获取码率
//                 */
//                PlayContext playContext = (PlayContext) player.getPlayContext();
//                if (playContext != null) {
//                    uicontext.setRateTypeItems(playContext.getDefinationsMap());
//                }
//
//                /**
//                 * 获取码率
//                 */
//                Map<Integer, String> definationsMap = playContext.getDefinationsMap();
//                Iterator<Entry<Integer, String>> it = definationsMap.entrySet().iterator();
//                while (it.hasNext()) {
//                    Entry<Integer, String> next = it.next();
//                    next.getKey();
//                    next.getValue();
//                }
//
//                break;
//            case EventPlayProxy.PROXY_VIDEO_INFO_REFRESH:
//                if (uicontext == null || player == null || player.getPlayContext() == null) {
//                    return;
//                }
//                uicontext.setVideoTitle(((PlayContext) player.getPlayContext()).getVideoTitle());
//                uicontext.setDownloadable(((PlayContext) player.getPlayContext()).isCanbeDownload());
//                break;
//        }
//    }
//
//    /**
//     * 处理广告事件
//     *
//     * @param state
//     * @param bundle
//     */
//    private void handleADState(int state, Bundle bundle) {
//        switch (state) {
//            case EventPlayProxy.PLAYER_PROXY_AD_START:
//                uicontext.setIsPlayingAd(true);// 广告播放时间
//                break;
//            case EventPlayProxy.PLAYER_PROXY_AD_END:
//                uicontext.setIsPlayingAd(false);
//                break;
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 处理播放器事件
//     *
//     * @param state
//     * @param bundle
//     */
//    private void handlePlayState(int state, Bundle bundle) {
//        switch (state) {
//            case ISplayer.MEDIA_EVENT_PREPARE_COMPLETE:
//                // TODO 获取当前播放的码率
//                uicontext.setCurrentRateType(playContext.getCurrentDefinationType());
//                if (uicontext.isClickPauseByUser() && player != null) {
//                    player.setVolume(0, 0);
//                }
//                if (player != null) {
//                    player.start();
//                }
//                break;
//            case ISplayer.MEDIA_EVENT_FIRST_RENDER:
//                if (uicontext.isClickPauseByUser() && player != null) {
//                    player.pause();
//                }
//                break;
//            case ISplayer.MEDIA_EVENT_VIDEO_SIZE:
//                if (videoView != null && player != null) {
//                    videoView.onVideoSizeChange(player.getVideoWidth(), player.getVideoHeight());
//                }
//                break;
//            case ISplayer.MEDIA_EVENT_PLAY_COMPLETE:
//                /**
//                 * 检查活动直播状态
//                 */
//                PlayerAssistant.checkActionLiveStatus(player, new LiveStatusCallback() {
//                    @Override
//                    public void onSuccess(LiveStatus liveStatus) {
//                        LeLog.dPrint("123", liveStatus.toString());
//                    }
//
//                    @Override
//                    public void onFail(VolleyError error) {
//                        LeLog.ePrint("123", "getActiveLiveStatus error ", error);
//                    }
//                });
//                break;
//        }
//    }
//
//    // //////////////////////////////////////////////////////////////////////////////////////////////////
//    // //////////////////////////////////////////////////////////////////////////////////////////////////
//    // //////////////////////////////////////////////////////////////////////////////////////////////////
//
//    public ISplayer getPlayer() {
//        return player;
//    }
//
//}
