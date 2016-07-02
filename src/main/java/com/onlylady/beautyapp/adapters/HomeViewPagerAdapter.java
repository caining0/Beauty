/*
package com.onlylady.beauty.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onlylady.beauty.R;
import com.onlylady.beauty.beans.VideoListBean;
import com.onlylady.beauty.utils.GlideUtils;

import java.util.List;

public class HomeViewPagerAdapter extends PagerAdapter {
    private List<View> mList;
    private List<VideoListBean.DataEntity> focuses;
    private Context context;

    public HomeViewPagerAdapter(List<View> list, List<VideoListBean.DataEntity> focuses, Context context) {
        this.mList = list;
        this.focuses = focuses;
        this.context = context;

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE / 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mList.get(position % mList.size());
        ImageView iv = (ImageView) view.findViewById(R.id.viewpagerimage);
        VideoListBean.DataEntity focusesEntity = focuses.get(position % mList.size());
//            glidemager.load(focusesEntity.getHpl()).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade(500).into(iv);
        GlideUtils.getInstance().setImageWithUrl(context, focusesEntity.getImg(), iv, false);
//        TextView viewpagerText = (TextView) view.findViewById(R.id.viewpagertext);
//        viewpagerText.setText(focusesEntity.getTt());
        container.addView(view);
       */
/* final FocusesEntity articles = focusesEntity;

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobclickAgent.onEvent(context, UmengKey.UMENTKEY18);
                if ("ad".equals(articles.getType())) {
                    StartActivityUtils.startADActivity(context, articles.getVal(), articles.getTt(), articles.getShu(), articles.getHpl(),articles);
                } else {
                    StartActivityUtils.startH5Activity(context, articles.getVal(), articles.getTt(), articles.getShu(), articles.getHpl(),"");
                }
            }
        });*//*

        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(mList.get(position % mList.size()));
    }

}*/
