package com.onlylady.beautyapp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.beans.onlylady.Focuses;
import com.onlylady.beautyapp.utils.ClickUtils;
import com.onlylady.beautyapp.utils.GlideUtils;

/**
 * Created by Sai on 15/8/4.
 * 本地图片Holder例子
 */
public class LocalImageHolderView implements Holder<Focuses.DataEntity.FocusesEntity> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(R.mipmap.banner_750_750);
        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, int position, Focuses.DataEntity.FocusesEntity data) {
//        imageView.setImageResource(data);

        ClickUtils.getInstance().setSecClickLisener(context, data,imageView);
        GlideUtils.getInstance().setImageWithUrl(context,data.getHpl(),imageView,false);
    }
}