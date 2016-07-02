package com.onlylady.beautyapp.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshRecycleView;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.adapters.HomeAdapter;
import com.onlylady.beautyapp.adapters.LocalImageHolderView;
import com.onlylady.beautyapp.adapters.RecyclerViewAdapter;
import com.onlylady.beautyapp.beans.VideoListBean;
import com.onlylady.beautyapp.beans.onlylady.Focuses;
import com.onlylady.beautyapp.beans.onlylady.HomeBeans;
import com.onlylady.beautyapp.beans.onlylady.Themes;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.exlib.fab.FloatingActionButton;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.FabUtils;
import com.onlylady.beautyapp.views.CnLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends ListviewFragment {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.neterror)
    RelativeLayout netlayout;
    @Bind(R.id.listviewlayout)
    RelativeLayout listviewlayout;
    private LayoutInflater layoutInflater;
    private ConvenientBanner convenientBanner;
    private int tiemd = 4500;
    private RecyclerView recyclerView;


    public HomeFragment() {
    }

    @Override
    protected void initadapter() {
        adapter = new HomeAdapter(getActivity());
    }

    @Override
    void loaddatas() {
        netlayout.findViewById(R.id.reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloaddatas();
            }
        });
        FabUtils.getInstance().initFab(fab, getPulltorefrshlistview());
        addheader();

    }

    private void addheader() {
        layoutInflater = LayoutInflater.from(getActivity());
        View headerViewPager = layoutInflater.inflate(R.layout.home_viewpager_view, null);
        View headerhorizontallistview = layoutInflater.inflate(R.layout.home_hlistview_view, null);
        View view = createrViewpager(headerViewPager);
        View hlistview = createHlistview(headerhorizontallistview);
        adapter.addHeader(view);
        adapter.addHeader(hlistview);
        loaddata(getPage());
    }

    private View createHlistview(View headerhorizontallistview) {
        recyclerView = (RecyclerView) headerhorizontallistview.findViewById(R.id.hlistview);
        recyclerView.setLayoutManager(new CnLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        return headerhorizontallistview;
    }

    private View createrViewpager(View v) {
        convenientBanner = (ConvenientBanner) v.findViewById(R.id.convenientBanner);
        loaddatasViewpager();
        loadatasRecycle();
        return v;
    }

    private void loadatasRecycle() {
        BaseEngine.getInstance().getTGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1002("index"), new TypeToken<Themes>() {
        }.getType(), new BaseEngine.CallbackForT<Themes>() {

            @Override
            public void finish(Themes bean) {
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(bean.getData().getTheme(), context);
                recyclerView.setAdapter(recyclerViewAdapter);
                adapter.notifyDataSetChanged();
            }


            @Override
            public void finish(List<Themes> listT) {
            }
        });
    }

    private void loaddatasViewpager() {
        BaseEngine.getInstance().getTGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1001(), new TypeToken<Focuses>() {
        }.getType(), new BaseEngine.CallbackForT<Focuses>() {
            @Override
            public void finish(Focuses bean) {
//                focuses = bean.getData().getFocuses();
                convenientBanner.setPages(
                        new CBViewHolderCreator<LocalImageHolderView>() {
                            @Override
                            public LocalImageHolderView createHolder() {
                                return new LocalImageHolderView();
                            }
                        }, bean.getData().getFocuses())

                        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.mipmap.home_yuandian, R.mipmap.home_yuandian_selected})
                                //设置指示器的方向
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
                //设置翻页的效果，不需要翻页效果可用不设
                //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
//                convenientBanner.setManualPageable(false);//设置不能手动影响
                adapter.notifyDataSetChanged();
            }

            @Override
            public void finish(List<Focuses> listT) {

            }
        });
//        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                String aid = focuses.get(position).getAid();
//                String type = focuses.get(position).getType();
//                StartActivityUtils.startH5(context, aid, type);
//            }
//        });
    }

    @Override
    boolean useEventbus() {
        return true;
    }

//    @Override
//    protected void initView(View v) {
//
//    }

    @Override
    protected PullToRefreshRecycleView findPulltorefrshlistview(View v) {
        return (PullToRefreshRecycleView) v.findViewById(R.id.pulltorefrshlistview_homefirst);
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(tiemd);
    }


    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    private void loaddata(int page) {

        BaseEngine.getInstance().getTGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1004(page), new TypeToken<VideoListBean>() {
        }.getType(), new BaseEngine.CallbackForT<VideoListBean>() {

            @Override
            public void finish(VideoListBean bean) {
                List<HomeBeans> changebean = changebean(bean);
                if (getPage() == 1) {
//                    clearDatas();
                    adapter.clear();
                    addDatas(changebean);
//                    setPage(getPage() + 1);
//                    loaddata(getPage());//直接加载两页
                } else {
                    addDatas(changebean);
                }
            }

            @Override
            public void finish(List<VideoListBean> listT) {

            }
        });


    }

    private List<HomeBeans> changebean(VideoListBean bean) {
        List<VideoListBean.DataEntity.LiveEntity> live = bean.getData().getLive();
        List<HomeBeans> homeBeanses = new ArrayList<>();
        boolean first = false;
        if (live != null && live.size() > 0) {
            first = true;
            HomeBeans homeBeans = new HomeBeans(2);
            homeBeans.setStat(live.get(0).getStat());
            homeBeans.setFirst(true);
            homeBeans.setType("live");
            homeBeanses.add(homeBeans);//留白 背景
            for (int i = 0; i < live.size(); i++) {
                HomeBeans homeBean = new HomeBeans(0);
                homeBean.setIu(live.get(i).getIu());
                homeBean.setLaid(live.get(i).getLaid());
                homeBean.setLid(live.get(i).getLid());
                homeBean.setStat(live.get(i).getStat());
                homeBean.setStu(live.get(i).getStu());
                homeBean.setTt(live.get(i).getTt());
                homeBean.setVl(live.get(i).getVl());
                homeBean.setType("lv");
//                homeBean.setMyType(0);
                homeBeanses.add(homeBean);
            }
        }
        List<VideoListBean.DataEntity.ArticlesEntity> articles = bean.getData().getArticles();
        if (articles != null && articles.size() > 0) {
            HomeBeans homeBeans = new HomeBeans(2);
            homeBeans.setStat(articles.get(0).getPt());//时间戳
            homeBeans.setType(articles.get(0).getType());
            if (!first) {
                homeBeans.setFirst(true);
            }
            homeBeanses.add(homeBeans);//留白 背景

            for (int i = 0; i < articles.size(); i++) {
                HomeBeans homeBean = new HomeBeans(1);
                homeBean.setIu(articles.get(i).getIu());
                homeBean.setAid(articles.get(i).getAid());
                homeBean.setCl(articles.get(i).getCl());
                homeBean.setUp(articles.get(i).getUp());
                homeBean.setUsr(articles.get(i).getUsr());
                homeBean.setType(articles.get(i).getType());
                homeBean.setTt(articles.get(i).getTt());
                homeBean.setVl(articles.get(i).getVl());
                homeBean.setChne(articles.get(i).getChne());
//                homeBean.setMyType(1);
                homeBeanses.add(homeBean);
            }
        }
        return homeBeanses;
    }

    @Override
    void uploaddatas() {
        setPage(getPage() + 1);
        loaddata(getPage());
    }

    @Override
    void downloaddatas() {
        adapter.mHeaders.clear();
        adapter.mFooters.clear();
        addheader();
        setPage(1);
        loaddata(getPage());
    }

    @Override
    int ViewResourcesid() {
        return R.layout.fragment_first;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

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
            listviewlayout.setVisibility(View.VISIBLE);
            netlayout.setVisibility(View.GONE);
            downloaddatas();

        } else {//断开网路
            listviewlayout.setVisibility(View.GONE);
            netlayout.setVisibility(View.VISIBLE);
        }
    }

}
