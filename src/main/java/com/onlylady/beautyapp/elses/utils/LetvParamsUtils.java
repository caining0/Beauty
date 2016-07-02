//package com.onlylady.beauty.elses.utils;
//
//import android.os.Bundle;
//
//import com.letv.controller.PlayProxy;
//import com.letv.universal.iplay.EventPlayProxy;
//
///**
// * 乐视云服务参数设置帮助类
// *
// * @author pys
// *
// */
//public class LetvParamsUtils {
//
//    /**
//     * 乐视运直播参数设置
//     * @param streamId
//     * @param liveId
//     * @param isHls
//     * @param isLetv
//     * @param isPanoVideo
//     * @return
//     */
//    public static Bundle setLiveParams(String streamId, String liveId, boolean isHls, boolean isLetv, boolean isPanoVideo) {
//
//        Bundle mBundle = new Bundle();
//        mBundle.putInt(PlayProxy.PLAY_MODE, EventPlayProxy.PLAYER_LIVE);
//
//        /**
//         * PlayProxy.PLAY_STREAMID和PlayProxy.PLAY_LIVEID参数说明：两者二选一传入就行
//         * 用户申请使用的直播业务中，如果申请使用的是streamID,则传入streamID,streamID示例：20150421300001210
//         * 用户申请使用的直播业务中，如果申请使用的是liveID,则传入liveID，liveID示例：201504213000012
//         */
//        mBundle.putString(PlayProxy.PLAY_STREAMID, streamId);
//        mBundle.putString(PlayProxy.PLAY_LIVEID, liveId);
//        // mBundle.putBoolean(PlayProxy.PLAY_ISRTMP, rb1.isChecked());
//        mBundle.putBoolean(PlayProxy.PLAY_USEHLS, isHls);
//        mBundle.putBoolean(PlayProxy.PLAY_ISLETV, isLetv);
//        mBundle.putBoolean(PlayProxy.BUNDLE_KEY_ISPANOVIDEO, isPanoVideo);
//        return mBundle;
//    }
//
//    /**
//     * 乐视云点播参数设置
//     * 没有的数值传空字符串""
//     * @param uuid
//     * @param vuid
//     * @param checkCode
//     * @param userKey
//     * @param isPannoVideo
//     * @return
//     */
//    public static Bundle setVodParams(String uuid, String vuid, String checkCode, String userKey, String playName, boolean isPannoVideo) {
//        Bundle mBundle = new Bundle();
//        mBundle.putInt(PlayProxy.PLAY_MODE, EventPlayProxy.PLAYER_VOD);
//        mBundle.putString(PlayProxy.PLAY_UUID, uuid);
//        mBundle.putString(PlayProxy.PLAY_VUID, vuid);
//        mBundle.putString(PlayProxy.PLAY_CHECK_CODE,checkCode);
//        mBundle.putString(PlayProxy.PLAY_PLAYNAME, playName);
//        mBundle.putString(PlayProxy.PLAY_USERKEY, userKey);
//        mBundle.putBoolean(PlayProxy.BUNDLE_KEY_ISPANOVIDEO, isPannoVideo);
//        return mBundle;
//    }
//
//    /**
//     * 乐视云活动直播参数设置
//     * @param actionId
//     * @param useHls
//     * @return
//     */
//    public static Bundle setActionLiveParams(String actionId,boolean useHls){
//        Bundle mBundle = new Bundle();
//        mBundle.putInt(PlayProxy.PLAY_MODE, EventPlayProxy.PLAYER_ACTION_LIVE);
//        mBundle.putString(PlayProxy.PLAY_ACTIONID, actionId);
////        mBundle.putBoolean(PlayProxy.PLAY_ISRTMP, rb1.isChecked());
//        mBundle.putBoolean(PlayProxy.PLAY_USEHLS, useHls);
//        return mBundle;
//    }
//
//}
