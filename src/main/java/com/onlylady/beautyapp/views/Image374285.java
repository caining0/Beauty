package com.onlylady.beautyapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView;

public class Image374285 extends ImageView {
    public Image374285(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Image374285(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Image374285(Context context) {
        super(context);  
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        widthMeasureSpec=width*374/750;

        int h = widthMeasureSpec*285/374;

        super.onMeasure(widthMeasureSpec,h );

//        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(h));
    }
}