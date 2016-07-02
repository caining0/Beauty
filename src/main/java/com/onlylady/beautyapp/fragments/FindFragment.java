package com.onlylady.beautyapp.fragments;


import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.reflect.TypeToken;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.adapters.BaseAdapter;
import com.onlylady.beautyapp.adapters.GridAdapter;
import com.onlylady.beautyapp.beans.onlylady.Themes;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.StartActivityUtils;

import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {

    @Bind(R.id.gridView)
    GridView gridView;
    @Bind(R.id.rl_hufu)
    RelativeLayout rlHufu;
    @Bind(R.id.rl_caizhuang)
    RelativeLayout rlCaizhuang;
    @Bind(R.id.rl_meifa)
    RelativeLayout rlMeifa;
    @Bind(R.id.rl_meiti)
    RelativeLayout rlMeiti;
    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.neterror)
    RelativeLayout netlayout;
    @Bind(R.id.listviewlayout)
    LinearLayout listviewlayout;
    private BaseAdapter adapter;
//    private List<Themes.DataEntity.ThemeEntity> datas;


    @Override
    void initfirst() {
//        datas = new ArrayList<>();

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
    protected void doAfterview() {
        rlHufu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtils.startSecD(context, null, "2013");
            }
        });
        rlCaizhuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtils.startSecD(context, null, "2014");
            }
        });
        rlMeifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtils.startSecD(context, null, "2015");
            }
        });
        rlMeiti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtils.startSecD(context, null, "2016");
            }
        });

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });


        BaseEngine.getInstance().getTGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1002("found"), new TypeToken<Themes>() {
        }.getType(), new BaseEngine.CallbackForT<Themes>() {

            @Override
            public void finish(Themes bean) {
                adapter = new GridAdapter(bean.getData().getTheme(), getActivity());
                gridView.setAdapter(adapter);

//                scrollView.scrollTo(0,0);
            }

            @Override
            public void finish(List<Themes> listT) {
            }
        });

    }

    @Override
    protected int getViewResourcesid() {
        return R.layout.fragment_find;
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
            doAfterview();

        } else {//断开网路
            listviewlayout.setVisibility(View.GONE);
            netlayout.setVisibility(View.VISIBLE);
        }
    }

}
