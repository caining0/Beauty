package com.onlylady.beautyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.beans.onlylady.Themes;
import com.onlylady.beautyapp.utils.GlideUtils;
import com.onlylady.beautyapp.utils.StartActivityUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by caining on 16/2/16.
 */
public class GridAdapter extends BaseAdapter {
    public GridAdapter(List datas, Context context) {
        super(datas, context);
    }

    @Override
    public View createView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_frind_griditem, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        GlideUtils.getInstance().setImageWithUrl(context,getDatas().get(i).getIu(),holder.imageview,false);
        holder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtils.startSecD(context,getDatas().get(i),null);
            }
        });
        return convertView;
    }

    @Override
    public List<Themes.DataEntity.ThemeEntity> getDatas() {
        return (List<Themes.DataEntity.ThemeEntity>) datas;
    }

    static class ViewHolder {
        @Bind(R.id.imageview)
        ImageView imageview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
