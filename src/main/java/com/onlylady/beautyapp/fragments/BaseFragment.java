package com.onlylady.beautyapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * cnn
 * fragment 基类
 */
public abstract class BaseFragment extends Fragment {
    Context context;
    private View v;

    public BaseFragment() {
        initfirst();
    }

    abstract void initfirst();

    abstract boolean useEventbus();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(getViewResourcesid(), null, false);
        ButterKnife.bind(this, v);
        context = getActivity();
        if (useEventbus()) {
            EventBus.getDefault().register(this);//并不能所有界面注册
        }
        returnView();
        doAfterview();
        return v;
    }

    public View returnView() {
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    protected abstract void doAfterview();


    protected abstract int getViewResourcesid();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (useEventbus()) {
            EventBus.getDefault().unregister(this);//并不能所有界面注册
        }
    }
}
