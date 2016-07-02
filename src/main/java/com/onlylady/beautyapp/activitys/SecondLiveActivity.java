package com.onlylady.beautyapp.activitys;

import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshRecycleView;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.adapters.SecLiveAdapter;
import com.onlylady.beautyapp.beans.MoreLiveBean;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.exlib.fab.FloatingActionButton;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.ClickUtils;
import com.onlylady.beautyapp.utils.FabUtils;

import java.util.List;

import butterknife.Bind;

public class SecondLiveActivity extends ListviewActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.fab)
    FloatingActionButton fab;


    @Override
    protected PullToRefreshRecycleView findPulltorefrshlistview() {
        return (PullToRefreshRecycleView) findViewById(R.id.pulltorefrshlistview_live);
    }

    @Override
    void setLisener() {
        ClickUtils.getInstance().setClosedWindowLisener(this, R.id.goback);
        FabUtils.getInstance().initFab(fab, getPulltorefrshlistview());
    }

//    @Override
//    protected void newlist() {
//        datas = new ArrayList<MoreLiveBean.DataEntity.LiveEntity>();
//    }

    @Override
    protected void initadapter() {
        adapter = new SecLiveAdapter(getApplicationContext());
    }

    @Override
    void loaddatas() {
        loaddata(getPage());
    }

    private int loadCount=0;

    private void loaddata(int page) {

//        themeEntity = (Themes.DataEntity.ThemeEntity) getIntent().getSerializableExtra("data");
        title.setText("回顾直播");


        BaseEngine.getInstance().getTGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1018(page), new TypeToken<MoreLiveBean>() {
        }.getType(), new BaseEngine.CallbackForT<MoreLiveBean>() {


            @Override
            public void finish(MoreLiveBean bean) {
                if (bean.getData().getLive() == null&&loadCount++<5) {
                    uploaddatas();
                } else {
                    loadCount=0;
                    addDatas(bean.getData().getLive());
                }

            }

            @Override
            public void finish(List<MoreLiveBean> listT) {

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
        return R.layout.activity_second_live;
    }

    @Override
    boolean useEventbuss() {
        return false;
    }


}
