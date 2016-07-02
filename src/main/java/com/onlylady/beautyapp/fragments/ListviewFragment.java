package com.onlylady.beautyapp.fragments;


import android.support.v4.app.Fragment;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecycleView;
import com.handmark.pulltorefresh.library.easyrecyclerview.widget.EasyRecyclerView;
import com.handmark.pulltorefresh.library.easyrecyclerview.widget.decorator.EasyDividerItemDecoration;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.adapters.BaseRAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class ListviewFragment<T> extends BaseFragment {
    //    List<T> datas;
    BaseRAdapter adapter;

    int page = 1;
    //    @Bind(R.id.pulltorefrshlistview)
    private PullToRefreshRecycleView pulltorefrshlistview;

    @Override
    void initfirst() {
//        newlist();
    }

    /**
     * new list
     */
//    protected abstract void newlist();

//    public void clearDatas() {
//        this.adapter.clear();
//        adapter.notifyDataSetChanged();
//    }
//    @Override
//    protected void initView(View v) {
//        pulltorefrshlistview = findPulltorefrshlistview(v);
//    }


    protected abstract PullToRefreshRecycleView findPulltorefrshlistview(View v);

    public void addDatas(List<T> datas) {
        if (page == 1) adapter.clear();
        this.adapter.addAll(datas);
        this.adapter.notifyDataSetChanged();
        pulltorefrshlistview.onRefreshComplete();
        pulltorefrshlistview.getRefreshableView().smoothScrollBy(1, 1);
    }



    public int getPage() {
        return page;
    }

    public void setPage(int page) {

        this.page = page;
    }

    public PullToRefreshRecycleView getPulltorefrshlistview() {

        return pulltorefrshlistview;
    }

    @Override
    protected void doAfterview() {
        pulltorefrshlistview= findPulltorefrshlistview(returnView());
        pulltorefrshlistview.setScrollingWhileRefreshingEnabled(true);
        pulltorefrshlistview.setMode(PullToRefreshBase.Mode.BOTH);
        pulltorefrshlistview.getRefreshableView().addItemDecoration(new EasyDividerItemDecoration(getActivity(), EasyDividerItemDecoration.VERTICAL_LIST, R.drawable.bg_recycler_view_divider));
        pulltorefrshlistview.setRefreshing();

        initadapter();
        pulltorefrshlistview.getRefreshableView().setAdapter(adapter);
        loaddatas();
        pulltorefrshlistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<EasyRecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<EasyRecyclerView> refreshView) {
                downloaddatas();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<EasyRecyclerView> refreshView) {
                uploaddatas();
            }
        });
    }

    /**
     * 初始化adapter
     */
    protected abstract void initadapter();


    abstract void loaddatas();

    abstract void uploaddatas();

    abstract void downloaddatas();

    abstract int ViewResourcesid();


    @Override
    protected int getViewResourcesid() {
        return ViewResourcesid();//R.layout.fragment_first;
    }


}
