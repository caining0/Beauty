package com.onlylady.beautyapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView;

public class ImageDetail extends ImageView {
    public ImageDetail(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ImageDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageDetail(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        widthMeasureSpec = wm.getDefaultDisplay().getWidth();
        int h = widthMeasureSpec * 650 / 750;
        super.onMeasure(widthMeasureSpec, h);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(h));
    }
}