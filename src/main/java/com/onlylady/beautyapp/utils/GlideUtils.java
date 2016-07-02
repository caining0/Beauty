package com.onlylady.beautyapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class GlideUtils {
    public static GlideUtils glideUtils;


    public static GlideUtils getInstance() {
        if (null == glideUtils) glideUtils = new GlideUtils();
        return glideUtils;
    }

    public void setImageWithUrl(Context context, String url, final ImageView img, boolean compress) {
        if (compress) {//压缩
            if (url.endsWith("gif")) {
                Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
            } else {
                Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Bitmap bitmap = BitmapUtils.getInstance().drawableToBitamp(resource);
                        Bitmap comp = BitmapUtils.getInstance().comp(bitmap);
                        img.setImageBitmap(comp);
                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE).preload();
            }
        }else {
            Glide.with(context).load(url).crossFade(500).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
        }
    }



}
