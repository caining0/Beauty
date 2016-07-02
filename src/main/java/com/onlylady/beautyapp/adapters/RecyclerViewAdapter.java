package com.onlylady.beautyapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.beans.onlylady.Themes;
import com.onlylady.beautyapp.utils.GlideUtils;
import com.onlylady.beautyapp.utils.StartActivityUtils;

import java.util.List;


/**
 * Created by Administrator on 2015/7/6.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Themes.DataEntity.ThemeEntity> datas;
    private Context context;
    public RecyclerViewAdapter(List<Themes.DataEntity.ThemeEntity> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_hlistview_item, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        final Themes.DataEntity.ThemeEntity tags = datas.get(i);
        GlideUtils.getInstance().setImageWithUrl(context, tags.getIu(), myViewHolder.image,false);
//        myViewHolder.textTitle.setText(tags.getTt());
        myViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtils.startSecD(context, tags, null);
            }
        });




    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
//        public TextView textTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageview);
//            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
        }
    }

}
