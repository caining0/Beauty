package com.onlylady.beautyapp.elses;

import android.view.View;

public abstract class onDoubleClick implements View.OnClickListener {
    private Long preTime = 0l;

    @Override
    public void onClick(View v) {
        long currentTime =System.currentTimeMillis();
        if ((currentTime - preTime) > 1000) {
            preTime = currentTime;

            return;
        }
        DoubleClick();
    }

    public abstract void DoubleClick();
}