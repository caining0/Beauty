package com.onlylady.beautyapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
   
public class SquareLayout extends RelativeLayout {
    public SquareLayout(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
   
    public SquareLayout(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
   
    public SquareLayout(Context context) {  
        super(context);  
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int size = width > height ? height : width;
        setMeasuredDimension(width, width);
    }
}