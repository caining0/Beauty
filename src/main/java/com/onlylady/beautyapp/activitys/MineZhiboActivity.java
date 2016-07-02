package com.onlylady.beautyapp.activitys;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecycleView;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.adapters.MineZhiboAdapter;
import com.onlylady.beautyapp.beans.onlylady.MineLiveBeans;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.ClickUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MineZhiboActivity extends ListviewActivity {


    @Bind(R.id.addzhibo)
    ImageView addzhibo;
    @Bind(R.id.feedbacktitle)
    RelativeLayout feedbacktitle;
    @Bind(R.id.textshow)
    public TextView textshow;
    @Override
    protected PullToRefreshRecycleView findPulltorefrshlistview() {
        return (PullToRefreshRecycleView) findViewById(R.id.pulltorefrshlistview_zhibo);
    }

    @Override
    void setLisener() {
        ClickUtils.getInstance().setClosedWindowLisener(this, R.id.setting_goback);

    }

    @Override
    protected void initadapter() {
        adapter = new MineZhiboAdapter(this);
    }

    @Override
    void loaddatas() {
        getPulltorefrshlistview().setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        loaddata(getPage());
    }

    private void loaddata(int page) {
        BaseEngine.getInstance().getTGet(Configs.getDOMAINV110(), UrlsHolder.getInstance().getParams1105(), new TypeToken<MineLiveBeans>() {
        }.getType(), new BaseEngine.CallbackForT<MineLiveBeans>() {


            @Override
            public void finish(MineLiveBeans bean) {
                if (bean != null && bean.getData() != null) {
                    getPulltorefrshlistview().setVisibility(View.VISIBLE);
                    textshow.setVisibility(View.GONE);
                    addDatas(bean.getData().getLive());
                }else if (bean.getData()==null||bean.getData() .getLive()==null||bean.getData() .getLive().size()==0){
//                    ToastUtils.show(getApplicationContext(),"没有数据");
                    getPulltorefrshlistview().setVisibility(View.GONE);
                    textshow.setVisibility(View.VISIBLE);

                }
              //  LogUtils.Log(bean.getData().getLive().size());
            }

            @Override
            public void finish(List<MineLiveBeans> listT) {

            }
        });
    }

    @Override
    void uploaddatas() {
        setPage(getPage() + 1);
        loaddata(getPage());
    }

    @Override
    void downloaddatas() {
        setPage(1);
        loaddata(getPage());
    }

    @Override
    int ViewResourcesid() {
        return R.layout.activity_mine_zhibo;
    }

    @Override
    boolean useEventbuss() {
        return true;
    }


    @OnClick(R.id.addzhibo)
    public void addzhibo() {
        startActivityForResult(new Intent(MineZhiboActivity.this, AddZhiboActivity.class), 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode == 1000) {
                downloaddatas();
            }
        }
    }
    public void onEvent(EventBusC c) {
        if (c.getFrom() == EventBusC.MINEREFRESS) {
            downloaddatas();
        }
    }
}
