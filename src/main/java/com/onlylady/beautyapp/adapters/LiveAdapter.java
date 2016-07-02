package com.onlylady.beautyapp.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.beans.onlylady.LiveBeans;
import com.onlylady.beautyapp.utils.ClickUtils;
import com.onlylady.beautyapp.utils.DensityUtil;
import com.onlylady.beautyapp.utils.GlideUtils;
import com.onlylady.beautyapp.utils.StartActivityUtils;
import com.onlylady.beautyapp.utils.TimeUtils;
import com.onlylady.beautyapp.views.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class LiveAdapter extends BaseRAdapter<LiveBeans> {
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;


    public LiveAdapter(Context context) {

        super( context);
    }

    @Override
    public int[] getItemlayouts() {
        return new int[]{R.layout.live_listview_item,R.layout.live_listview_item1};
    }

    @Override
    public int getItemType(int position) {
        if (getDatas().get(position).getStu() == 2) {//未开始 小布局
            return TYPE_2;
        } else {
            return TYPE_1;
        }
    }


    @Override
    public void onBindViewHolder(EasyRecyclerViewHolder viewHolder, final int position) {
        int type = getItemType(position);
        CircleImageView circle ;
        TextView name ;
        TextView textvalue;
        View liubai ;
        switch (type) {
            case TYPE_1:
                liubai = viewHolder.findViewById(R.id.liubai);
                TextView timeRiqi = viewHolder.findViewById(R.id.time_riqi);
                RelativeLayout layoutTimeriqi = viewHolder.findViewById(R.id.layout_timeriqi);
                ImageView imageview = viewHolder.findViewById(R.id.imageview);
                TextView playerTimer =    (viewHolder.findViewById(R.id.player_timer));
                playerTimer .setText(getDatas().get(position).getVl());
                textvalue = viewHolder.findViewById(R.id.textvalue);
                circle = viewHolder.findViewById(R.id.circle);
                name = viewHolder.findViewById(R.id.name);
                ImageView play = viewHolder.findViewById(R.id.play);
                ImageView playorhuifang = viewHolder.findViewById(R.id.playorhuifang);

                GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getIu(), imageview, false);
                GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getUp(), circle, false);
                name.setText(getDatas().get(position).getUsr());
                liubai.setVisibility(View.GONE);
                ClickUtils.getInstance().setSecClickLisener(context,  getDatas().get(position),imageview,textvalue);


                if (getDatas().get(position).isFirst()) {
                    liubai.setVisibility(View.VISIBLE);
                    layoutTimeriqi.setVisibility(View.VISIBLE);
                    if (position==0){
                        layoutTimeriqi.setVisibility(View.GONE);
                    }
                    timeRiqi.setText(TimeUtils.getInstance().getRiqiandWeek(getDatas().get(position).getStat()));
                } else {
                    layoutTimeriqi.setVisibility(View.GONE);
                    if (position > 0 && getDatas().get(position).getType().equals(getDatas().get(position - 1).getType()) && getDatas().get(position).getStu() != getDatas().get(position - 1).getStu()) {
                        //如果  ，并且 是同一天的，但是类型不一样
                        liubai.setVisibility(View.VISIBLE);
                    }
                }
                textvalue.setText(getDatas().get(position).getTt());
                if (getDatas().get(position).getStu() == 1) {//直播
                    playerTimer.setVisibility(View.GONE);
                    playorhuifang.setImageResource(R.mipmap.zhiboicon);
                } else {//回放
                    playerTimer.setVisibility(View.VISIBLE);
                    playorhuifang.setImageResource(R.mipmap.huifang);
                }
                break;
            case TYPE_2:
                TextView lookmore = viewHolder.findViewById(R.id.lookmore);
                LinearLayout layoutTitle = viewHolder.findViewById(R.id.layout_title);
                ImageView imageview1 = viewHolder.findViewById(R.id.imageview);
                circle = viewHolder.findViewById(R.id.circle);
                textvalue = viewHolder.findViewById(R.id.textvalue);
                liubai = viewHolder.findViewById(R.id.liubai);
                name = viewHolder.findViewById(R.id.name);
                ImageView yuyueornot = viewHolder.findViewById(R.id.yuyueornot);
                LinearLayout mainLayout = viewHolder.findViewById(R.id.mainLayout);
                TextView timeRiqiyesteday = viewHolder.findViewById(R.id.time_riqiyesteday);
                TextView timeRiqitoday = viewHolder.findViewById(R.id.time_riqitoday);


                lookmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActivityUtils.startSecLive(context);
                    }
                });
                if ("tomorrownull".equals(getDatas().get(position).getType())){
                    mainLayout.setVisibility(View.GONE);
                    layoutTitle.setVisibility(View.GONE);
                    lookmore.setVisibility(View.VISIBLE);
                    break;
                }else {
                    mainLayout.setVisibility(View.VISIBLE);
                    layoutTitle.setVisibility(View.VISIBLE);
                    lookmore.setVisibility(View.GONE);
                }

                if (getDatas().get(position).getIrse()==1){//预约
                    yuyueornot.setImageResource(R.mipmap.yiyuyue);
                }else if (getDatas().get(position).getIrse()==0){//否
                    yuyueornot.setImageResource(R.mipmap.yuyue);
                }
                ClickUtils.getInstance().setYuyueLisener(context,yuyueornot,getDatas().get(position));



                GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getIu(), imageview1, false);
                GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getUp(), circle, false);
//                ClickUtils.getInstance().setSecClickLisener(context,getDatas().get(position), imageview1,textvalue,circle,name);
                //预约类不能进详情
                name.setText(getDatas().get(position).getUsr());
                textvalue.setText(getDatas().get(position).getTt());
                lookmore.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                liubai.setVisibility(View.GONE);

                if ("today".equals(getDatas().get(position).getType())) {
                    timeRiqiyesteday.setVisibility(View.GONE);
                    timeRiqitoday.setVisibility(View.VISIBLE);
                    timeRiqitoday.setText(TimeUtils.getInstance().getTime(getDatas().get(position).getStat()));
                } else {
                    timeRiqiyesteday.setVisibility(View.VISIBLE);
                    timeRiqitoday.setVisibility(View.GONE);
                    timeRiqiyesteday.setText(TimeUtils.getInstance().getRiqiandTime(getDatas().get(position).getStat()));
                }

                int i12 = DensityUtil.dip2px(context, 0);
                int i15 = DensityUtil.dip2px(context, 10);
                int i5 = DensityUtil.dip2px(context, 5);
                if (getDatas().get(position).isFirst()) {//第一个
                    liubai.setVisibility(View.VISIBLE);
                    //30px
                    if (!TextUtils.isEmpty(getDatas().get(position).getType())) {
                        if ("tomorrow".equals(getDatas().get(position).getType())) {
                            lookmore.setVisibility(View.VISIBLE);

                            layoutTitle.setVisibility(View.VISIBLE);
                        }
                        mainLayout.setPadding(i12, i15, i12, i5);
                    }
                    if (position < getListSize() - 1) {//并且是最后一个
                        if (!getDatas().get(position).getType().equals(getDatas().get(position + 1).getType())) {
                            mainLayout.setPadding(i12, i15, i12, i15);
                        }

                        if (getDatas().get(position).getStu() != getDatas().get(position + 1).getStu()) {
                            mainLayout.setPadding(i12, i15, i12, i15);
                        }

                    }
                } else {
                    if (position == getListSize() - 1) {
                        mainLayout.setPadding(i12, i5, i12, i15);
                    }
                    if (position < getListSize() - 1) {
                        if (!getDatas().get(position).getType().equals(getDatas().get(position + 1).getType())) {
                            mainLayout.setPadding(i12, i5, i12, i15);
                        }

                        if (getDatas().get(position).getStu() != getDatas().get(position + 1).getStu()) {
                            mainLayout.setPadding(i12, i5, i12, i15);
                        }

                    }
                }
                break;
        }

    }

    @Override
    public List<LiveBeans> getDatas() {
        return (List<LiveBeans>) getList();
    }


}
