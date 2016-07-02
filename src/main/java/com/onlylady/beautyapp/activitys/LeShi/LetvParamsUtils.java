//package com.onlylady.beautyapp.activitys.LeShi;
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
////        mBundle.putBoolean(PlayProxy.PLAY_ISRTMP, true);
//        mBundle.putBoolean(PlayProxy.PLAY_USEHLS, useHls);
//        return mBundle;
//    }
//
//}
