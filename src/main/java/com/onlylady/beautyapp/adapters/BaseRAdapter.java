package com.onlylady.beautyapp.adapters;

import android.content.Context;

import com.handmark.pulltorefresh.library.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.handmark.pulltorefresh.library.easyrecyclerview.holder.EasyRecyclerViewHolder;

import java.util.List;

/**
 * Created by caining on 16/3/1.
 */
public abstract class BaseRAdapter<T> extends EasyRecyclerViewAdapter {
    public Context context;


    public BaseRAdapter(Context context) {
        this.context = context;
//        setList(datas);
//        notifyDataSetChanged();

    }

    @Override
    public int[] getItemLayouts() {
        return getItemlayouts();
    }

    public abstract int[] getItemlayouts();
    public abstract int getItemType(int position);
    public abstract void onBindViewHolder(EasyRecyclerViewHolder viewHolder, int position);
    public abstract List<T> getDatas();

    @Override
    public void onBindRecycleViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        onBindViewHolder(viewHolder,position);
    }

    @Override
    public int getRecycleViewItemType(int position) {
        return getItemType(position);
    }
}
