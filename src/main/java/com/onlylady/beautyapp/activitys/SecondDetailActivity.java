package com.onlylady.beautyapp.activitys;

import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshRecycleView;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.adapters.SecDetailAdapter;
import com.onlylady.beautyapp.beans.onlylady.SecDetailBeans;
import com.onlylady.beautyapp.beans.onlylady.Themes;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.exlib.fab.FloatingActionButton;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.ClickUtils;
import com.onlylady.beautyapp.utils.FabUtils;

import java.util.List;

import butterknife.Bind;


public class SecondDetailActivity extends ListviewActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private Themes.DataEntity.ThemeEntity themeEntity;

    @Override
    protected PullToRefreshRecycleView findPulltorefrshlistview() {
        return (PullToRefreshRecycleView) findViewById(R.id.pulltorefrshlistview_detail);
    }

    @Override
    void setLisener() {
        ClickUtils.getInstance().setClosedWindowLisener(this, R.id.goback);
        FabUtils.getInstance().initFab(fab, getPulltorefrshlistview());
    }

//    @Override
//    protected void newlist() {
////        datas = new ArrayList<SecDetailBeans.DataEntity.ArticlesEntity>();
//    }

    @Override
    protected void initadapter() {
        adapter = new SecDetailAdapter( getApplicationContext());
    }

    @Override
    void loaddatas() {
        loaddata(getPage());

    }


    private void loaddata(int page) {
        String type = getIntent().getStringExtra("type");
        if ("2013".equals(type)){
            title.setText("护肤");
        }else if ("2014".equals(type)){
            title.setText("彩妆");

        }else if ("2015".equals(type)){
            title.setText("美发");

        }else if ("2016".equals(type)){
            title.setText("美体");

        }
        if (!TextUtils.isEmpty(type)) {

            BaseEngine.getInstance().getTGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1005(page, type), new TypeToken<SecDetailBeans>() {
            }.getType(), new BaseEngine.CallbackForT<SecDetailBeans>() {

                @Override
                public void finish(SecDetailBeans bean) {
//                LogUtils.Log(bean.toString(), getApplicationContext());
                    addDatas(bean.getData().getArticles());
                }

                @Override
                public void finish(List<SecDetailBeans> listT) {

                }
            });

        }else {
            themeEntity = (Themes.DataEntity.ThemeEntity) getIntent().getSerializableExtra("data");
            title.setText(themeEntity.getTt());


            BaseEngine.getInstance().getTGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1003(themeEntity.getTid(), page), new TypeToken<SecDetailBeans>() {
            }.getType(), new BaseEngine.CallbackForT<SecDetailBeans>() {

                @Override
                public void finish(SecDetailBeans bean) {
//                LogUtils.Log(bean.toString(), getApplicationContext());
                    addDatas(bean.getData().getArticles());
                }

                @Override
                public void finish(List<SecDetailBeans> listT) {

                }
            });

        }

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
        return R.layout.activity_second_detail;
    }

    @Override
    boolean useEventbuss() {
        return false;
    }


}
