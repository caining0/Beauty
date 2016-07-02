package com.onlylady.beautyapp.activitys;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.ClickUtils;
import com.onlylady.beautyapp.utils.ToastUtils;

import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.feedback_edittext)
    EditText editText;
    @Bind(R.id.feedback_commit)
    ImageView feedbackCommit;
    @Bind(R.id.rl_feedback_text)
    RelativeLayout rlFeedbackText;
    @Bind(R.id.mainLayout)
    RelativeLayout mainLayout;


    private InputMethodManager imm;

    @Override
    public boolean useEventbus() {
        return false;
    }

    @Override
    public void createview() {
        setContentView(R.layout.activity_feedback);
    }

    @Override
    public void initlisener() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        editText.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    //InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if (imm.isActive()) {
////                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
////                        editText.setText(editText.getText()+"\n");
////                        feedbackCommit.performClick();
//                    }
//                    return true;
//                }
//
//                return false;
//
//            }
//        });
        rlFeedbackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusable(true);
                if (imm.isActive()) {
                    imm.showSoftInput(editText, 0);
                }
                //ToastUtils.show(getApplicationContext(),"aaa");
            }
        });
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
            }
        });
        ClickUtils.getInstance().setClosedWindowLisener(this, R.id.setting_goback);

    }

    @Override
    public void initData() {
        feedbackCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.show(getApplicationContext(), "请输入反馈内容");
                    return;
                }else if (s.length()<1||s.length()>500){
                    ToastUtils.show(getApplicationContext(),"请输入1-500字");
                 return;
                }

                Map<String, String> params1025 = UrlsHolder.getInstance().getParams1025(s);
                BaseEngine.getInstance().getStringPost(Configs.getDOMAINV100(), params1025, new BaseEngine.CallbackForT<String>() {
                    @Override
                    public void finish(String bean) {
                        ToastUtils.show(getApplicationContext(), "发送成功！");
                        FeedbackActivity.this.finish();
//                        editText.setText("");
//                        editText.setFocusable(false);
//                        if (imm.isActive()) {
//                            imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
//                        }
                        //FeedbackActivity.this.finish();
                    }

                    @Override
                    public void finish(List<String> listT) {

                    }
                });
            }
        });
    }

}
