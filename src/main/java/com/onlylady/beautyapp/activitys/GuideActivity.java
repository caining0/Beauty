package com.onlylady.beautyapp.activitys;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.onlylady.beautyapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class GuideActivity extends BaseActivity {


    @Bind(R.id.mviewpager)
    ViewPager mViewpager;
    // 如果想直接应用到你的项目中，只需在这里添加删除图片id即可
    private int[] ids = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3, R.mipmap.guide_4};
    private List<ImageView> imageViews = new ArrayList<>();

    @Override
    public boolean useEventbus() {
        return false;
    }

    @Override
    public void createview() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_guide);
        if ("Xiaomi".equals(android.os.Build.MANUFACTURER)){
            ids[0]=R.mipmap.xiaomi_guide_1;
            ids[1]=R.mipmap.xiaomi_guide_2;
            ids[2]=R.mipmap.xiaomi_guide_3;
            ids[3]=R.mipmap.xiaomi_guide_4;
        }
    }

    private void initview() {
        mViewpager.setOffscreenPageLimit(4);
        for (int i = 0; i < ids.length; i++) {
            imageViews.add(buildImageView(ids[i]));
        }
        imageViews.add(new ImageView(getApplicationContext()));
    }

    private boolean isdohere = false;

    @Override
    public void initlisener() {
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 3 && !isdohere && positionOffset > 0) {
                    isdohere = true;
                    GuideActivity.this.finish();
                }
            }

            @Override
            public void onPageSelected(int position) {
                // LogUtils.Log(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void initData() {
        initview();
        mViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {

                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imageViews.get(position));
                return imageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
                container.removeView(imageViews.get(position));
            }
        });

    }

    private ImageView buildImageView(int id) {
        ImageView iv = new ImageView(this);
        iv.setImageResource(id);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(params);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        return iv;
    }
}
