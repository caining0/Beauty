//package com.onlylady.beauty.elses.utils;
//
//import com.letv.controller.interfacev1.ISplayerController;
//import com.letv.skin.BaseSkin;
//import com.letv.skin.interfacev1.IActionCallback;
//import com.letv.skin.utils.UIPlayContext;
//import com.letv.skin.v4.V4PlaySkin;
//
//public class PlayerSkinFactory {
//
//    /**
//     * 创建播放器皮肤
//     * @param skin
//     * @param skinBuildType
//     * @return
//     */
//    public static BaseSkin initPlaySkin(BaseSkin skin, int skinBuildType){
//        UIPlayContext uicontext=new UIPlayContext();
//        uicontext.setSkinBuildType(skinBuildType);
//        uicontext.setScreenResolution(ISplayerController.SCREEN_ORIENTATION_PORTRAIT);
////        uicontext.setScreenResolution(UIPlayerControl.SCREEN_ORIENTATION_LANDSCAPE);
//        if(skin instanceof V4PlaySkin){
//            ((V4PlaySkin)skin).changeLayoutParams(16, 9);
//        }
//        skin.build(uicontext);
//        return skin;
//    }
//
//    /**
//     * 创建活动直播皮肤
//     * @param skin
//     * @param skinBuildType
//     * @param activityId
//     * @param mIActionCallback
//     * @return
//     */
//    public static BaseSkin initActionLivePlaySkin(BaseSkin skin,int skinBuildType,String activityId,IActionCallback mIActionCallback){
//        ((V4PlaySkin)skin).setIActionCallback(mIActionCallback);
//
//        UIPlayContext uicontext=new UIPlayContext();
//        uicontext.setSkinBuildType(skinBuildType);
//        uicontext.setScreenResolution(ISplayerController.SCREEN_ORIENTATION_PORTRAIT);
//        if(skin instanceof V4PlaySkin){
//            ((V4PlaySkin)skin).changeLayoutParams(16, 9);
//        }
//        uicontext.setActivityId(activityId);
//        skin.build(uicontext);
//
//        return skin;
//    }
//
//}
