package com.onlylady.beautyapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/12/29.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    public List<T> datas;
    public Context context;
    public BaseAdapter(List<T> datas, Context context){
        this.datas=datas;
        this.context=context;
    }
    @Override
    public int getCount() {
        return datas==null?0: datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        return createView(i,convertView,viewGroup);
    }
    public  abstract View createView(int i, View convertView, ViewGroup viewGroup);

    public abstract List<T> getDatas() ;
}
