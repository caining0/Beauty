package com.onlylady.beautyapp.fragments;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecycleView;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.adapters.LiveAdapter;
import com.onlylady.beautyapp.beans.LiveBean;
import com.onlylady.beautyapp.beans.onlylady.LiveBeans;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.UmengUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends ListviewFragment {

    @Bind(R.id.neterror)
    RelativeLayout netlayout;

    public LiveFragment() {

    }

    @Override
    boolean useEventbus() {
        return true;
    }


    @Override
    protected PullToRefreshRecycleView findPulltorefrshlistview(View v) {
        return (PullToRefreshRecycleView) v.findViewById(R.id.pulltorefrshlistview_home_live);
    }


    @Override
    protected void initadapter() {
        adapter = new LiveAdapter(getActivity());
    }

    @Override
    void loaddatas() {
        getPulltorefrshlistview().setMode(PullToRefreshBase.Mode.PULL_FROM_START);//只下拉
        loaddata();
    }

    private void loaddata() {
        BaseEngine.getInstance().getTGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1006(UmengUtils.getInstance().getUmengDeviceToken(context)), new TypeToken<LiveBean>() {
        }.getType(), new BaseEngine.CallbackForT<LiveBean>() {

            @Override
            public void finish(LiveBean bean) {
                if (getPage() == 1) {
                    adapter.clear();
                    addDatas(changebean(bean));
                } else {
                    addDatas(changebean(bean));
                }
            }

            @Override
            public void finish(List<LiveBean> listT) {

            }
        });
    }

    /**
     * 改变bean 为自己的bean
     *
     * @param bean
     * @return
     */

    private List<LiveBeans> changebean(LiveBean bean) {
        List<LiveBeans> lnew = new ArrayList<>();
        List<LiveBean.DataEntity.TodayEntity> today = bean.getData().getToday();
        if (today != null && today.size() > 0) {
            for (int i = 0; i < today.size(); i++) {
                LiveBeans liveBeans = new LiveBeans();
                liveBeans.setType("today");
                if (i == 0)
                    liveBeans.setFirst(true);
                liveBeans.setStat(today.get(i).getStat());
                liveBeans.setIu(today.get(i).getIu());
                liveBeans.setLaid(today.get(i).getLaid());
                liveBeans.setLid(today.get(i).getLid());
                liveBeans.setStu(today.get(i).getStu());
                liveBeans.setTt(today.get(i).getTt());
                liveBeans.setUp(today.get(i).getUp());
                liveBeans.setUsr(today.get(i).getUsr());
                liveBeans.setVl(today.get(i).getVl());
                liveBeans.setIrse(today.get(i).getIrse());
                liveBeans.setPm(today.get(i).getPm());
                lnew.add(liveBeans);
            }
        }
        List<LiveBean.DataEntity.YesterdayEntity> yeterday = bean.getData().getYesterday();
        if (yeterday != null && yeterday.size() > 0) {
            for (int i = 0; i < yeterday.size(); i++) {
                LiveBeans liveBeans = new LiveBeans();
                liveBeans.setType("yeterday");
                if (i == 0)
                    liveBeans.setFirst(true);
                liveBeans.setStat(yeterday.get(i).getStat());
                liveBeans.setIu(yeterday.get(i).getIu());
                liveBeans.setLaid(yeterday.get(i).getLaid());
                liveBeans.setLid(yeterday.get(i).getLid());
                liveBeans.setStu(yeterday.get(i).getStu());
                liveBeans.setTt(yeterday.get(i).getTt());
                liveBeans.setVl(yeterday.get(i).getVl());
                liveBeans.setUp(yeterday.get(i).getUp());
                liveBeans.setUsr(yeterday.get(i).getUsr());
                liveBeans.setIrse(yeterday.get(i).getIrse());
                liveBeans.setPm(yeterday.get(i).getPm());

                lnew.add(liveBeans);
            }
        }
        List<LiveBean.DataEntity.TomorrowEntity> tomorrow = bean.getData().getTomorrow();
        if (tomorrow != null && tomorrow.size() > 0) {
            for (int i = 0; i < tomorrow.size(); i++) {
                LiveBeans liveBeans = new LiveBeans();
                liveBeans.setType("tomorrow");
                if (i == 0)
                    liveBeans.setFirst(true);
                liveBeans.setStat(tomorrow.get(i).getStat());
                liveBeans.setIu(tomorrow.get(i).getIu());
                liveBeans.setLaid(tomorrow.get(i).getLaid());
                liveBeans.setLid(tomorrow.get(i).getLid());
                liveBeans.setStu(tomorrow.get(i).getStu());
                liveBeans.setTt(tomorrow.get(i).getTt());
                liveBeans.setVl(tomorrow.get(i).getVl());
                liveBeans.setUp(tomorrow.get(i).getUp());
                liveBeans.setUsr(tomorrow.get(i).getUsr());
                liveBeans.setIrse(tomorrow.get(i).getIrse());
                liveBeans.setPm(tomorrow.get(i).getPm());
                lnew.add(liveBeans);
            }
        } else {
            LiveBeans liveBeans = new LiveBeans();
            liveBeans.setStu(2);
            liveBeans.setType("tomorrownull");//空
            lnew.add(liveBeans);
        }

        return lnew;
    }

    @Override
    void uploaddatas() {
        getPulltorefrshlistview().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPulltorefrshlistview().onRefreshComplete();
            }
        }, 10);
    }

    @Override
    public void downloaddatas() {
        setPage(1);
        loaddata();
    }

    @Override
    int ViewResourcesid() {
        return R.layout.fragment_live;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onEvent(EventBusC c) {
        if (c.getFrom() == EventBusC.NETCONNET) {
            setVisible(c.getBundle().getBoolean("connect"));
        }
    }

    private void setVisible(boolean connect) {
        if (connect) {//网络连接
            getPulltorefrshlistview().setVisibility(View.VISIBLE);
            netlayout.setVisibility(View.GONE);
            doAfterview();
        } else {//断开网路
            getPulltorefrshlistview().setVisibility(View.GONE);
            netlayout.setVisibility(View.VISIBLE);
        }
    }
}
