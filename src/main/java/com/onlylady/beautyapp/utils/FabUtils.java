package com.onlylady.beautyapp.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshRecycleView;
import com.onlylady.beautyapp.exlib.fab.FloatingActionButton;
import com.onlylady.beautyapp.exlib.fab.ScrollDetector;
import com.onlylady.beautyapp.exlib.fab.ShowHideOnScroll;

/**
 * Created by caining on 16/3/9.
 * 管理fab的工具类
 */
public class FabUtils {
    public static FabUtils clickUtils;

    public static FabUtils getInstance() {
        if (clickUtils == null) {
            clickUtils = new FabUtils();
        }
        return clickUtils;
    }

    public void initFab(final FloatingActionButton fab, final PullToRefreshRecycleView pulltorefrshlistview){
        pulltorefrshlistview.getRefreshableView().setOnTouchListener(new ShowHideOnScroll(fab));
        pulltorefrshlistview.getRefreshableView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollOffset() < 200) {
                    ScrollDetector.hide = true;
                    fab.setVisibility(View.GONE);
                } else {
                    ScrollDetector.hide = false;
                }
            }
        });


//        fab.initBackground();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pulltorefrshlistview.getRefreshableView().smoothScrollToPosition(0);
                fab.setVisibility(View.GONE);
            }
        });

    }
}
