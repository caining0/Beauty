package com.onlylady.beautyapp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.beans.onlylady.SecDetailBeans;
import com.onlylady.beautyapp.utils.ClickUtils;
import com.onlylady.beautyapp.utils.GlideUtils;
import com.onlylady.beautyapp.views.CircleImageView;
import com.onlylady.beautyapp.views.ImageDetail;

import java.util.List;

/**
 * Created by caining on 16/2/15.
 */
public class SecDetailAdapter extends BaseRAdapter {
    public SecDetailAdapter( Context context) {
        super( context);
    }

    @Override
    public int[] getItemlayouts() {
        return new int[]{R.layout.secdetail_listview_item};
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        View liubai = viewHolder.findViewById(R.id.liubai);
        RelativeLayout layoutTimeriqi = viewHolder.findViewById(R.id.layout_timeriqi);
        ImageDetail imageview = viewHolder.findViewById(R.id.imageview);
        TextView textvalue = viewHolder.findViewById(R.id.textvalue);
        CircleImageView circle = viewHolder.findViewById(R.id.circle);
        TextView name = viewHolder.findViewById(R.id.name);
        View below = viewHolder.findViewById(R.id.below);
        ImageView play = viewHolder.findViewById(R.id.play);
        ImageView playorhuifang = viewHolder.findViewById(R.id.playorhuifang);


        GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getIu(), imageview, false);
        GlideUtils.getInstance().setImageWithUrl(context, getDatas().get(position).getUp(), circle, false);
        ClickUtils.getInstance().setSecClickLisener(context,getDatas().get(position),imageview);

        name.setText(getDatas().get(position).getUsr());
        layoutTimeriqi.setVisibility(View.GONE);
        liubai.setVisibility(View.GONE);
        textvalue.setText(getDatas().get(position).getTt());
        playorhuifang.setVisibility(View.GONE);
        if ("ar".equals(getDatas().get(position).getType())) {
            play.setVisibility(View.GONE);
            playorhuifang.setVisibility(View.GONE);
        } else if ("va".equals(getDatas().get(position).getType())) {
            play.setVisibility(View.VISIBLE);
//
//            playorhuifang.setImageResource(R.mipmap.huifang);
        }

    }


    @Override
    public List<SecDetailBeans.DataEntity.ArticlesEntity> getDatas() {
        return (List<SecDetailBeans.DataEntity.ArticlesEntity>) getList();
    }

}
