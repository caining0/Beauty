package com.onlylady.beautyapp.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;

import com.onlylady.beautyapp.activitys.HomeActivity;
import com.onlylady.beautyapp.adapters.BaseRAdapter;
import com.onlylady.beautyapp.beans.onlylady.MineLiveBeans;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.exlib.MaterialDialog;
import com.onlylady.beautyapp.urls.UrlsHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by caining on 16/3/18.
 */
public class DialogUtils {
    public static DialogUtils clickUtils;

    public static DialogUtils getInstance() {
        if (clickUtils == null) {
            clickUtils = new DialogUtils();
        }
        return clickUtils;
    }
    public void quite(final HomeActivity activity){
        final MaterialDialog materialDialog = new MaterialDialog(activity);

        materialDialog.setTitle("真的要退出?").setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        }).show();
    }

    public void goOnOrOnt(final Context activity, final YesNot yesOrNot){
        final MaterialDialog materialDialog = new MaterialDialog(activity);

        materialDialog.setTitle("非Wifi网络，是否继续?").setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesOrNot.positive();
                materialDialog.dismiss();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesOrNot.negative();
                materialDialog.dismiss();
            }
        }).show();
    }
    public interface YesNot {
        void positive();
        void negative();
    }

    public void dialogout(final Activity activity, final View view){
        final MaterialDialog materialDialog = new MaterialDialog(activity);

        materialDialog.setTitle("确认退出吗?").setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDKUtils.getInstance().logout(activity);
                EventBus.getDefault().post(EventBusC.getInstance(EventBusC.logoutEvent, null));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    view.setAlpha(0f);
                }
                view.setClickable(false);
                materialDialog.dismiss();
                activity.finish();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        }).show();
    }

    public void deleteItem( Context activity, final String lid, final List<MineLiveBeans.DataEntity.LiveEntity> datas, final BaseRAdapter adapter, final int posi){
        final MaterialDialog materialDialog = new MaterialDialog(activity);

        materialDialog.setTitle("是否删除?").setPositiveButton("是", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseEngine.getInstance().getStringPost(Configs.getDOMAINV110(), UrlsHolder.getInstance().getParams1107(lid), new BaseEngine.CallbackForT<String>() {
                    @Override
                    public void finish(String bean) {
                        JSONObject jsonobject = null;
                        try {
                            jsonobject = new JSONObject(bean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (jsonobject.optInt("errcode")==0){//删除成功
                            datas.remove(posi);
                            adapter.notifyDataSetChanged();
                            materialDialog.dismiss();
                        }
                    }

                    @Override
                    public void finish(List<String> listT) {

                    }
                });
            }
        }).setNegativeButton("否", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        }).show();
    }
}
