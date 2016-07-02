package com.onlylady.beautyapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView;

public class LiveImage extends ImageView {
    public LiveImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LiveImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveImage(Context context) {
        super(context);  
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        widthMeasureSpec = wm.getDefaultDisplay().getWidth();
        int h = widthMeasureSpec*480/750;
        super.onMeasure(widthMeasureSpec,h );
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(h);
//        int size = width > height ? height : width;
        setMeasuredDimension(width, height);
    }
}