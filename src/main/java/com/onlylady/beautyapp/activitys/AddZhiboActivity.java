package com.onlylady.beautyapp.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.engines.GetLiveBeanFromLid;
import com.onlylady.beautyapp.exlib.pickerview.TimePickerView;
import com.onlylady.beautyapp.exlib.ucrop.UCrop;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.ClickUtils;
import com.onlylady.beautyapp.utils.GlideUtils;
import com.onlylady.beautyapp.utils.LogUtils;
import com.onlylady.beautyapp.utils.PhoneInfo;
import com.onlylady.beautyapp.utils.TimeUtils;
import com.onlylady.beautyapp.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

//import com.yalantis.ucrop.UCrop;

public class AddZhiboActivity extends BaseActivity {
    private static final int REQUEST_SELECT_PICTURE = 0x01;

    @Bind(R.id.commit)
    ImageView commit;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.title)
    EditText title;
    @Bind(R.id.content)
    EditText content;
    @Bind(R.id.begintime)
    TextView tvTime;
    @Bind(R.id.zhiboscroll)
    ScrollView zhiboscroll;
    @Bind(R.id.begintimelayout)
    RelativeLayout begintimelayout;


    private File file;
    private TimePickerView pvTime;
    private String lid;

    private Uri mDestinationUri;
    private File updapeFile;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "CropImage.jpeg";
    private boolean change = false;//修改
    private final long delayTime = (10 * 60 * 1000);
    private boolean timePicker = false;
    private InputMethodManager imm;
    private int chooseCount = 0;//选取的图片次数


    @Override
    public boolean useEventbus() {
        return false;
    }

    @Override
    public void createview() {
        setContentView(R.layout.activity_add_zhibo);

    }

    @Override
    public void initlisener() {
        ClickUtils.getInstance().setClosedWindowLisener(this, R.id.setting_goback);
        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void initData() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        file = new File(getCacheDir(), "" + PhoneInfo.uid + chooseCount + SAMPLE_CROPPED_IMAGE_NAME);
        mDestinationUri = Uri.fromFile(file);

        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
//        Date date = new Date(new Date().getTime() + delayTime);
//        pvTime.setTime(date);
//        tvTime.setText(TimeUtils.getInstance().getTime(date));
        tvTime.setText("请点击此处选择时间");
        pvTime.setCyclic(false);

        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                timePicker = true;
                if (date.getTime() < new Date().getTime()) {
                    ToastUtils.show(getApplicationContext(), "日期需大于当前日期！");
                    tvTime.setText(TimeUtils.getInstance().getTime(new Date()));
                } else {
                    tvTime.setText(TimeUtils.getInstance().getTime(date));
                }
            }
        });


        //弹出时间选择器
        begintimelayout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                hideClib(v);
                pvTime.setTime(new Date(new Date().getTime() + delayTime));
                pvTime.show();
            }
        });
        lid = getIntent().getStringExtra("lid");
        //Extra("lid", -1);
        if (!TextUtils.isEmpty(lid)) {
            change = true;
            ((TextView) findViewById(R.id.text_title)).setText("修改直播");

            GetLiveBeanFromLid.getInstance().getLiveBeanLaid(lid, new GetLiveBeanFromLid.CallbackJson() {
                @Override
                public void finish(JSONObject json) {
                    title.setText(json.optString("tt"));
                    tvTime.setText(TimeUtils.getInstance().getTime(TimeUtils.getInstance().timeStamp2Date(json.optInt("stat"))));
                    timePicker = true;
                    content.setText(json.optString("des"));
                    GlideUtils.getInstance().setImageWithUrl(getApplicationContext(), json.optString("iu"), image, false);

                }
            });

        }
    }

    private void hideClib(View v) {
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    @OnClick(R.id.commit)
    public void commit() {
        hideClib(commit);

        commit.setClickable(false);
        String tit = title.getText().toString();
        String cotent = content.getText().toString();
        LogUtils.Log(tit.length());
        if (!timePicker) {
            ToastUtils.show(getApplicationContext(), "请选择时间");
            commit.setClickable(true);
            return;
        }
        if (chooseCount == 0 && !change) {
            ToastUtils.show(getApplicationContext(), "请选择图片");
            commit.setClickable(true);
            return;
        }

        if (TextUtils.isEmpty(tit)) {
            ToastUtils.show(getApplicationContext(), "请输入标题");
            commit.setClickable(true);
            return;
        }
        if (TextUtils.isEmpty(cotent)) {
            ToastUtils.show(getApplicationContext(), "请输入内容");
            commit.setClickable(true);
            return;
        }

        if (tit.length() > 30) {
            ToastUtils.show(getApplicationContext(), "标题字数不能大于30字");
            commit.setClickable(true);
            return;
        }
        if (cotent.length() > 500) {
            ToastUtils.show(getApplicationContext(), "内容字数不能大于500字");
            commit.setClickable(true);
            return;
        }

//        file = new File(getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME);

        String text = tvTime.getText().toString();
        String timeChuo = TimeUtils.getInstance().getTimeChuo(text);

        if (Integer.parseInt(timeChuo) < Integer.parseInt(TimeUtils.getInstance().getTimeChuo(TimeUtils.getInstance().getTime(new Date())))) {
            ToastUtils.show(getApplicationContext(), "选择日期已过期");
            return;
        }

        Toast.makeText(getApplicationContext(), "提交中...", Toast.LENGTH_LONG).show();
        if (change) {
            update1102(timeChuo, tit, cotent);
        } else {
            update1101(timeChuo, tit, cotent);
        }


    }

    private void update1102(String timeChuo, String tit, String cotent) {
        BaseEngine.getInstance().setFilePost(Configs.getDOMAINV110(), updapeFile, UrlsHolder.getInstance().getParams1102(timeChuo, tit, cotent, lid), new BaseEngine.CallbackForT<String>() {
            @Override
            public void finish(String bean) {
                LogUtils.Log(bean);
                try {
                    JSONObject js = new JSONObject(bean);
                    if (js.optInt("errcode") != 0) {
                        ToastUtils.show(getApplicationContext(), js.optString("errmsg"));
                        commit.setClickable(true);
                    } else {
                        ToastUtils.show(getApplicationContext(), "修改成功");
//                        setResult(RESULT_OK);
                        EventBus.getDefault().post(EventBusC.getInstance(EventBusC.MINEREFRESS, null));
                        AddZhiboActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void finish(List<String> listT) {

            }
        });
    }

    private void update1101(String timeChuo, String tit, String cotent) {
        BaseEngine.getInstance().setFilePost(Configs.getDOMAINV110(), updapeFile, UrlsHolder.getInstance().getParams1101(getApplicationContext(), timeChuo, tit, cotent), new BaseEngine.CallbackForT<String>() {
            @Override
            public void finish(String bean) {
                LogUtils.Log(bean);
                try {
                    JSONObject js = new JSONObject(bean);
                    if (js.optInt("errcode") != 0) {
                        ToastUtils.show(getApplicationContext(), js.optString("errmsg"));
                        commit.setClickable(true);
                    } else {
                        ToastUtils.show(getApplicationContext(), "创建成功");
//                        setResult(RESULT_OK);
                        EventBus.getDefault().post(EventBusC.getInstance(EventBusC.MINEREFRESS, null));
                        AddZhiboActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void finish(List<String> listT) {

            }
        });
    }


    @OnClick(R.id.image)
    public void image() {
        hideClib(image);
        if (chooseCount > 0) {//重新初始化
            file = new File(getCacheDir(), "" + PhoneInfo.uid + chooseCount + SAMPLE_CROPPED_IMAGE_NAME);
            mDestinationUri = Uri.fromFile(file);
        }
        pickFromGallery();
    }

    private void pickFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(AddZhiboActivity.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void startCropActivity(@NonNull Uri uri) {
//        UCrop uCrop = UCrop.of(uri, mDestinationUri);
//
//        uCrop = basisConfig(uCrop);
//        uCrop = advancedConfig(uCrop);

//        uCrop.start(SampleActivity.this);

        int count = 0;
        while (mDestinationUri == null && count++ < 10) {
            LogUtils.Log("" + uri + "----,,,,,," + mDestinationUri);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mDestinationUri = Uri.fromFile(file);
        }

        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(100);

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        if (mDestinationUri != null)
            UCrop.of(uri, mDestinationUri)
                    .withAspectRatio(750, 480).withOptions(options)
                    .withMaxResultSize(750, 480)
                    .start(AddZhiboActivity.this);
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            chooseCount++;
//            ResultActivity.startWithUri(AddZhiboActivity.this, resultUri);
//            ToastUtils.show(getApplicationContext(),"haha");

            image.setImageURI(resultUri);
//            listUris.add(mDestinationUri);

            updapeFile = file;
            Bitmap bitmap = BitmapFactory.decodeFile(updapeFile.getAbsolutePath());

            LogUtils.Log(bitmap.getWidth()+"-----"+bitmap.getHeight());
//            GlideUtils.getInstance().setImageWithUrl(getApplicationContext(),resultUri.toString(),image,false);


        } else {
            Toast.makeText(AddZhiboActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(AddZhiboActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AddZhiboActivity.this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        zhiboscroll.post(new Runnable() {
            @Override
            public void run() {
                zhiboscroll.scrollTo(0, 0);
            }
        });
    }
}
