package com.onlylady.beautyapp.activitys;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecycleView;
import com.handmark.pulltorefresh.library.easyrecyclerview.widget.EasyRecyclerView;
import com.handmark.pulltorefresh.library.easyrecyclerview.widget.decorator.EasyDividerItemDecoration;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.adapters.BaseRAdapter;

import java.util.List;

/**
 * Created by caining on 16/2/15.
 */
public abstract class ListviewActivity<T> extends BaseActivity {
//    List<T> datas;
    BaseRAdapter adapter;

    int page = 1;
//    @Bind(R.id.pulltorefrshlistview)
    private PullToRefreshRecycleView pulltorefrshlistview;

    @Override
    public boolean useEventbus() {
        return useEventbuss();
    }

    @Override
    public void createview() {
//        newlist();
        setContentView(ViewResourcesid());
        pulltorefrshlistview = findPulltorefrshlistview();
    }

    protected abstract PullToRefreshRecycleView findPulltorefrshlistview();

    @Override
    public void initlisener() {
        setLisener();
    }
    abstract void setLisener();

    @Override
    public void initData() {
        pulltorefrshlistview.setScrollingWhileRefreshingEnabled(true);
        pulltorefrshlistview.setMode(PullToRefreshBase.Mode.BOTH);
        pulltorefrshlistview.getRefreshableView().addItemDecoration(new EasyDividerItemDecoration(this,EasyDividerItemDecoration.VERTICAL_LIST,R.drawable.bg_recycler_view_divider));
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



//    protected abstract void newlist();

    public void clearDatas() {
        this.adapter.clear();
        adapter.notifyDataSetChanged();
    }


    public void addDatas(List<T> datas) {
        if (datas==null){
            pulltorefrshlistview.onRefreshComplete();
            return;
        }
        if (page==1) clearDatas();
        adapter.addAll(datas);
        adapter.notifyDataSetChanged();
        pulltorefrshlistview.onRefreshComplete();
        pulltorefrshlistview.getRefreshableView().smoothScrollBy(1,1);
    }

//    public void addData(T data) {
//        if (datas==null){
//            pulltorefrshlistview.onRefreshComplete();
//            return;
//        }
//        this.datas.add(data);
//        adapter.notifyDataSetChanged();
//        pulltorefrshlistview.onRefreshComplete();
//    }



    public int getPage() {
        return page;
    }

    public void setPage(int page) {

        this.page = page;
    }

    public PullToRefreshRecycleView getPulltorefrshlistview() {
        return pulltorefrshlistview;
    }

    /**
     * 初始化adapter
     */
    protected abstract void initadapter();


    abstract void loaddatas();
    abstract void uploaddatas();
    abstract void downloaddatas();
    abstract int ViewResourcesid();
    abstract boolean useEventbuss();
}
