package com.onlylady.beautyapp.activitys;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.PhoneInfo;
import com.onlylady.beautyapp.utils.ShareSDKUtils;
import com.onlylady.beautyapp.utils.SharedPrefeUtils;
import com.onlylady.beautyapp.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class UserRegisterActivity extends BaseActivity {

    @Bind(R.id.register_titlename)
    TextView title;
    @Bind(R.id.usericon_register)
    ImageView userIcon;
    @Bind(R.id.username_register)
    TextView usernameView;
    @Bind(R.id.username_edit)
    EditText usernameEdit;
    @Bind(R.id.checkbox_agreeservice)
    CheckBox agreeService;
    @Bind(R.id.agreeservice_text)
    TextView agreeServiceText;
    @Bind(R.id.commit)
    Button commit;

    private boolean telornot = false;
    private String tel;
    private String username;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean useEventbus() {
        return false;
    }

    @Override
    public void createview() {
        setContentView(R.layout.activity_user_register);


    }

    @Override
    public void initlisener() {
        usernameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 15) {
                    Toast.makeText(UserRegisterActivity.this, "字数超出限制", Toast.LENGTH_SHORT).show();
                    String ss = s.subSequence(0, 15).toString();
                    usernameEdit.setText(ss);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        agreeService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b)
                    ToastUtils.show(getApplicationContext(), "请同意服务协议");
//                commit.setClickable(b);
            }
        });

    }

    @Override
    public void initData() {
        telornot = getIntent().getBooleanExtra("telornot", false);
        tel = getIntent().getStringExtra("tel");
        username = SharedPrefeUtils.getSettings(getApplicationContext(), SharedPrefeUtils.USERNAME);
        if (telornot) {
            username = tel;
            Glide.with(this).load(getIntent().getStringExtra("imageturl")).crossFade(500).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(userIcon);
        }
        usernameView.setText(username);
        usernameEdit.setText(username);
        Selection.setSelection(usernameEdit.getText(), usernameEdit.length());
//        usernameEdit.setHint(username);
        if (!telornot) {
            String usericon = SharedPrefeUtils.getSettings(getApplicationContext(), SharedPrefeUtils.USERICON);
            Glide.with(this).load(usericon).crossFade(500).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(userIcon);
        }
        String logintype = SharedPrefeUtils.getSettings(getApplicationContext(), SharedPrefeUtils.LOGINTYPE);
        if ("1".equals(logintype)) {
            type = "1009";
            title.setText("绑定新浪微博账号");
        } else if ("2".equals(logintype)) {
            type = "1008";
            title.setText("绑定QQ账号");
        } else if ("3".equals(logintype)) {
            type = "1010";
            title.setText("绑定微信账号");
        }
        if (telornot) {
            title.setText("修改账户昵称");
        }
    }


    private void telregestUserTel() {
        BaseEngine.getInstance().getStringPost(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1014(tel, username), new BaseEngine.CallbackForT<String>() {
            @Override
            public void finish(String bean) {
                JSONObject json = null;
                try {
                    json = new JSONObject(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (json.optInt("errcode") != 0) {
                    toast(json);
                } else {
                    JSONObject data = json.optJSONObject("data");
                    ShareSDKUtils.getInstance().saveUserInfo(getApplicationContext(), data);
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    UserRegisterActivity.this.finish();
                }
            }

            @Override
            public void finish(List<String> listT) {

            }
        });
    }

    private void toast(JSONObject json) {
        Toast.makeText(getApplicationContext(), json.optString("errmsg") + "(请使用字母/汉字/数字组合，去掉空格及特殊字符!)", Toast.LENGTH_SHORT).show();
    }


    public void regesterFromOut() {
        BaseEngine.getInstance().getStringPost(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1008(type, SharedPrefeUtils.getSettings(getApplicationContext(), SharedPrefeUtils.ACCESSTOKEN)), new BaseEngine.CallbackForT<String>() {
            @Override
            public void finish(String bean) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonObject.optInt("errcode") != 0) {//用户名已存在 //不合法用户名
                    usernameEdit.setFocusable(true);
                    Toast.makeText(getApplicationContext(), jsonObject.optString("errmsg"), Toast.LENGTH_SHORT).show();
                    toast(jsonObject);
                } else {
                    setResult(Activity.RESULT_OK);
                    UserRegisterActivity.this.finish();
                }
            }

            @Override
            public void finish(List<String> listT) {

            }
        });
    }


    @OnClick(R.id.register_goback)
    public void goback(View view) {
        this.finish();
    }

    @OnClick(R.id.agreeservice_text)
    public void readService(View view) {
        Intent intent = new Intent(UserRegisterActivity.this, TextReaderActivity.class);
        intent.putExtra("fwxy", true);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @OnClick(R.id.commit)
    public void setCommit(View view) {
        if (!agreeService.isChecked()) {
            ToastUtils.show(getApplicationContext(), "请同意服务协议");
            return;
        }
        if (usernameEdit.getText().toString().isEmpty()) {
            Toast.makeText(UserRegisterActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            usernameEdit.setText("");
            usernameEdit.setFocusable(true);
            return;
        } else {
            username = usernameEdit.getText().toString().trim();
        }
        SharedPrefeUtils.saveSettings(getApplicationContext(), SharedPrefeUtils.USERNAME, username);
        PhoneInfo.username = username;
        if (!telornot) {
            regesterFromOut();
        } else {
            telregestUserTel();
        }
    }
}

