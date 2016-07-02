package com.onlylady.beautyapp.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.activitys.FeedbackActivity;
import com.onlylady.beautyapp.activitys.LoginActivity;
import com.onlylady.beautyapp.activitys.MineZhiboActivity;
import com.onlylady.beautyapp.activitys.SettingActivity;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.utils.GlideUtils;
import com.onlylady.beautyapp.utils.SharedPrefeUtils;
import com.onlylady.beautyapp.views.CircleImageView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


    @Bind(R.id.usericon)
    CircleImageView usericon;
    @Bind(R.id.loginname)
    TextView loginname;
    @Bind(R.id.username_personal)
    TextView username;
    @Bind(R.id.feedback_zhibo)
    RelativeLayout feedbackZhibo;
    private boolean islogin = false;

    @Override
    void initfirst() {

    }

    @Override
    boolean useEventbus() {
        return true;
    }



    @Override
    protected void doAfterview() {
        islogin = SharedPrefeUtils.getSettings(context,SharedPrefeUtils.LOGIN,false);
        if(islogin){
            LoginActivity.getuserinfo(getActivity());
            GlideUtils.getInstance().setImageWithUrl(getActivity(),SharedPrefeUtils.getSettings(context,SharedPrefeUtils.USERICON),usericon,false);
            username.setText(SharedPrefeUtils.getSettings(context, SharedPrefeUtils.USERNAME));
            loginname.setVisibility(View.GONE);
            username.setVisibility(View.VISIBLE);

        }
    }

    @OnClick(R.id.usericon)
    public void loginlayout(View view) {
        if(!SharedPrefeUtils.getSettings(context,SharedPrefeUtils.LOGIN,false)) {
            login();
        }
    }
    @OnClick(R.id.loginname)
    public void loginname(View view) {
        login();
    }

    @OnClick(R.id.setting_usercenter)
    public void goSetting(View view){
        startActivity(new Intent(context, SettingActivity.class));
    }

    @OnClick(R.id.feedback_usercenter)
    public void goFeedBack(View view){
        startActivity(new Intent(context, FeedbackActivity.class));
    }
    @OnClick(R.id.feedback_zhibo)
    public void feedbackZhibo(View view){
        startActivity(new Intent(context, MineZhiboActivity.class));
    }

    private void login() {
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        intent.setAction("main");
        startActivity(intent);
    }

    @Override
    protected int getViewResourcesid() {
        return R.layout.fragment_personal;
    }


    public void onEvent(EventBusC c) {
        switch (c.getFrom()){
            case EventBusC.LoginEvent:
                Bundle bundle = c.getBundle();
                GlideUtils.getInstance().setImageWithUrl(getActivity(), bundle.getString("ul"), usericon, false);
                loginname.setVisibility(View.GONE);
                username.setText(bundle.getString("ue"));
                username.setVisibility(View.VISIBLE);
                if (bundle.getInt("ilv")==1){
                    feedbackZhibo.setVisibility(View.VISIBLE);
                }else {
                    feedbackZhibo.setVisibility(View.GONE);
                }
                break;
            case EventBusC.logoutEvent:
                loginname.setVisibility(View.VISIBLE);
                username.setVisibility(View.GONE);
                usericon.setImageResource(R.mipmap.morentouxiang);
                feedbackZhibo.setVisibility(View.GONE);
                break;
//            case EventBusC.JSLOGIN://插件调用
//                Intent intent = new Intent(getActivity(),LoginActivity.class);
//                intent.setAction("main");
//                startActivityForResult(intent, Configs.JSLOGIN);
//                 break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK&&requestCode == Configs.LOGIN) {
            LoginActivity.getuserinfo(getActivity());
        }
//        if (resultCode== Activity.RESULT_OK&&requestCode == Configs.JSLOGIN) {
//            JSONObject retJSONObj = new JSONObject();
//            PGPlugintest.getJsonUserInfo(retJSONObj);
//            JSUtil.execCallback(PGPlugintest.pWebview, PGPlugintest.CallBackID, retJSONObj, JSUtil.OK, false);
//        }
    }

}
