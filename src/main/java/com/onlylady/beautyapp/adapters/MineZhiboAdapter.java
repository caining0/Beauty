package com.onlylady.beautyapp.adapters;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.activitys.AddZhiboActivity;
import com.onlylady.beautyapp.activitys.MineZhiboActivity;
import com.onlylady.beautyapp.beans.onlylady.MineLiveBeans;
import com.onlylady.beautyapp.engines.GetLiveBeanFromLid;
import com.onlylady.beautyapp.exlib.pili.Config;
import com.onlylady.beautyapp.exlib.pili.HWCodecCameraStreamingActivity;
import com.onlylady.beautyapp.utils.ClickUtils;
import com.onlylady.beautyapp.utils.DensityUtil;
import com.onlylady.beautyapp.utils.DialogUtils;
import com.onlylady.beautyapp.utils.GlideUtils;
import com.onlylady.beautyapp.utils.LogUtils;
import com.onlylady.beautyapp.utils.TimeUtils;
import com.onlylady.beautyapp.utils.ToastUtils;
import com.onlylady.beautyapp.views.LiveImage;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by caining on 16/2/15.
 */
public class MineZhiboAdapter extends BaseRAdapter {
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    private MineZhiboActivity activit;

    public MineZhiboAdapter(MineZhiboActivity context) {
        super(context);
        this.activit = context;
    }

    @Override
    public int[] getItemlayouts() {
        return new int[]{R.layout.minelive_listview_item, R.layout.minelive_listview_item1};
    }

    @Override
    public int getItemCount() {
        int itemCount = super.getItemCount();
//        if (itemCount==0){
//            activit.getPulltorefrshlistview().setVisibility(View.GONE);
//            activit.textshow.setVisibility(View.VISIBLE);
//        }
        return itemCount;
    }

    @Override
    public int getItemType(int position) {
        if (getDatas().get(position).getStu() == 1 || getDatas().get(position).getStu() == 2) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public void onBindViewHolder(EasyRecyclerViewHolder viewHolder, final int position) {
        addpading(getItemType(position), position, viewHolder.findViewById(R.id.rootview));
        switch (getItemType(position)) {
            case TYPE_1:
                ImageView imageView = viewHolder.findViewById(R.id.imageview);
                ImageView beginplay = viewHolder.findViewById(R.id.beginplay);
                LinearLayout mainLayout = viewHolder.findViewById(R.id.mainLayout);

                final String stat = getDatas().get(position).getStat();
                beginplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastUtils.show(context, "aaa");
                        if (TextUtils.isEmpty(stat) || !TimeUtils.getInstance().isSameDay(Integer.parseInt(stat))) {
                            ToastUtils.show(context, "非今天直播不允许开启！");
                            return;
                        }
//                        Intent intent = new Intent(activit, LiveBaiduActivity.class);

                        GetLiveBeanFromLid.getInstance().getLiveBeanLaid(getDatas().get(position).getLid(), new GetLiveBeanFromLid.CallbackJson() {
                            @Override
                            public void finish(JSONObject json) {
                                LogUtils.Log(json.toString());
                                Intent intent = new Intent(activit, HWCodecCameraStreamingActivity.class);
                                intent.putExtra(Config.EXTRA_KEY_STREAM_JSON, json.optString("js"));
                                intent.putExtra("lid",getDatas().get(position).getLid());
                                activit.startActivityForResult(intent, 1000);
                            }
                        });

                    }
                });
                if (getDatas().get(position).getStu() == 2) {//未开始
                    beginplay.setBackgroundResource(R.drawable.selector_zhibo_begin);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (getDatas().get(position).getStu()  == 2){//未开始
                                Intent intent = new Intent(context, AddZhiboActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("lid", getDatas().get(position).getLid());
                                context.startActivity(intent);
                            }
                        }
                    });
                    imageView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteItem(getDatas().get(position).getLid(), position);
                            return true;
                        }
                    });

                    mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteItem(getDatas().get(position).getLid(), position);
                            return true;
                        }
                    });

                } else if (getDatas().get(position).getStu() == 1) {//进行中
                    beginplay.setBackgroundResource(R.drawable.selector_zhibo_continue);
                }
                TextView title = viewHolder.findViewById(R.id.title);
                TextView time = viewHolder.findViewById(R.id.time);
                if (TextUtils.isEmpty(stat) || !TimeUtils.getInstance().isSameDay(Integer.parseInt(stat))) {
                    time.setText(TimeUtils.getInstance().getRiqiandTime(stat));
                } else {//今天
                    time.setText(TimeUtils.getInstance().getTime(stat));
                }

                GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getIu(), imageView, false);
                title.setText(getDatas().get(position).getTt());

                break;
            case TYPE_2:
                LiveImage image = viewHolder.findViewById(R.id.imageview);
                image.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        deleteItem(getDatas().get(position).getLid(), position);
                        return true;
                    }
                });
                GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getIu(), image, false);
                TextView textView = viewHolder.findViewById(R.id.player_timer);
                TextView title1 = viewHolder.findViewById(R.id.title);
                textView.setText(getDatas().get(position).getVl());
                title1.setText(getDatas().get(position).getTt());
//                StartActivityUtils.startH5(context,getDatas().get(position));
                ClickUtils.getInstance().setSecClickLisener(context, getDatas().get(position), image);
                break;
        }


    }

    private void addpading(int itemType, int position, View v) {

        if (position > 0 && getDatas().get(position - 1).getStu() != getDatas().get(position).getStu()) {// TODO: 16/4/20 如果不是第一个 并且和上一个类型不一样
            v.setPadding(0, DensityUtil.dip2px(context, 5), 0, 0);
        }
        if (itemType == TYPE_1) {//
            v.setPadding(0, DensityUtil.dip2px(context, 5), 0, 0);
        } else {

        }
    }

    private void deleteItem(String lid, final int posi) {
        int stu = getDatas().get(posi).getStu();
        if ( stu== 2||stu==3)
            DialogUtils.getInstance().deleteItem(activit, lid, getDatas(), this, posi);
    }


    @Override
    public List<MineLiveBeans.DataEntity.LiveEntity> getDatas() {
        return (List<MineLiveBeans.DataEntity.LiveEntity>) getList();
    }
}
