package com.onlylady.beautyapp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.beans.onlylady.HomeBeans;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.utils.ClickUtils;
import com.onlylady.beautyapp.utils.GlideUtils;
import com.onlylady.beautyapp.utils.StartActivityUtils;
import com.onlylady.beautyapp.utils.TimeUtils;
import com.onlylady.beautyapp.views.CircleImageView;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/12/29.
 */
public class HomeAdapter extends BaseRAdapter<HomeBeans> {
    private Context context;

    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;


    public HomeAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int[] getItemlayouts() {
        return new int[]{R.layout.home_listview_item1, R.layout.home_listview_item, R.layout.home_listview_item_headerview};
    }

    @Override
    public int getItemType(int position) {
        return getDatas().get(position).getMyType();
    }

    @Override
    public void onBindViewHolder(EasyRecyclerViewHolder viewHolder, int position) {

        int type = getItemType(position);
        ImageView imageview;
        TextView tt;
        TextView playerTimer;
        ImageView beautyLiveLogo;
        switch (type) {
            case TYPE_1:
                imageview = viewHolder.findViewById(R.id.imageview);
                tt = viewHolder.findViewById(R.id.tt);
                beautyLiveLogo = viewHolder.findViewById(R.id.beauty_live_logo);
                playerTimer = viewHolder.findViewById(R.id.player_timer);
                HomeBeans homeBeans = getDatas().get(position);
                GlideUtils.getInstance().setImageWithUrl(context, homeBeans.getIu(), imageview, false);
                tt.setText(homeBeans.getTt());
                if (isYesterday(homeBeans)) {//直播
                    //holder.liveorpasstext.setText("今日直播");
                    playerTimer.setVisibility(View.GONE);
                } else {
                    // holder.liveorpasstext.setText("往期直播");
                    playerTimer.setVisibility(View.VISIBLE);
                    playerTimer.setText(getDatas().get(position).getVl());
                }

                if (homeBeans.getStu() == 1) {
                    beautyLiveLogo.setImageResource(R.mipmap.beauty_live_logo);
                } else if (homeBeans.getStu() == 3) {
                    beautyLiveLogo.setImageResource(R.mipmap.beauty_playback_logo);
                } else if (homeBeans.getStu() == 2) {
                    beautyLiveLogo.setImageResource(R.mipmap.jijiangzhibo);
                    playerTimer.setVisibility(View.VISIBLE);
                    playerTimer.setText(TimeUtils.getInstance().getTime(getDatas().get(position).getStat()));
                }
                ClickUtils.getInstance().setSecClickLisener(context, getDatas().get(position), imageview);
                break;
            case TYPE_2:
                imageview = viewHolder.findViewById(R.id.imageview);
                tt = viewHolder.findViewById(R.id.tt);
                ImageView play = viewHolder.findViewById(R.id.play);
                CircleImageView circle = viewHolder.findViewById(R.id.circle);
                TextView name = viewHolder.findViewById(R.id.name);
                TextView cl = viewHolder.findViewById(R.id.cl);
                playerTimer = viewHolder.findViewById(R.id.player_timer);
                GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getIu(), imageview, false);
                GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getUp(), circle, false);
                name.setText(getDatas().get(position).getUsr());
                cl.setText(getDatas().get(position).getCl());
                tt.setText(getDatas().get(position).getTt());
                TextView logoName = viewHolder.findViewById(R.id.logo_name);
                logoName.setText(getDatas().get(position).getChne());

                ClickUtils.getInstance().setSecClickLisener(context, getDatas().get(position), imageview);
                if ("va".equals(getDatas().get(position).getType())) {//视频
                    playerTimer.setVisibility(View.VISIBLE);
                    play.setVisibility(View.VISIBLE);
                    playerTimer.setText(getDatas().get(position).getVl());
                } else if ("ar".equals(getDatas().get(position).getType())) {//文章
                    playerTimer.setVisibility(View.GONE);
                    play.setVisibility(View.GONE);
                }

                break;
            case TYPE_3:

                View layoutTimeriqi = viewHolder.findViewById(R.id.layout_timeriqi);
                TextView timeRiqi = viewHolder.findViewById(R.id.time_riqi);
                View liuBai = viewHolder.findViewById(R.id.liubai);
                View more = viewHolder.findViewById(R.id.beauty_more);
                TextView liveorpasstext = viewHolder.findViewById(R.id.liveorpasstext);
                if (getDatas().get(position).isFirst()) {
                    layoutTimeriqi.setVisibility(View.VISIBLE);
                    timeRiqi.setText(TimeUtils.getInstance().getRiqiandWeek(getDatas().get(position).getStat()));
                } else {
                    layoutTimeriqi.setVisibility(View.GONE);

                }
                if (position == 0) {

                    layoutTimeriqi.setVisibility(View.GONE);
                    liveorpasstext.setTextColor(context.getResources().getColor(R.color.colorff3064));
                } else {
                    liveorpasstext.setTextColor(context.getResources().getColor(R.color.home_text));

                }
                if ("live".equals(getDatas().get(position).getType())) {
                    if (isYesterday(getDatas().get(position))) {//直播
                        liveorpasstext.setText("今日直播");
                        more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EventBus.getDefault().post(EventBusC.getInstance(EventBusC.GOLIVE, null));

                            }
                        });

                    } else {
                        liveorpasstext.setText("往期直播");
                        more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StartActivityUtils.startSecLive(context);
                            }
                        });
                    }
                    more.setVisibility(View.VISIBLE);
                } else {
                    if (isYesterday(getDatas().get(position))) {
                        liveorpasstext.setText("今日推荐");
                        more.setVisibility(View.GONE);
                    } else {
                        liveorpasstext.setText("往期推荐");
                        more.setVisibility(View.GONE);
                    }
                    if ("va".equals(getDatas().get(position).getType())) {//视频

                    } else if ("ar".equals(getDatas().get(position).getType())) {//文章
                        more.setVisibility(View.GONE);
                    }


                }

                break;
        }
    }


//    // 每个convert view都会调用此方法，获得当前所需要的view样式
//    @Override
//     public int getItemViewType(int position) {
//        LogUtils.Log("-------"+position);
//        return getDatas().get(position).getMyType();
//    }


    private boolean isYesterday(HomeBeans homeBeans) {
        boolean yesterday = false;
        try {
            yesterday = TimeUtils.getInstance().isSameDay(Integer.parseInt(homeBeans.getStat()));
        } catch (NumberFormatException e) {

        }
        return yesterday;
    }

    @Override
    public List<HomeBeans> getDatas() {
        return (List<HomeBeans>) getList();
    }


}
