package com.onlylady.beautyapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView;

import com.onlylady.beautyapp.utils.DensityUtil;

public class Image345245 extends ImageView {
    public Image345245(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Image345245(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Image345245(Context context) {
        super(context);  
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        widthMeasureSpec=(width- DensityUtil.dip2px(getContext(),30))/2;

        int h = widthMeasureSpec*245/345;

        super.onMeasure(widthMeasureSpec,h );

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(h));
    }
}