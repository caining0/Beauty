package com.onlylady.beautyapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView;

import com.onlylady.beautyapp.utils.DensityUtil;

public class Image168 extends ImageView {
    public Image168(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Image168(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Image168(Context context) {
        super(context);  
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        widthMeasureSpec=width/4- DensityUtil.dip2px(getContext(),9);
        int h = widthMeasureSpec;

        super.onMeasure(widthMeasureSpec,h );

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(h));
    }
}